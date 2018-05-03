define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "connectResource/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('connectResource');

	//查询接口
	srvMap.add("listTotalDbConnects", "", "arch/index/listTotalDbConnects");
	//查询接口
	srvMap.add("listTotalDbConnectsnew", "", "arch/index/listTotalDbConnectsnew");
	//查询接口
	srvMap.add("listDbConnectsTopnew", "", "arch/index/listDbConnectsTopnew");

	
	var cache = {
			datas : ""
		};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			this._time();
			//查询
			var _form = Page.findId('queryDataForm');
			var cmd = _form.serialize();
			this._getGridList(cmd);
			this._query_event();
			//helper 注册
			this._handlebar_help_register();
		},
		//初始化时间框
		_time:function(){			
			//初始化时间框
			function showMonthFirstDay() {     
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.yesterdayTime2str(date,"yyyy-MM-dd");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find("[name='insertTime']").val(Rose.date.yesterdayTime2str(new Date(),"yyyy-MM-dd"));
		},
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('numberFluctuationList');
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var stime = _cmd.substring(11,21);
			var _cmd2 = {
    			endMonth:stime,
    			indexId:[[1030001, 1030002, 1030022,1031001, 1031002, 1031022,1032001, 1032002, 1032022,1033001, 1033002, 1033022],
							[1030003, 1030004,1031003, 1031004,1032003, 1032004,1033003, 1033004],
							[1030005, 1030038,1031005, 1031038,1032005, 1032038,1033005, 1033038],
							[1030006, 1030007,1031006, 1031007,1032006, 1032007,1033006, 1033007],
							[1030008, 1030009,1031008, 1031009,1032008, 1032009,1033008, 1033009],
							[1030010, 1030011, 1030013,1031010, 1031011, 1031013,1032010, 1032011, 1032013,1033010, 1033011, 1033013],
							[1030014, 1030015,1031014, 1031015,1032014, 1032015,1033014, 1033015],
							[1030016,1031016,1032016,1033016],
							[1030017, 1030018,1031017, 1031018,1032017, 1032018,1033017, 1033018],
							[1030019,1031019,1032019,1033019],
							[1030020, 1030021,1031020, 1031021,1032020, 1032021,1033020, 1033021],
							[1030023, 1030024,1031023, 1031024,1032023, 1032024,1033023, 1033024],
							[1030025,1031025,1032025,1033025],
							[1030026, 1030028,1031026, 1031028,1032026, 1032028,1033026, 1033028],
							[1030027,1031027,1032027,1033027],
							[1030029,1031029,1032029,1033029],
							[1030030, 1030031, 1030032,1031030, 1031031, 1031032,1032030, 1032031, 1032032,1033030, 1033031, 1033032],
							[1030033, 1030034, 1030035, 1030036, 1030037, 1030042,1031033, 1031034, 1031035, 1031036, 1031037, 1031042,1032033, 1032034, 1032035, 1032036, 1032037, 1032042,1033033, 1033034, 1033035, 1033036, 1033037, 1033042]],
				indexName:["账户中心连接数", "融合CRM连接数", "客户中心连接数", "政企中心连接数", "流量中心连接数", "家庭订单子中心连接数", "资源中心连接数", "规则中心连接数","个人订单子中心连接数","通用中心连接数","政企订单子中心连接数","外围中心连接数","4A系统用户连接数","到期中心连接数","开通中心连接数","账管BOSS连接数","物联网中心连接数","其他渠道中心连接数"]
			};
			Rose.ajax.postJson(srvMap.get('listDbConnectsTopnew'),_cmd2,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('numberFluctuationTemp'));
					var tablebtn = _dom.find("[name='content']");
					tablebtn.html(template(json.data));
	        		Utils.eventTrClickCallback(_dom);
	        		//7------按钮
	        		tablebtn.find("[class='btn btn-primary btn-table-detail7']").off('click').on('click', function() {
	        			var db = $(this).attr("data-db");
	        			var time = $(this).attr("data-time");
						// 转换日期格式
						end = time.replace(/-/g, '/'); // "2010/08/01";
						// 创建日期对象
						var date = new Date(end);
						// -7 
						date.setDate(date.getDate() - 7);
						// 没有格式化的功能，只能一个一个取  // 因为js里month从0开始，所以要加1
						var start = date.getFullYear() + '-' + (parseInt(date.getMonth()) + 1) + '-' + date.getDate();
						var _ggcmd = {
							startMonth : start,
							endMonth : time,
							indexId : new Array()
						}
	        			if(db=='账户中心连接数'){
	        				_ggcmd.indexId=[1030001, 1030002, 1030022,1031001, 1031002, 1031022,1032001, 1032002, 1032022,1033001, 1033002, 1033022];
	        			}else if(db=='融合CRM连接数'){
	        				_ggcmd.indexId=[1030003, 1030004,1031003, 1031004,1032003, 1032004,1033003, 1033004];
	        			}else if(db=='客户中心连接数'){
	        				_ggcmd.indexId=[1030005, 1030038,1031005, 1031038,1032005, 1032038,1033005, 1033038];
	        			}else if(db=='政企中心连接数'){
	        				_ggcmd.indexId=[1030006, 1030007,1031006, 1031007,1032006, 1032007,1033006, 1033007];
	        			}else if(db=='流量中心连接数'){
	        				_ggcmd.indexId=[1030008, 1030009,1031008, 1031009,1032008, 1032009,1033008, 1033009];
	        			}else if(db=='家庭订单子中心连接数'){
	        				_ggcmd.indexId=[1030010, 1030011, 1030013,1031010, 1031011, 1031013,1032010, 1032011, 1032013,1033010, 1033011, 1033013];
	        			}else if(db=='资源中心连接数'){
	        				_ggcmd.indexId=[1030014, 1030015,1031014, 1031015,1032014, 1032015,1033014, 1033015];
	        			}else if(db=='规则中心连接数'){
	        				_ggcmd.indexId=[1030016,1031016,1032016,1033016];
	        			}else if(db=='个人订单子中心连接数'){
	        				_ggcmd.indexId=[1030017, 1030018,1031017, 1031018,1032017, 1032018,1033017, 1033018];
	        			}else if(db=='通用中心连接数'){
	        				_ggcmd.indexId=[1030019,1031019,1032019,1033019];
	        			}else if(db=='政企订单子中心连接数'){
	        				_ggcmd.indexId=[1030020, 1030021,1031020, 1031021,1032020, 1032021,1033020, 1033021];
	        			}else if(db=='外围中心连接数'){
	        				_ggcmd.indexId=[1030023, 1030024,1031023, 1031024,1032023, 1032024,1033023, 1033024];
	        			}else if(db=='4A系统用户连接数'){
	        				_ggcmd.indexId=[1030025,1031025,1032025,1033025];
	        			}else if(db=='到期中心连接数'){
	        				_ggcmd.indexId=[1030026, 1030028,1031026, 1031028,1032026, 1032028,1033026, 1033028];
	        			}else if(db=='开通中心连接数'){
	        				_ggcmd.indexId=[1030027,1031027,1032027,1033027];
	        			}else if(db=='账管BOSS连接数'){
	        				_ggcmd.indexId=[1030029,1031029,1032029,1033029];
	        			}else if(db=='物联网中心连接数'){
	        				_ggcmd.indexId=[1030030, 1030031, 1030032,1031030, 1031031, 1031032,1032030, 1032031, 1032032,1033030, 1033031, 1033032];
	        			}else if(db=='其他渠道中心连接数'){
	        				_ggcmd.indexId=[1030033, 1030034, 1030035, 1030036, 1030037, 1030042,1031033, 1031034, 1031035, 1031036, 1031037, 1031042,1032033, 1032034, 1032035, 1032036, 1032037, 1032042,1033033, 1033034, 1033035, 1033036, 1033037, 1033042];
	        			}
	        			var _7cmd = "startMonth="+_ggcmd.startMonth+"&endMonth="+_ggcmd.endMonth+"&indexId="+_ggcmd.indexId;
						Rose.ajax.postJson(srvMap.get("listTotalDbConnectsnew"), _7cmd, function(json, status) {
							if(status) {
								window.XMS.msgbox.hide();
								self._graphSec(7,json);
				        		var _modal = Page.findId('show7dayModal');
								_modal.modal('show');
								Utils.setSelectData(_modal);
								_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
								});	
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}
			  			});
	        		});
	        		//30------按钮
	        		tablebtn.find("[class='btn btn-primary btn-table-detail30']").off('click').on('click', function() {
						var db = $(this).attr("data-db");
	        			var time = $(this).attr("data-time");
						// 转换日期格式
						end = time.replace(/-/g, '/'); // "2010/08/01";
						// 创建日期对象
						var date = new Date(end);
						// -30
						date.setDate(date.getDate() - 30);
						// 没有格式化的功能，只能一个一个取  // 因为js里month从0开始，所以要加1
						var start = date.getFullYear() + '-' + (parseInt(date.getMonth()) + 1) + '-' + date.getDate();
						var _ggcmd = {
							startMonth : start,
							endMonth : time,
							indexId : new Array()
						}
	        			if(db=='账户中心连接数'){
	        				_ggcmd.indexId=[1030001, 1030002, 1030022,1031001, 1031002, 1031022,1032001, 1032002, 1032022,1033001, 1033002, 1033022];
	        			}else if(db=='融合CRM连接数'){
	        				_ggcmd.indexId=[1030003, 1030004,1031003, 1031004,1032003, 1032004,1033003, 1033004];
	        			}else if(db=='客户中心连接数'){
	        				_ggcmd.indexId=[1030005, 1030038,1031005, 1031038,1032005, 1032038,1033005, 1033038];
	        			}else if(db=='政企中心连接数'){
	        				_ggcmd.indexId=[1030006, 1030007,1031006, 1031007,1032006, 1032007,1033006, 1033007];
	        			}else if(db=='流量中心连接数'){
	        				_ggcmd.indexId=[1030008, 1030009,1031008, 1031009,1032008, 1032009,1033008, 1033009];
	        			}else if(db=='家庭订单子中心连接数'){
	        				_ggcmd.indexId=[1030010, 1030011, 1030013,1031010, 1031011, 1031013,1032010, 1032011, 1032013,1033010, 1033011, 1033013];
	        			}else if(db=='资源中心连接数'){
	        				_ggcmd.indexId=[1030014, 1030015,1031014, 1031015,1032014, 1032015,1033014, 1033015];
	        			}else if(db=='规则中心连接数'){
	        				_ggcmd.indexId=[1030016,1031016,1032016,1033016];
	        			}else if(db=='个人订单子中心连接数'){
	        				_ggcmd.indexId=[1030017, 1030018,1031017, 1031018,1032017, 1032018,1033017, 1033018];
	        			}else if(db=='通用中心连接数'){
	        				_ggcmd.indexId=[1030019,1031019,1032019,1033019];
	        			}else if(db=='政企订单子中心连接数'){
	        				_ggcmd.indexId=[1030020, 1030021,1031020, 1031021,1032020, 1032021,1033020, 1033021];
	        			}else if(db=='外围中心连接数'){
	        				_ggcmd.indexId=[1030023, 1030024,1031023, 1031024,1032023, 1032024,1033023, 1033024];
	        			}else if(db=='4A系统用户连接数'){
	        				_ggcmd.indexId=[1030025,1031025,1032025,1033025];
	        			}else if(db=='到期中心连接数'){
	        				_ggcmd.indexId=[1030026, 1030028,1031026, 1031028,1032026, 1032028,1033026, 1033028];
	        			}else if(db=='开通中心连接数'){
	        				_ggcmd.indexId=[1030027,1031027,1032027,1033027];
	        			}else if(db=='账管BOSS连接数'){
	        				_ggcmd.indexId=[1030029,1031029,1032029,1033029];
	        			}else if(db=='物联网中心连接数'){
	        				_ggcmd.indexId=[1030030, 1030031, 1030032,1031030, 1031031, 1031032,1032030, 1032031, 1032032,1033030, 1033031, 1033032];
	        			}else if(db=='其他渠道中心连接数'){
	        				_ggcmd.indexId=[1030033, 1030034, 1030035, 1030036, 1030037, 1030042,1031033, 1031034, 1031035, 1031036, 1031037, 1031042,1032033, 1032034, 1032035, 1032036, 1032037, 1032042,1033033, 1033034, 1033035, 1033036, 1033037, 1033042];
	        			}
	        			var _30cmd = "startMonth="+_ggcmd.startMonth+"&endMonth="+_ggcmd.endMonth+"&indexId="+_ggcmd.indexId;
						Rose.ajax.postJson(srvMap.get("listTotalDbConnectsnew"), _30cmd, function(json, status) {
							if(status) {
								window.XMS.msgbox.hide();
								self._graphSec(30,json);
				        		var _modal = Page.findId('show30dayModal');
								_modal.modal('show');
								Utils.setSelectData(_modal);
								_modal.off('shown.bs.modal').on('shown.bs.modal', function () {

								});
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}
			  			});
	        		});
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');			 
			Utils.setSelectData(_form);		 
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();				
				self._getGridList(cmd);
			});		
        },   
        //
        _handlebar_help_register: function() {
			Handlebars.registerHelper("changePowerSty",function(value) {
				if(value>=0) {
					return 'change-font-green';
				} else if(value<0){
					return 'change-font-red';
				}else{
					return '';
				}
			});
			Handlebars.registerHelper("pesentAdd",function(value) {
				return value+"%";
			});
			Handlebars.registerHelper("addColor",function(value) {
				if(value=='优秀') {
					return 'change-font-green';
				} else if(value=='良好'){
					return 'change-font-yellow';
				} else if(value=='较差'){
					return 'change-font-orange';
				} else if(value=='危险'){
					return 'change-font-red';
				}else{
					return '';
				}
			});
		},
		_graphSec: function(day,json) {
			var echartsinitdom = null;
			if(day==7){
				echartsinitdom = "archiIndex7View";
			}else if(day==30){
				echartsinitdom = "archiIndex30View";
			}
			var myChart = echarts.init(Page.findId(echartsinitdom)[0]);
            myChart.showLoading({
                text: '读取数据中...' //loading，是在读取数据的时候显示
            });
                
			var option = {
				title : {
			        text: '指标情况',
			        subtext: '数据采集截止时间：XX月XX日XX:XX'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	y:'bottom',
                    type: 'scroll',
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
/*                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top: 40,
                    bottom: 20,
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']*/
			    },
			    toolbox: {
			        show : true,
			        x:120,
        			y:0,
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
				        dataView: { //数据视图
	                        show: true
	                    },
	                    myButtons:{//自定义按钮 danielinbiti,这里增加，selfbuttons可以随便取名字    
		                   show:true,//是否显示    
		                   title:'切换图例位置', //鼠标移动上去显示的文字    
		                   icon:'path://M432.45,595.444c0,2.177-4.661,6.82-11.305,6.82c-6.475,0-11.306-4.567-11.306-6.82s4.852-6.812,11.306-6.812C427.841,588.632,432.452,593.191,432.45,595.444L432.45,595.444z M421.155,589.876c-3.009,0-5.448,2.495-5.448,5.572s2.439,5.572,5.448,5.572c3.01,0,5.449-2.495,5.449-5.572C426.604,592.371,424.165,589.876,421.155,589.876L421.155,589.876z M421.146,591.891c-1.916,0-3.47,1.589-3.47,3.549c0,1.959,1.554,3.548,3.47,3.548s3.469-1.589,3.469-3.548C424.614,593.479,423.062,591.891,421.146,591.891L421.146,591.891zM421.146,591.891', //图标    
		                   option:{},
		                   onclick:function(param) {//点击事件,这里的param是chart的信息 底下有option   	
	                   			if(option.legend.orient) {
		                   			delete option.legend.orient;
		                   			delete option.legend.right;
		                   			delete option.legend.top;
		                   			option.legend.y='bottom';
		                   		} else {
		                   			option.legend.orient = 'vertical';
		                   			option.legend.right = 20;
		                   			option.legend.top = 40;
		                   		}
		                   		myChart.clear();
		                   		myChart.setOption(option);
		                   }    
		                }
			        }
			    },
				calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap: false,
			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
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
			Rose.ajax.postJson(srvMap.get("onlineTimeFind"), '', function(onlinejson, status) {
				if(status) {
					window.XMS.msgbox.hide();
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
				if(json && json.data) {
					option.legend.data = json.data.legend;
					option.series = json.data.series;
					if(json.data.xAxis) {
						var onlinePonint = new Array(100);
						for(var indexXAxis in json.data.xAxis){
							for(var indexOnline in onlinejson.data){
								if(json.data.xAxis[indexXAxis]==onlinejson.data[indexOnline]){
									json.data.xAxis[indexXAxis] += "上线";
									onlinePonint.push(indexXAxis);
								}
							}
						}
						option.xAxis[0].data = json.data.xAxis;
					}
					for(var indexSeries in option.series) {
						var markData = [];
						for(var indexPoint in onlinePonint){
							var pzs = parseInt(onlinePonint[indexPoint]);
                			var plan =  {name : '上线',value:option.series[indexSeries].data[pzs], xAxis: pzs, yAxis: option.series[indexSeries].data[pzs]};
	                		markData.push(plan);
						}
			            option.series[indexSeries].markPoint = {
			                data : markData
			            };
					}
					option.title.subtext=cache.deadline;
				}
				//加载前数据刷新
				myChart.clear();
				myChart.setOption(option);	
				myChart.hideLoading();//隐藏loading

				window.onresize = myChart.resize;
				Page.findId(echartsinitdom).resize(function(){
	                myChart.resize();             
	            });

  			});			
		}
	};
				 
	module.exports = init;
	
});