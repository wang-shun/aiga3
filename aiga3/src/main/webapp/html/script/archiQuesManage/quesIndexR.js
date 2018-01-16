define(function(require, exports, module) {
	require("lib/ztree/3.5.28/js/jquery.ztree.excheck.min.js");
	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesIndexRView');
	//分页根据条件查询功能点归属
    //指标主表
    srvMap.add("getAmCoreIndexList", "", "archi/index/list");
    srvMap.add("getAmCoreIndexListfk", "", "archi/index/list2");
    srvMap.add("getAmCoreIndexListfksb", "", "archi/index/list3");
    //指标主表
    srvMap.add("getAmCoreIndexList2", "", "archi/index/listidx");
    //指标分表
    srvMap.add("getArchDbConnectList", "", "archi/dbconnect/list");
    //指标分表---table
    srvMap.add("listDbConnects", "", "arch/index/listDbConnects");
    //指标分表---table
    srvMap.add("listDbConnects22", "", "arch/index/listDbConnects22");
    //指标分表---echarts
    srvMap.add("listDbConnects2", "", "arch/index/listDbConnects2");
    //指标分表---table
    srvMap.add("listSrvManages", "", "arch/index/listSrvManages");
    //指标分表---echarts
    srvMap.add("listSrvManages2", "", "arch/index/listSrvManages2");
    //指标月份分表---table
    srvMap.add("listMonthIndex", "", "arch/index/listMonthIndex");
    //指标月份分表---echarts
    srvMap.add("listMonthIndex2", "", "arch/index/listMonthIndex2");
    //日指标分组
    srvMap.add("fetchdistinct", "", "archi/index/distinct");
    //月指标分组
    srvMap.add("fetchdistinctMonth", "", "archi/index/distinctMonth");
    // 获取上线时间
    srvMap.add("onlineTimeFind", "", "archi/online/timeFind");
    //
//    srvMap.add("findAllAmCores", "", "index/typein/findAllAmCores");
//    srvMap.add("findAllAmCores2", "", "index/typein/findAllAmCores2");
    //八大军规指标树 按日
    srvMap.add("findAllAmCoresByDay", "", "index/tree/findAllIndexByDay");
    //八大军规指标树 按月
    srvMap.add("findAllAmCoresByMonth", "", "index/tree/findAllIndexByMonth");
    //八大军规指标树 总数柱状图db_connect
    srvMap.add("listTotalDbConnects", "", "arch/index/listTotalDbConnects");
    //多个
    srvMap.add("listTotalDbConnectsOrderByGroupId", "", "arch/index/listTotalDbConnectsOrderByGroupId");

    
    //八大军规指标树 总数柱状图srv_manage
    srvMap.add("listTotalSrvManages", "", "arch/index/listTotalSrvManages");
    //八大军规指标树 总数饼状图 db_connect
    srvMap.add("listTotalDbConnectsPie", "", "arch/index/listTotalDbConnectsPie");
    //八大军规指标树 总数饼状图 srv_manage
    srvMap.add("listTotalSrvManagesPie", "", "arch/index/listTotalSrvManagesPie");
    //八大军规指标树 总数饼状图 month_index
    srvMap.add("listTotalMonthIndexPie", "", "arch/index/listTotalMonthIndexPie");
    
	// 模板对象
	var Tpl = {
		getQuestionInfoList: require('tpl/archiQuesManage/quesTemplate.tpl'),
		getAmCoreIndexList: require('tpl/archiQuesManage/AmCoreIndex.tpl'),
		getArchDbConnectList: require('tpl/archiQuesManage/ArchDbConnect.tpl')

	};

	var cache = {
		datas : "",
		tableName : "",
		tableName2 : "",
		tableIndex:""
	};
	var Tpl = {
        getIndexGroupList: require('tpl/archiQuesManage/getIndexGroupList.tpl'),
        getAmCoreIndexList: require('tpl/archiQuesManage/AmCoreIndex.tpl'),
		getQuestionInfoList: require('tpl/archiQuesManage/quesTemplate.tpl'),
		getArchDbConnectList: require('tpl/archiQuesManage/ArchDbConnect.tpl')
    };
    var Mod = {
        getIndexGroupR: '#Page_getIndexGroupR'
    };
    var Dom = {
        getRoleFuncTable: "#JS_getRoleFuncTable",
        rolefuncUpdate: "#JS_rolefuncUpdate"
    };
	var Data = {
		queryListCmd: null,
		indexId:"",
		indexId2:"",
		isSame:"",
		isOtherSame: new Array(100),
		isParent:"",
		isOtherParent: new Array(100),
		isSame2:"",
		isOtherSame2: new Array(100),
		isParent2:"",
		isOtherParent2: new Array(100),
		i:1,
		j:1,
		indexIds:"",
		indexIds2:"",
		isOtherNode:new Array(100),
		isOtherNode2:new Array(100),
		gpindexIds:"",
		flag : true ,
		isOne : true ,
	};

	var Query = {
		init: function() {
			//按日查询 树
			this.getRightTreeR();
			//按月查询 树
			this.getRightTreeR2();
			// 初始化查询表单
			this.queryDataMaintainForm();
			this.queryDataMaintainForm2();
			//映射
			this.hdbarHelp();
		},
		getRightTreeR: function(cmd) {
            Rose.ajax.postJson(srvMap.get('findAllAmCoresByDay'), cmd, function(AllAmCoresJson, status) {
                if (status) {
                    //checkbox代码块
                    var setting = {
                        check: {
                            enable: true,
                            chkStyle: "checkbox",
                            chkboxType: { "Y": "p", "N": "ps" }
                        },
                        data: {
                            key: {
                                name: "indexName"
                            },
                            simpleData: {
                                enable: true,
                                idKey: "indexId",
                                pIdKey: "groupId"
                            }
                        },
                        callback: {
                            onCheck: function(event, treeId, treeNode) {
                                funcIdNum = treeNode.indexId;
                                var node =treeNode.getCheckStatus();
                                console.log(node);
                                console.log(funcIdNum);
                            }
                        }
                    };
                    $.fn.zTree.init($("#Tree_getRightTreeRR"), setting, AllAmCoresJson.data);
                }
            });
        },
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form,true);
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(Utils.showMonthFirstDay());
			_form.find('input[name="endMonth"]').val(this.formatDate(now));
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {				
                Page.findId('getDataMaintainListSec').attr({style:"display:display;height:460px;"});      
				Page.findId('sysMessageView').attr({style:"display:display"});  
				Page.findId('totalMessageView').attr({style:"display:display"});  
				var _cmd = _form.serialize();	
				var _groupcmd = {
					startMonth : _cmd.substring(11,21),
					endMonth : _cmd.substring(31,41),
					indexId : new Array()
				}
				var command = $.fn.zTree.getZTreeObj("Tree_getRightTreeRR").getCheckedNodes();
				var indexIds ='';
//				var tArray = new Array();   //先声明一维

				var groupId = new Set();
				var lastFatherId = 0;
				var lastFatherGroupId = 0;
				var lastNode ={};
				var secondLastClassIdList = [];
				var secondLastClassNameList = new Array();
				var secondLastClassNodes = new Array();
				var lastClassIdList = [];
				var lastClassNameList = new Array();
				var lastClassNodes = new Array();
				console.log(command);
				if(command){
					for(var i in command){
						//过滤所有父节点
						if(command[i].indexId<=1000000){
							//选取多个一级二级节点时 过滤 （比较无意义）
							if(command[i].indexId<=10){
							}else if(command[i].indexId>=10001 && command[i].indexId<=10005){
								secondLastClassIdList.push(command[i].indexId);
								secondLastClassNameList.push(command[i].indexName);
								secondLastClassNodes.push(command[i]);
							}else if((command[i].indexId>=100001 && command[i].indexId<=100077)||(command[i].indexId>=1000 && command[i].indexId<=2010)){
								lastClassIdList.push(command[i].indexId);
								lastClassNameList.push(command[i].indexName);
								lastClassNodes.push(command[i]);	
							}
							lastFatherId = command[i].indexId;
							lastFatherGroupId = command[i].groupId;
							lastNode = command[i];
							continue;
						}else{
							Data.flag = true;
							//过滤出最后一层indexId字符串并用","分割；
							indexIds += command[i].indexId + ",";
							//根据groupId判断指标是同组，否弹框提示取消勾选所有节点；
							groupId.add(command[i].groupId);
							if(groupId.size>=2){
							    var textModal = Page.findId('modall');
								textModal.off('shown.bs.modal').on('shown.bs.modal', function () {
									//是
									textModal.find("[name='pass']").off('click').on('click', function(){
										textModal.modal('hide');
									});
								});
								textModal.modal('show');
								//取消勾选全部节点
								var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTreeRR");
								treeObj.checkAllNodes(false);
							}
						}						
					}
					
					//如果有子节点 查询~
					if(indexIds != ''){
						_cmd += "&indexId=" + indexIds;
						_cmd = _cmd.substring(0,_cmd.length-1);
					}
					
					//如果没有子节点，过滤出倒数第二层
					if(indexIds == ''){
						Data.flag = false;
						//倒数第二层
//						debugger
						if((100001<=lastFatherId && lastFatherId<=100077) || (1001<=lastFatherId && lastFatherId<=2010 && lastFatherId != 1002)){
							//如果同层节点仅有一个
							if(lastClassNodes.length==1){
								Data.isOne = true;
								var childrencommand = lastNode.children;
								for(var x in childrencommand){
									indexIds += childrencommand[x].indexId + ",";
								}
								_cmd += "&indexId=" + indexIds;
								_cmd = _cmd.substring(0,_cmd.length-1);
							//如果同层节点有多个
							}else if(lastClassNodes.length>=2){
								Data.isOne = false;
								for(var ii in lastClassNodes){
									_groupcmd.indexId[ii]=new Array();    //声明二维，每一个一维数组里面的一个元素都是一个数组
									var brothercommand = lastClassNodes[ii];
									var brotherchildrencommand = brothercommand.children;
									for(var x in brotherchildrencommand){
										//indexIds += brotherchildrencommand[x].indexId + ",";
										_groupcmd.indexId[ii][x] = brotherchildrencommand[x].indexId;
									}
								}
								_cmd += "&indexId=" + _groupcmd.indexId;
							}
						//倒数第三层
						}else if(10001<=lastFatherId && lastFatherId<=10004){
							if(secondLastClassNodes.length==1){
								Data.isOne = true;
								var childrencommand = lastNode.children;
								for(var x in childrencommand){
									if(childrencommand[x].children){
										var secondchildrencmd = childrencommand[x].children;
										for(var y in secondchildrencmd){
											indexIds += secondchildrencmd[y].indexId + ",";
										}
									}
								}
								_cmd += "&indexId=" + indexIds;
								_cmd = _cmd.substring(0,_cmd.length-1);
							//如果同层节点有多个
							}else if(secondLastClassNodes.length>=2){
								Data.isOne = false;
								for(var ii in secondLastClassNodes){
									_groupcmd.indexId[ii]=new Array();    //声明二维，每一个一维数组里面的一个元素都是一个数组
									var brothercommand = secondLastClassNodes[ii];
									var brotherchildrencmd = brothercommand.children;
									for(var x in brotherchildrencmd){
										if(brotherchildrencmd[x].children){
											var secondchildrencmd = brotherchildrencmd[x].children;
											for(var y in secondchildrencmd){
												//indexIds += secondchildrencmd[y].indexId + ",";
												_groupcmd.indexId[ii][y] = brotherchildrencommand[y].indexId;
											}
										}
									}
								}
								_cmd += "&indexId=" + _groupcmd.indexId;
							}
						//倒数第四层//1002
						}else if(lastFatherId == 1002){
							var childrencommand = lastNode.children;
							for(var x in childrencommand){
								if(childrencommand[x].children){
									var secondchildrencmd = childrencommand[x].children;
									for(var y in secondchildrencmd){
										if(secondchildrencmd[y].children){
											var thirdchildrencmd = secondchildrencmd[y].children;
											for(var z in thirdchildrencmd){
												indexIds += thirdchildrencmd[z].indexId + ",";
											}
										}
									}
								}
							}
							_cmd += "&indexId=" + indexIds;
							_cmd = _cmd.substring(0,_cmd.length-1);	
						}else{//一级目录提示 选择二三子目录！
							XMS.msgbox.show('请选择二级、三级子目录！', 'error', 2000);
							return
						}
					}
				}
				//
/*				if(Data.gpindexIds){
					_cmd += "&indexId=" + Data.indexId;
					_cmd = _cmd.substring(0,_cmd.length-1);
				}*/
				if(_cmd.indexOf('indexGroup=&')>-1) {
					XMS.msgbox.show('请选择指标组！', 'error', 2000);
					return
				}
				if(_cmd.indexOf('startMonth=&')>-1) {
					XMS.msgbox.show('请输入开始时间！!!!!!!!', 'error', 2000);
					return
				}
				if(_cmd.indexOf('endMonth=&')>-1) {
					XMS.msgbox.show('请输入结束时间！', 'error', 2000);
					return
				}
				self.getDataMaintainList(_cmd);
				
				var _ggcmd = _cmd;	
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				if(cache.tableName){
					var task2 = "";
					var taskPie = "";
					if(cache.tableName=="ARCH_DB_CONNECT"){
						if(Data.flag==true){
							task2 = "listDbConnects2";
						}else if(Data.flag==false && Data.isOne==true){
							task2 = "listTotalDbConnects";
						}else if(Data.flag==false && Data.isOne==false){
							task2 = "listTotalDbConnectsOrderByGroupId";
							_ggcmd = _groupcmd;
						}
						taskPie = "listTotalDbConnectsPie";
					}else if(cache.tableName=="ARCH_SRV_MANAGE"){
						if(Data.flag==true){
							task2 = "listSrvManages2";
						}else{
							task2 = "listTotalSrvManages"
						}
						taskPie = "listTotalSrvManagesPie";
					}else if(cache.tableName2=="ARCH_MONTH_INDEX"){
						task2 = "listMonthIndex2";
						taskPie = "listTotalMonthIndexPie";
					}
				}
				Rose.ajax.postJson(srvMap.get(task2), _ggcmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.hide();
						self._graphSec(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
				Rose.ajax.postJson(srvMap.get(taskPie), _cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.hide();
						self._graphTotal(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;

			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//隐藏的主表获取分表表名tableName;
			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
//			var tcmd = _cmd.split(",")[0];
			var tcmd = "indexId=" + _cmd.substring(_cmd.length-7,_cmd.length);
			Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexList2'), tcmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
					var template = Handlebars.compile(Tpl.getAmCoreIndexList);
					_dom.find("[name='content']").html(template(json.data));
					cache.tableName = json.data[0].schId;
					cache.tableIndex= json.data[0].indexId;
					//美化单机
					Utils.eventTrClickCallback(_dom);
				}
			}, _domPagination);
			
			var _domSec = Page.findId('getDataMaintainListSec');
			var _domPaginationSec = _domSec.find("[name='paginationSec']");
			// 设置服务器端分页listDbConnects
			var task = 'listDbConnects22';
			if(cache.tableName){
				switch(cache.tableName){
            		case "ARCH_DB_CONNECT":
            			task = 'listDbConnects22';
            			break;
            		case "ARCH_SRV_MANAGE":
            			task = 'listSrvManages';
            			break;
            		case "ARCH_MONTH_INDEX":
						task = 'listMonthIndex';
						break;
            	}
			}
			Utils.getServerPage(srvMap.get(task), _cmd, function(json, status) {//getArchDbConnectList
				cache.datas = json.data;
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getArchDbConnectList);
				//按月份排序
				json.data.content = json.data.content.sort(function(a,b){return a.settMonth - b.settMonth;});
				for(var i=0;i<json.data.content.length;i++){
					if(json.data.content[i].key3!=null){
						json.data.content[i].key3="("+json.data.content[i].key3+")";
					}
				};
				_domSec.find("[name='content']").html(template(json.data.content));
				//美化单机
				Utils.eventTrClickCallback(_domSec);
			}, _domPaginationSec);
		},
		//时间格式化
		formatDate: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear(); 
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [year, month, day].join('-');	
		},
		formatDate2: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear(); 
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [year, month].join('-');	
		},
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tbody tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		},
		//映射处理
		hdbarHelp: function() {
			Handlebars.registerHelper("transformatImp", function(value) {
                if (value == 'ZJCRMA') {
                    return "营业库A";
                } else if (value == 'ZJCRMB') {
                    return "营业库B";
                } else if (value == 'ZJCRMC') {
                    return "营业库C";
                } else if (value == 'ZJCRMD') {
                    return "营业库D";
                } else if (value == 'ZJRES') {
				    return "渠道资源库";
				} else if (value == 'ZJCSF') {
                    return "CSF库";
                } else if (value == 'ZJPUB') {
                    return "公共库";
                } else if (value == 'ZJXLOG') {
                    return "日志库";
                }
            });         
		},		
		_graphSec: function(json) {
			var myChart = echarts.init(Page.findId('archiIndexView')[0]);
			var option = {
				title : {
			        text: '指标情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top: 40,
                    bottom: 20,
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
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
	                    }
			        }
			    },
				calculable : true,
			    xAxis : [
			        {
			            type : 'category',
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
				}
				//加载前数据刷新
				myChart.clear();
				myChart.setOption(option);				
				window.onresize = myChart.resize;
  			});			
		},
		//汇总饼状图
		_graphTotal: function(json) {
			var myChart = echarts.init(Page.findId('totalArchiIndexView')[0]);
			var data = genData(50);
			var option = {
			    title : {
			        text: '数据库连接接入情况分布图',
			        subtext: '所查询时间段内总数及占比',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        type: 'scroll',
			        orient: 'vertical',
			        right: 30,
			        top: 20,
			        bottom: 20,
			        data: data.legendData
			    },
			    series : [
			        {
			            name: '姓名',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '50%'],
			            data: data.seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
			function genData(count) {
			    var nameList = [
			        '赵', '钱', '孙', '李', '周', '吴', '郑', '王', '冯', '陈', '褚', '卫', '蒋', '沈', '韩', '杨', '朱', '秦', '尤', '许', '何', '吕', '施', '张', '孔', '曹', '严', '华', '金', '魏', '陶', '姜', '戚', '谢', '邹', '喻', '柏', '水', '窦', '章', '云', '苏', '潘', '葛', '奚', '范', '彭', '郎', '鲁', '韦', '昌', '马', '苗', '凤', '花', '方', '俞', '任', '袁', '柳', '酆', '鲍', '史', '唐', '费', '廉', '岑', '薛', '雷', '贺', '倪', '汤', '滕', '殷', '罗', '毕', '郝', '邬', '安', '常', '乐', '于', '时', '傅', '皮', '卞', '齐', '康', '伍', '余', '元', '卜', '顾', '孟', '平', '黄', '和', '穆', '萧', '尹', '姚', '邵', '湛', '汪', '祁', '毛', '禹', '狄', '米', '贝', '明', '臧', '计', '伏', '成', '戴', '谈', '宋', '茅', '庞', '熊', '纪', '舒', '屈', '项', '祝', '董', '梁', '杜', '阮', '蓝', '闵', '席', '季', '麻', '强', '贾', '路', '娄', '危'
			    ];
			    var legendData = [];
			    var seriesData = [];
			    for (var i = 0; i < 50; i++) {
			        name = Math.random() > 0.65
			            ? makeWord(4, 1) + '·' + makeWord(3, 0)
			            : makeWord(2, 1);
			        legendData.push(name);
			        seriesData.push({
			            name: name,
			            value: Math.round(Math.random() * 100000)
			        });
			    }
			
			    return {
			        legendData: legendData,
			        seriesData: seriesData
			    };
			
			    function makeWord(max, min) {
			        var nameLen = Math.ceil(Math.random() * max + min);
			        var name = [];
			        for (var i = 0; i < nameLen; i++) {
			            name.push(nameList[Math.round(Math.random() * nameList.length - 1)]);
			        }
			        return name.join('');
			    }
			}
			if(json && json.data) {
				option.legend.data = json.data.legendData;
				option.series[0].data = json.data.seriesData;
			}
			myChart.clear();
			myChart.setOption(option);			
			window.onresize = myChart.resize;
		},
/* --------------------------------------------------PAGE--2--------------------------------------------------------------- */
		getRightTreeR2: function(cmd) {
            Rose.ajax.postJson(srvMap.get('findAllAmCoresByMonth'), cmd, function(json, status) {
                if (status) {
                    console.log(json.data)
                    //checkbox代码块
                    var setting = {
                        check: {
                            enable: true,
                            chkStyle:"checkbox"
                        },
                        data: {
                            key: {
                                name: "indexName"
                            },
                            simpleData: {
                                enable: true,
                                idKey: "indexId",
                                pIdKey: "groupId"
                            }
                        },
                        callback: {
                            onCheck: function(event, treeId, treeNode) {
                                var funcIdNum = treeNode.indexId;
                                var node =treeNode.getCheckStatus();
                                if(!node) {
                                	//清除存储的参数
                                	return;
                                }
                                console.log(node);
                                console.log(funcIdNum);
                                //判断指标名称是否在同一个指标分组里面
                                if(Data.j==1){
                                	var pcmd = "indexId="+funcIdNum;
									Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfk'), pcmd, function(json, status) {
										if(status) {
											window.XMS.msgbox.hide();
											Data.isSame2=json.data[0].groupId;
											Data.isParent2=json.data[0].indexId;
											var parentNode = treeNode.getParentNode();
                                            Data.isOtherNode2[Data.j] = parentNode;
										} else {
											XMS.msgbox.show(json.retMessage, 'error', 2000);
										}
						  			});
						  			Data.j++;
                                }else if(Data.j>=2){
                                	var pcmd = "indexId="+funcIdNum;
									Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfk'), pcmd, function(json, status) {
										if(status) {
											window.XMS.msgbox.hide();
											Data.isOtherSame2[Data.j+1]=json.data[0].groupId;
											Data.isOtherParent2[Data.j+1]=json.data[0].indexId;
											var parentNode = treeNode.getParentNode();
                                            Data.isOtherNode2[Data.j]=parentNode;
										} else {
											XMS.msgbox.show(json.retMessage, 'error', 2000);
										}
						  			});
						  			Data.j++;
                                }
                                if(Data.isOtherSame2[Data.j]==Data.isSame2 || Data.isOtherSame2[Data.j]==Data.isOtherSame2[Data.j-1]){
	                                if(node.checked==true){
	                                	if(1001<=funcIdNum<=2010){
	                                		Data.indexIds2 ="";
	                                		//调用后台接口查询indexIds 返回long[]
	                                		var lkcmd = "groupId="+ funcIdNum;
			                                Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfksb'), lkcmd, function(json, status) {
												if(status) {
													window.XMS.msgbox.hide();
													for(var i=0;i<json.data.length;i++){
														Data.indexIds2 += json.data[i].indexId+",";
														console.log(Data.indexIds2);
														Data.indexId2 = Data.indexIds2;
													}
												} else {
													XMS.msgbox.show(json.retMessage, 'error', 2000);
												}
								  			});
                                        }else if(300001<=funcIdNum<=300010 || funcIdNum<=3000){
	                                		XMS.msgbox.show('您选择的指标范围太大，请选择二级、三级指标查询展示', 'error', 6000);
	                                	}
	                                	if(1001>funcIdNum || funcIdNum>2010){
			                                Data.indexId2 += funcIdNum + ",";
	                                	}
	                                }else{
	                                	var gg = Data.indexId2.indexOf(funcIdNum);
	                                	if(gg>=0){
		                                	var tou = Data.indexId2.substring(0,gg);
		                                	var one = funcIdNum.toString();
		                                	var pg = gg+one.length;
	                                		var wei = Data.indexId2.substring(pg+1);
		                                	Data.indexId2 = tou + wei ;
	                                	}
	                                }
                                }else if(Data.isOtherSame2[Data.j]==Data.isParent2 || Data.isOtherSame2[Data.j]==Data.isOtherParent2[Data.j-1]){
	                                if(node.checked==true){
	                                	if(1001<=funcIdNum<=2010){
	                                		Data.indexIds2 ="";
	                                		//调用后台接口查询indexIds 返回long[]
	                                		var lkcmd = "groupId="+ funcIdNum;
			                                Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfksb'), lkcmd, function(json, status) {
												if(status) {
													window.XMS.msgbox.hide();
													for(var i=0;i<json.data.length;i++){
														Data.indexIds2 += json.data[i].indexId+",";
														console.log(Data.indexIds2);
														Data.indexId2 = Data.indexIds2;
													}
												} else {
													XMS.msgbox.show(json.retMessage, 'error', 2000);
												}
								  			});
	                                	}else if(300001<=funcIdNum<=300010 || funcIdNum<=3000){
	                                		XMS.msgbox.show('您选择的指标范围太大，请选择二级、三级指标查询展示', 'error', 6000);
	                                	}
	                                	if(1001>funcIdNum || funcIdNum>2010){
			                                Data.indexId2 += funcIdNum + ",";
	                                	}
	                                }else if(node.checked==false){
	                                	var gg = Data.indexId2.indexOf(funcIdNum);
	                                	if(gg>=0){
		                                	var tou = Data.indexId2.substring(0,gg);
		                                	var one = funcIdNum.toString();
		                                	var pg = gg+one.length;
	                                		var wei = Data.indexId2.substring(pg+1);
		                                	Data.indexId2 = tou + wei ;
	                                	}
	                                }
                                }else if(Data.isOtherSame2[Data.j]!=Data.isSame2 || Data.isOtherSame2[Data.j]!=Data.isOtherSame2[Data.j-1]){
                                	var textModal = Page.findId('modall');
									textModal.off('shown.bs.modal').on('shown.bs.modal', function () {
										//是
										textModal.find("[name='pass']").off('click').on('click', function(){
											textModal.modal('hide');
										});
									});
									textModal.modal('show');
									var cancel = Data.isOtherNode2[Data.j-2];
                                    cancel.checked=false;
                                    cancel.check_Child_State=0;
									//取消勾选全部节点
									var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTreeRR2");
									treeObj.checkAllNodes(false);
                                	Data.indexId2 = '';
                                	Data.indexId2 = funcIdNum + ",";
                                }
                            }
                        }
                    };
                    $.fn.zTree.init($("#Tree_getRightTreeRR2"), setting, json.data);
                }
            });
        },
		// 按条件查询
		queryDataMaintainForm2: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm2');
			Utils.setSelectDataPost(_form,true);
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(this.formatDate2(now));
			_form.find('input[name="endMonth"]').val(this.formatDate2(now));
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {			
				Page.findId('getDataMaintainListSec').attr({style:"display:display;height:460px;"});      
				Page.findId('sysMessageView').attr({style:"display:display"});      
				Page.findId('totalMessageView').attr({style:"display:display"});      
				var _cmd = _form.serialize();
				if(Data.indexId2){
					_cmd += "&indexId=" + Data.indexId2;
					_cmd = _cmd.substring(0,_cmd.length-1);
				}
				if(_cmd.indexOf('indexGroup=&')>-1) {
					XMS.msgbox.show('请选择指标组！', 'error', 2000);
					return
				}
				if(_cmd.indexOf('startMonth=&')>-1) {
					XMS.msgbox.show('请输入开始时间！', 'error', 2000);
					return
				}
				if(_cmd.indexOf('endMonth=&')>-1) {
					XMS.msgbox.show('请输入结束时间！', 'error', 2000);
					return
				}
				self.getDataMaintainList2(_cmd);
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				if(cache.tableName2){
					var task2 = "listMonthIndex2";
					if(cache.tableName2=="ARCH_DB_CONNECT"){
						task2 = "listDbConnects2";
					}else if(cache.tableName2=="ARCH_SRV_MANAGE"){
						task2 = "listSrvManages2";
					}else if(cache.tableName2=="ARCH_MONTH_INDEX"){
						task2 = "listMonthIndex2";
					}
				}
				Rose.ajax.postJson(srvMap.get(task2), _cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.hide();
						self._graphSec(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
		},
		// 查询数据维护
		getDataMaintainList2: function(cmd) {
			var self = this;
			var _cmd = cmd || '';
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//隐藏的主表获取分表表名tableName;
			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexList2'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getAmCoreIndexList);
				_dom.find("[name='content']").html(template(json.data));
				cache.tableName2 = json.data[0].schId;
				cache.tableIndex= json.data[0].indexId;
				//美化单机
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);
			
			var _domSec = Page.findId('getDataMaintainListSec');
			var _domPaginationSec = _domSec.find("[name='paginationSec']");
			// 设置服务器端分页listDbConnects
			var task = 'listMonthIndex';
			if(cache.tableName2){
				switch(cache.tableName2){
					case "ARCH_MONTH_INDEX":
						task = 'listMonthIndex';
						break;
            		case "ARCH_DB_CONNECT":
            			task = 'listDbConnects22';
            			break;
            		case "ARCH_SRV_MANAGE":
            			task = 'listSrvManages';
            			break;
            	}
			}
			Utils.getServerPage(srvMap.get(task), _cmd, function(json, status) {//getArchDbConnectList
				cache.datas = json.data;
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getArchDbConnectList);
				//按月份排序
				json.data.content = json.data.content.sort(function(a,b){return a.settMonth - b.settMonth;});
				for(var i=0;i<json.data.content.length;i++){
					if(json.data.content[i].key3!=null){
						json.data.content[i].key3="("+json.data.content[i].key3+")";
					}
				};
				_domSec.find("[name='content']").html(template(json.data.content));
				//美化单checkbox
				Utils.eventTrClickCallback(_domSec);
			}, _domPaginationSec);
		}
	};
	module.exports = Query;
});