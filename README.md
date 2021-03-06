# ---------------架构治理平台开发方案-------------
**摘要：**     
       近日，随着架构治理平台的开发和使用发现，平台陆续出现很多改造和优化的工作。造成的返工的原因一方面是项目需初期只顾及功能的实现，没有考虑到长久的宏观上的考虑，比如，以八大军规为例，所有业务的指标的采集数据全部塞到arch_db_connect表中，数据库中部分表的数据量累计过大（数百万级别），导致页面出现卡顿延迟现象，另一方面是前期开发需求不明确，没有一定的开发需求标准，为了今后提高大家的工作效率避免多次重复性返工，现展开一下初步的开发需求的标准进行统一研讨指定。
## 一、表结构设计  
### 数据采集频率(准实时、少于N次/天)  
目前，营业数据库A/B/C/D中心连接数采集频率为10分钟一次，每天采集6 x 24=144次；     
目前，营业CRM库SESSION信息采集频率为30分钟一次，每天采集2 x 24=48次；   
因此，数据采集频率太快了也不行，高频度采集会导致表每日增量剧增，以后势必导致查询慢，规定理论查询频率不得高于10min/1次，对于需求中超出理论值会很消耗性能，需求中要反应实际性能情况；          
考虑到每个定时采集任务在同一时间点不能无限多个，有一定的线程数量限制，规定不能超过20个，超出的采集配置需要另起一个采集任务；    
### 采集一次数据量  
目前，营业数据库A/B/C/D中心连接数采集一次数据量为171条；    
目前，营业CRM库SESSION信息采集一次数据量为25000条左右；   
因此，建议采集一次数据量也不要太大，或者降低其采集频率，否则数据量过多导致表每日增量剧增，以后势必导致查询慢；   
### 表理论每日增量  
目前，营业数据库A/B/C/D中心连接数表理论每日增量为171 x 144=24624条；  
目前，营业CRM库SESSION信息采集表理论每日增量为25000 x 48=120万条左右，尽管现在arch_session_yyyymmdd已经建立日表，但是每天百万以上的数据量查询依旧性能很差；  
表理论增量=数据采集频率 x 采集一次数据量；可以根据表理论每日增量进行分表策略，考虑采集数据大多索引利用率不高，规定表理论每日增量不得超过100w条；对于需求中如果估算超出100w理论值的情形，可以讨论削减需求的采集频率，或者在不降低采集频率的情况下放宽查询性能要求；    

### 单表最大数据量级  
oracle推荐是单表500万，考虑到业务上面大都是连表查询，部分数据没有唯一主键索引利用率不高，查询字段多等，这里本项目规定单表最大数据量级为100万；对于需求中如果单表最大数量级超出100w理论值的情形，可以讨论是否分库分表，或者在不分库分表的情况下放宽查询性能要求；    
 
### 数据保留时间    
这里首先在没有采用按年月日分表的情况下，表数据保留时间根据单表最大数量级进行判定；  
* 如一个应用的数据在一年后数据量会达到100w左右，那么我们就可以考虑保留一年的数据来做为一个表或者库来存储，而距离现在超过一年的数据删除掉；  
* 如果数据量在一个月就达到了100w左右，那么我们就可以保留一个月之内的有效数据，而距离现在超过一个月的数据删除掉；   
* 如果数据量在某些天就达到100w左右，那么我们就可以保留这几天之内的有效数据，而距离现在超过一个月的数据删除掉；   
* 如果数据量在一天之内就达到100w以上，那么我们应该讨论需求是否应该减短有效数据保留时间，删除保留时间以外的数据，或者放低查询的性能要求；   
* 此外，如果数据量在很长一段时间呢都不会达到100万的数量级，那么就忽略数据保留时间的问题了；   
如果有按年月日的分表策略，并且单表最大数量级未达到上限，则可以忽略数据保留时间问题；   
### 建表方式(新建、复用单表或分表分区等) 
根据表的以后存储的数据数量级判定，当一个应用的数据量大的时候，我们用单表和单库来存储会严重影响操作速度，如oracle存储，我们经过测试，100w以下的时候，oracle的访问速度都还可以接受，但是如果超过100w以上的数据，他的访问速度会急剧下降，影响到我们webapp的访问速度，而且数据量太大的话，如果用单表存储，就会使得系统相当的不稳定，所以当数据量超过100w的时候，建议还是考虑分库分表，除非在需求中不追求查询性能的情形；         
以下是平台常见的分表算法。    
#### 按自然时间来分表/分库;    
* 如一个应用的数据在一年后数据量会达到100w左右，那么我们就可以考虑用一年的数据 来做为一个表或者库来存储，例如，表名为archi，那么2018年的数据就是archi_2018，archi_2019;
* 如果数据量在一个月就达到了100w左右，那么我们就可以用月份来分，archi_2018_01，archi_2018_02等；  
* 如果数据量在一天就达到100w左右，那么我们就可以用日来分，archi_2018_01_01，archi_2018_01_02等；   
* 此外，如果数据量在一天内的数据量仍很多，可以继续按照业务等进行拆分，除非需求中明确不追求查询性能的要求；     
### 建索引  
简单地讲，语句执行的时间越短越好。而对于查询语句，由于全表扫描读取的数据多，尤其是对于大型表不仅查询速度慢，而且对磁盘IO造成大的压力，通常都要避免，而避免的方式通常是使用索引Index。  
* 单列索引  
* 复合索引  
建设原则:
1、索引应该经常建在Where 子句经常用到的列上。如果某个大表经常使用某个字段进行查询，并且检索行数小于总表行数的5%。则应该考虑。   
2、对于两表连接的字段，应该建立索引。如果经常在某表的一个字段进行Order By 则也经过进行索引。    
3、不应该在小表上建设索引。      
创建索引:    
单一索引:Create Index <Index-Name> On <Table_Name>(Column_Name);    
复合索引: Create Index index_aiga_staff on aiga_staff(id,code); —>在aiga_staff表的id、code列建立索引。   
select * from aiga_staff where id=66 and code='sals' ->走索引。   
select * from aiga_staff where id=66 OR code='sals' ->将进行全表扫描。不走索引   
select * from aiga_staff where id=66 ->走索引。   
select * from aiga_staff where code='sals' ->进行全表扫描、不走索引。   
通过创建唯一性索引，可以保证数据库表中每一行数据的唯一性。如果在where子句中有OR操作符或单独引用code列(索引列的后面列)则将不会走索引，将会进行全表扫描。     
### 分表分区（按日分表/按月分表）
一般不按照数据量计算，oracle给的建议是1G，oracle推荐是单表500万，数据量能大于1G，那么就需要分区。具体的数据量没给那么清晰。
不过个人认为，是否需要分区要根据实际需求来判断，比如几百万的数据量，总会按月查询，每月大概几十万到一百万左右，那么就可以分区，这样可以减少查询等待时间，并且减少一次查询的数据量。    
### 多维度（实时多表关联查询/实时计算结果单表查询/缓存数据实时计算查询）   
实时多表关联查询/实时计算结果单表查询在sql查询性能方面是一样的，只不过实时计算结果单表是在实时多表关联查询的上层封装，缓存数据实时计算查询的性能借用缓存技术，性能会得到很大程度的提升，不过带来的问题是时间换空间，会消耗更多的内存；   
### 缓存*（在持久层或持久层之上做缓存）  
日后可以考虑引入到我们项目中， [redis官网](http://redis.io/download/ "Title") 使用简单配置redis.conf配置文件，启动redis服务端，通过jedis-2.9.0.jar去连接redis服务，进而通过Java去操作缓存数据，优势如下：  
1，nosql非关系型的数据库，sql语句对它不起作用，不需要建表存数据，分布式（主从复制），开源的水平可扩展；     
2.它可以处理超大量的数据，运行在便宜的pc服务器集群上，击碎了性能瓶颈（性能好，也不需要优化，对数据高并发读写，对海量数据的高效率存储和访问，对数据的高扩展性和高可用性     
3，redis可以定时把内存数据同步到磁盘，将数据持久化，redis可以看作加上持久化特性的memcache；     
4，丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作,可以包含任何数据（图片和序列化的对象都可以）；      

## 二、查询功能模块设计  
### 查询时间方式(特定时间选择or可选择时间区间)
1.特定时间，对于显示每天报表的类型数据，页面初始加载是默认指定当天；    
2.可选择时间，对于显示阶段性变化趋势类型的数据，暂定默认选定当月1号到当月当天之间的数据；如果数据量庞大百万级别的情况下可以酌情查询近7天；     
### 查询耗时要求
1.考虑到避免影响用户的使用体验，将页面显示的数据及时的呈现，查询耗时最好维持在2秒钟以内；    
### 是否有最大查询时间跨度限制  
首先要根据涉及表存储最大数量级进行预先判断：   
这里首先在没有采用按年月日分表的情况下，表数据保留时间根据涉及表存储最大数量级进行预先判断：   
* 如果一个应用的数据在一年后数据量会达到100w左右，那么我们就设定最大查询时间跨度限制为1年，否则延长时间跨度将降低查询性能；   
* 如果数据量在一个月就达到了100w左右，那么我们就设定最大查询时间跨度限制为1个月，否则延长时间跨度将降低查询性能；    
* 如果数据量在某些天就达到100w左右，就设定最大查询时间跨度限制为几天，否则延长时间跨度将降低查询性能；    
* 此外，如果数据量在很长一段时间呢都不会达到100万的数量级，那么就忽略最大查询时间跨度限制；     
如果有按年月日的分表策略，并且单表最大数量级未达到上限，则可以忽略最大查询时间跨度限制；  

### 是否支持结果Excel导出
项目对于Excel导出功能已经封装到ExcelExportController中，首先明确需求确定用户需要具体导出哪些字段，此外如果数据有时间字段的话要明确默认导出某几天之内的数据，避免需求不统一；
> 	
	public HSSFWorkbook export(List<Map> list) { 
		//明确需求，明确excel，sheet页面表格的表头字段
		String[] excelHeader = { "系统名称", "系统编号", "所属一级域" , "所属二级域", "所属分层", "系统描述", "责任部门", "项目立项信息", "规划设计信息", "状态"}; 
		//明确excel，每个sheet的名称列表
		String[] excelSheet = { "业务支撑域", "管信域", "BOMC域", "安全域", "大数据域", "公共域", "网络域", "地市域", "开放域"};
		//创建Excel对象
		HSSFWorkbook wb = new HSSFWorkbook();  
		//创建sheet页对象列表
		List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
		//创建row行对象列表
		List<HSSFRow> rowList = new ArrayList<HSSFRow>();
		int[] num = new int[9];
		for(String excelSheetBase : excelSheet) {
			//循环创建9个sheet页对象，并分别创建第一行设置表头
			HSSFSheet sheet = wb.createSheet(excelSheetBase);
			sheetList.add(sheet);
			HSSFRow row = sheet.createRow((int) 0);
			rowList.add(row);
		}
		//为excel对象添加样式，设置合并单元格，行高，列宽
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		for (int i = 0; i < excelHeader.length; i++) {
			for(int j=0; j < rowList.size(); j++) {
				HSSFCell cell = rowList.get(j).createCell(i);
			    cell.setCellValue(excelHeader[i]);  
			    cell.setCellStyle(style); 
			    if("1,2,3,4,8".contains(String.valueOf(i))) {
				sheetList.get(j).setColumnWidth(i, 12 * 256);
			    } else if(i==5) {
				sheetList.get(j).setColumnWidth(i, 60 * 256);  
			    } else {
				sheetList.get(j).setColumnWidth(i, 20 * 256);
			    }	         
			}
		}
		//将数据填充到excel对象中sheet页的每一行中
		for (Map data : list) {  
			int index = Integer.parseInt(String.valueOf(data.get("idThird")))/10000000;
			index--;
			HSSFRow row =sheetList.get(index).createRow(++num[index]);  
			row.createCell(0).setCellValue(String.valueOf(data.get("name")).replace("null", ""));
			row.createCell(1).setCellValue(String.valueOf(data.get("idThird")).replace("null", ""));
			row.createCell(2).setCellValue(String.valueOf(data.get("firName")).replace("null", ""));
			row.createCell(3).setCellValue(String.valueOf(data.get("secName")).replace("null", ""));
			row.createCell(4).setCellValue(String.valueOf(data.get("belongLevel")).replace("null", ""));
			row.createCell(5).setCellValue(String.valueOf(data.get("systemFunction")).replace("null", ""));
			row.createCell(6).setCellValue(String.valueOf(data.get("department")).replace("null", ""));
			row.createCell(7).setCellValue(String.valueOf(data.get("projectInfo")).replace("null", "")); 
			row.createCell(8).setCellValue(String.valueOf(data.get("designInfo")).replace("null", ""));
			row.createCell(9).setCellValue(String.valueOf(data.get("codeName")).replace("null", ""));
		}  
		return wb;  
	    } 

	public @ResponseBody void sysMessageExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//查询后台数据库中的数据
		List<Map> findData = architectureThirdSv.excelExport(0L,"");
		//排序
		Collections.sort(findData, new sysExportComparator());
		//创建excel对象
		HSSFWorkbook wb = export(findData); 
		//设置response返回头信息，以及excel文件名，并本地下载
		response.setContentType("application/vnd.ms-excel");  
		Date nowtime = new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd");
		String time=format.format(nowtime);
		response.setHeader("Content-disposition", "attachment;filename="+new String("系统基线_".getBytes(),"iso-8859-1")+time+".xls");  
		OutputStream ouputStream = response.getOutputStream();  
		wb.write(ouputStream);  
		ouputStream.flush();  
		ouputStream.close(); 
		}
### 是否存在环比变化计算
1.日环比计算    
计算公式：（今日数据-昨日数据）/昨日数据的百分比值%    
2.月环比计算    
计算公式：（今日数据-上月今日数据）/上月今日数据的百分比值%    
### 环比变化计算参照时间是否可选择
1.参照时间可选，第一次进入页面时，默认加载查询展示出当天和前一天的数据，以后的更换参照时间进行查询时，按照对比的条件在后台查询汇总展示到页面中来；   
2.参照时间不可选，则默认加载查询展示出当天和前一天的数据；   
### 是否有曲线图展示模块
#### echarts规范/近7天、近30天曲线(同理)    
目前项目引入折线图、柱状图以及饼状图展示，开发曲线图展示要理解需求中展现数据的维度，横纵坐标轴的划分等；

1、项目引入图形化数据展示可以使用Echats组件， [Echarts官网](http://echarts.baidu.com/examples/ "Title")
> 
	require("lib/ztree/3.5.28/js/jquery.ztree.excheck.min.js");

2、然后在绘图前我们需要为 ECharts 准备一个具备高宽的 DOM 容器。

> 
	<body>
	    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
	    <div id="main" style="width: 600px;height:400px;"></div>
	</body>

3、然后就可以通过 echarts.init 方法初始化一个 echarts 实例并通过 setOption 方法生成一个简单的柱状图，下面是完整代码。

>   
    var myChart = echarts.init(Page.findId('archiIndexViewMax')[0]);//echarts初始化操作
            myChart.showLoading({
                text: '读取数据中...' //loading，是在读取数据的时候显示
            });            
			var option = {
				title : {
			        text: '指标情况',//主标题
			        subtext: '数据采集截止时间：XX月XX日XX:XX'//副标题
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {//数据项指标
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
			    },
			    toolbox: {
			        show : true,
			        feature : {
	                    restore: { //重置
	                        show: true
	                    },
	                    dataZoom: { //数据缩放视图
	                        show: true
	                    },
	                    saveAsImage: {//保存图片
	                        show: true
	                    },
	                    magicType: {//动态类型切换
	                        type: ['bar', 'line']
	                    },
				calculable : true,
			    xAxis : [//横坐标轴展示维度
			        {
			            type : 'category',
			            boundaryGap: false,
			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			        }
			    ],
			    yAxis : [//纵坐标轴展示数值维度
			        {
			            type : 'value'
			        }
			    ],
			    series : [//具体数据项，名称，图类型，坐标横纵轴数据
			        {
			            name:'营业库A',
			            type:'line',
			            data:[0, 0, 0, 0, 16970, 14747, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库B',
			            type:'line',
			            data:[0, 0, 0, 0, 18045, 15594, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库C',
			            type:'line',
			            data:[0, 0, 0, 0, 17468, 15024, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库D',
			            type:'line',
			            data:[0, 0, 0, 0, 17909, 15358, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'渠道资源库',
			            type:'line',
			            data:[0, 0, 0, 0, 19932, 19793, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        }
			    ]
			};
			//动态数据替换，后台查询拼装后，对数据进行替换动态变换展示
			if(json && json.data) {
				option.legend.data = json.data.legend;
				option.series = json.data.series;
				option.xAxis[0].data = json.data.xAxis;
				option.title.subtext=cache.deadline;
			}
			
			myChart.clear();//加载前数据刷新
			myChart.setOption(option);//渲染动态数据
			myChart.hideLoading();//隐藏loading
		
		
### 定时采集任务相关配置规定*
涉及表配置：  
select * from aiam.am_core_index;   //指标主表    
select * from aiam.am_core_index_ext;    //指标分表，指标执行sql配置    
select * from aiam.cfg_db_url;  //数据源配置tns    
select * from aiam.cfg_db_acct;  //用户名密码信息配置    
select * from aiam.cfg_db_relat;  //关联配置    
select * from aiam.cfg_task;   //配置task任务，task任务的实现类以及task执行时间频率    
select * from aiam.cfg_task_param_value;     //task任务id与指标group_id关联    
注意事项：（待补充。。。）     

## 三、代码标准
### 基础说明  
1.目前，平台后台开发分4层结构调用，当web服务区接收到request请求后，依次调用Controller层、Service层、Dao层和Entity层，最后操作到对应的表数据。实体类Entity层面可以ORM自动生成，也可以手动编写，注意要与数据库中的表名、表字段一一对应；数据库上, 目前我们的表是不存在外键的 (也不建议) , 所以ORM框架的mapping中在自动生成上面也就没有关联信息(既不存在@OneToMany等)    

2.DAO层,主要用于处理增删改查操作，采用了spring-data-jpa, hibernate, JPA规范, JPA规范具体的实现由hibernate完成, spring-data-jpa对hibernate进行使用层面的API进行了封装,所以对于业务开发人员, 我们直接接触的是spring-data-jpa提供的操作方式.spring-data-jpa 1.11.1版本文档.    

3.spring-data-jpa提供的DAO层的基类    

![Alt text](https://taoyf2012.github.io/doc/asiainfo/image/JpaRepository.png "Optional title")

### 零、基本操作原则

#### 操作类
    1.在不需要验证数据就操作数据的情况下, 请使用@Query @Modifying 这样的方式通过写HQL来解决.
    2.在需要验证的情况下, 在合理的编写代码成本和基本性能要求之内, 尽量使用JpaRepository中所提供的操作方法.
#### 查询类
    1.在合理的编写代码成本和基本性能要求之内, 尽量使用JpaRepository中所提供的查询方法.
    2.在上述无法满足情况下, 考虑使用定义方法的方式来执行查询HQL.
    3.定义方法不满足的情况下, 使用@Query来定义HQL和执行. – 这里先不要使用naviteSql了, 目前还无法将数据映射到一个对象中, 等后续再说.
    4.在以上都无法满足的情况下, 使用SearchAndPageRepository进行动态HQL查询和naviteSql查询.
    5.在以上都无法满足的情况下, 请提出来, 我们讨论.
### 一、单表操作

#### 实体类
由hibernate生成器生成.在hibernate.reveng.xml配置文件中配置好表名，运行maven build...(hibernate3:hbm2java)即可生成模型域对象；   

>	
	@Entity
	@Table(name="SYS_ROLE")
	public class SysRole  {
	     private long roleId;
	     private String code;
	     private String name;
	     private String notes;
	     private Byte state;
	     private Long doneCode;
	     private Date createDate;
	     private Date doneDate;
	     private Date validDate;
	     private Date expireDate;
	     private Long opId;
	     private Long orgId;
	}

#### dao类:DAO层的命名规范（模型域对象名称+Dao）   
1, 实现增删改查操作，继承spring-data-jpa的操作接口 JpaRepository  
>     
	public interface SysRoleDao extends JpaRepository<SysRole, Long>{
	
	}

由此, 就拥有就拥有CrudRepository, PagingAndSortingRepository, JpaRepository的基础的API.
* CrudRepository : 对save, delete, findOne, 这些基于单表的增删改查的方法.save保存是根据传入的实体类判断是新装还是修改.查询只是基于主键的简单查询
* PagingAndSortingRepository : 对查询进行增强, 对排序和分页进行支持.
* JpaRepository : 进一步增强单表的简单的单表的增删改查的方法.
2, 自定义根据方法名生成sql的查询方法
定义方法名基本按照find…By, read…By, query…By, count…By, and get…By定义, 同时在方法名中体现查询的属性名.

** 举例 **
>     
	public interface SysRoleDao extends JpaRepository<SysRole, Long>{
	  //接卸到sql : select * from SYS_ROLE r where r.name = ? and r.state = ?
	  List<SysRole> findByNameAndState(String name, Byte state);
	  ...
	}

** 方法名中关键字说明 **

官方文档位置 :[spring-data-jpa 1.11.1版本文档 -关键字说明.](https://docs.spring.io/spring-data/jpa/docs/1.11.1.RELEASE/reference/html/#jpa.query-methods.query-creation "Title")
这里只是摘抄一些常用的 :

|数据库中关键字|数据库中关键字|举例|对应sql|
|:---|:---:|:---:|:---:|
|AND|And|findByNameAndState(String name, Byte state)|…where name = ?1 and state = ?2|
|OR|Or|findByNameAndCode(String name, String code)|…where name = ?1 or code = ?2|
|LIKE|Like|findByNameAndCode(String name, String code)|…where name like ?1|
|IN|In|findByNameAndCode(String name, String code)|…where name in ?1|
|...|...|...|...|

其他详见官方文档
3, 自定义sql的查询方法 @Query
相当于给DAO的方法配置一句具体执行的sql.    
** 举例 **
>
	public interface SysRoleDao extends JpaRepository<SysRole, Long>{
	  //根据@Query中的sql执行
	  @Query("select r from SysRole r where r.state = ?1")
	  List<SysRole> findAll(Byte state);

	  //根据@Query中的sql执行
	  @Query("select r.state, count(1) as number from SysRole r where r.state = ?1 group by r.state")
	  List<Object[]> findAll(Byte state);
	  ...
	}

返回值说明 : 
返回的字段, 如果可以通过Entity类中的mapping注解找到对于的属性则能够返回对应的对象; 如果不能, 则只能定义Object数组来存放返回值. – 这个问题的优化, 等待后续解决. 

4, 自定义sql的执行方法 @Query @Modifying
由于JpaRepository的操作有限, 所以无法满足无穷的个性化操作, 所以可以通过写sql的方式来个性化修改,删除等类型的操作.
** 举例 **
>
	public interface SysRoleDao extends JpaRepository<SysRole, Long>{

	  //根据@Query中的sql执行
	  @Modifying
	  @Query("update SysRole r set r.state = ?2 where r.name = ?1")
	  void updateState(String name, Byte state);
	  ...
	}


5, 自定义基类 : SearchAndPageRepository, 用于动态sql的查询
有一种场景, 查询的条件是会变化的, 如果用穷举的方式来定义方法, 那么只会降低易用度. 所以增加这样的功能.

具体API说明
1.
>
	/**
	 * @Description: 根据条件信息, 查询单表信息
	 * @param cons 属性条件
	 * @return
	 */
	List<T> search(List<Condition> cons);
2.
>
	/**
	 * @Description: 根据条件和分页信息, 查询单表信息
	 * @param cons 属性条件
	 * @param pageable 分页信息
	 * @return
	 */
	Page<T> search(List<Condition> cons, Pageable pageable);
	
** 举例 **

>
	List<Condition> cons = new ArrayList<Condition>();

	if(StringUtils.isNoneBlank(testName)){
	  cons.add(new Condition("testName", "%".concat(testName).concat("%"), Condition.Type.LIKE));
	}
	if(sysId > 0){
	  cons.add(new Condition("sysId", sysId, Condition.Type.EQ));
	}
	if(sysSubId > 0){
	  cons.add(new Condition("sysSubId", sysSubId, Condition.Type.EQ));
	}
	if(funId > 0){
	  cons.add(new Condition("funId", funId, Condition.Type.EQ));
	}
	if(important > 0){
	  cons.add(new Condition("important", important, Condition.Type.EQ));
	}

	if(pageNumber <= 0){
	  pageNumber = 0;
	}else{
	  pageNumber--;
	}
	if(pageSize <= 0){
	  pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
	}

	Pageable pageable = new PageRequest(pageNumber, pageSize);
	Page<NaTestCase> result = testCaseDao.search(cons, pageable);
	
### 二, 多表查询
实体类
由hibernate生成器生成.
>
	@Entity
	@Table(name="SYS_ROLE")
	public class SysRole  {
	     private long roleId;
	     private String code;
	     private String name;
	     private String notes;
	     private Byte state;
	     private Long doneCode;
	     private Date createDate;
	     private Date doneDate;
	     private Date validDate;
	     private Date expireDate;
	     private Long opId;
	     private Long orgId;
	}

	@Entity
	@Table(name = "AIGA_ROLE_FUNC")
	public class AigaRoleFunc {
	    private long funcRoleTrlatId;
	    private Long funcId;
	    private Long roleId;
	}

	@Entity
	@Table(name="AIGA_FUNCTION")
	public class AigaFunction{
	     private long funcId;
	     private String funcCode;
	     private String name;
	}

这3个表时多对多的关系.
1, 自定义多表sql的查询方法 @Query    
** 举例 **
>
	public interface SysRoleDao extends JpaRepository<SysRole, Long>{
	  //根据@Query中的sql执行
	  @Query("select f from SysRole ro, AigaRoleFunc r, AigaFunction f where ro.roleId = r.roleId and r.funcId = f.funcId")
	  List<AigaFunction> findAllFunctions(String roleName);

	  //根据@Query中的sql执行
	  @Query("select ro,f from SysRole ro, AigaRoleFunc r, AigaFunction f where ro.roleId = r.roleId and r.funcId = f.funcId")
	  List<Object[]> findAllFunctions(String roleName);
	  //这个Object数字第一个是SysRole类, 第二个是AigaFunction类.
	  ...
	}
返回值说明 :
返回的字段, 如果可以通过Entity类中的mapping注解找到对于的属性则能够返回对应的对象; 如果不能, 则只能定义Object数组来存放返回值. – 这个问题的优化, 等待后续解决.

2, 自定义基类 : SearchAndPageRepository, 用于native sql的查询
有一种场景, 多表查询, 查询的条件还是变化的, 所以增加这样的功能.

具体API说明

使用参数的理由是 : 因为直接拼sql, 会有被sql注入的危险.

>
	//1,
	/**
	 * 根据原生SQL按照分页查询, 返回Page<Map<String, Object>>

	 * @param nativeSQL 原生sql
	 * @param parameters sql中的参数
	 * @param pageable
	 * @return
	 */
	Page<Map> searchByNativeSQL(String nativeSQL, List<Parameter> parameters, Pageable pageable);

	//使用
	List<Parameter> list =new ArrayList<Parameter>();
	StringBuilder sql = new StringBuilder("select au.*, ro.name from sys_role ro cross join aiga_author au where ro.role_id=au.role_id");

	sql.append(" and au.staff_id = :staff_id");
	list.add(new Parameter("staff_id", 11l));
	dao.searchByNativeSQL(sql.toString(), list, new PageRequest(1, 10)

	//2,
	/**
	 * 根据原生SQL查询, PageList<R> , R代表返回类型Class
	 * @param nativeSQL 原生sql
	 * @param parameters sql中的参数
	 * @param domainClass 返回对象类型
	 * @param pageable
	 * @return
	 */
	 <R> Page<R> searchByNativeSQL(String nativeSQL, List<Parameter> parameters, Class<R> domainClass, Pageable pageable);

	//使用
	List<Parameter> list =new ArrayList<Parameter>();
	StringBuilder sql = new StringBuilder("select au.*, ro.name from sys_role ro cross join aiga_author au where ro.role_id=au.role_id");
	sql.append(" and au.staff_id = :staff_id");
	list.add(new Parameter("staff_id", 11l));
	sql.toString(), list, AigaAuthorView.class, new PageRequest(1, 10)

	//AigaAuthorView
	@Data
	public class AigaAuthorView {
	    private Long roleAuthorId;
	    private long roleId;
	    private Long staffId;
	    private String name;
	    private char xxx;
	}
	// 这个类中属性, 来自各个entity中的属性.

### service层代码编写
这层代码，类名规范以Service结尾，最好继承一下BaseService，通常的代码逻辑放在这一层当中进行集中处理；
>
	@Service        /*供spring扫描的注解*/
	@Transactional  /*该注解表面，本service中的方法均会开启事务*/
	public class FunctionSv extends BaseService{
		public static final int TOP_PARENT_ID = 0;
		public static final int DEFAULTS_STATE_YES = 1;

		@Autowired  /*供spring注入依赖*/
		private AigaFunctionDao aigaFunctionDao;

		public List<AigaFunction> findFunctions() {
			return aigaFunctionDao.findAll();
		}

		public AigaFunction findOne(Long funcId) {
			if(funcId == null || funcId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcId");
			}
			return aigaFunctionDao.findOne(funcId);
		}
		
### controller层代码编写
这层代码，类名规范，以Controller结尾，进行处理后的数据封装返回；  
>
	@Controller     /*供spring mvc扫描的注解*/
	@Api(value = "RoleController", description = "角色相关api")
	public class RoleController {

		@Autowired
		private RoleSv roleSv;

	    	@Autowired
	    	private RoleFuncSv roleFuncSv;

		@RequestMapping(path = "/sys/role/list")
		public @ResponseBody JsonBean list(){
			JsonBean bean = new JsonBean();
			bean.setData(roleSv.findRoles());
			return bean;
		}

		@RequestMapping(path = "/sys/role/save")
		public @ResponseBody JsonBean save(RoleRequest roleRequest){
			roleSv.saveRole(roleRequest);
			return JsonBean.success;
		}
	}
