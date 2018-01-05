define(function(require, exports, module) {
	require("lib/ztree/3.5.28/js/jquery.ztree.excheck.min.js");
	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesIndexRView');
	    //分页根据条件查询功能点归属
    //问题展示
    srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
    //新增问题
    srvMap.add("saveQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/save");
    //修改问题
    srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update")
    //刪除問題
    srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/delete");
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
    //指标名称
    srvMap.add("fetchselectName", "", "archi/index/selectName");
    // 获取上线时间
    srvMap.add("onlineTimeFind", "", "archi/online/timeFind");
    //
    srvMap.add("findAllAmCores", "", "index/typein/findAllAmCores");
    srvMap.add("findAllAmCores2", "", "index/typein/findAllAmCores2");
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
		isOtherNode2:new Array(100)
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
            Rose.ajax.postJson(srvMap.get('findAllAmCores'), cmd, function(json, status) {
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
                                funcIdNum = treeNode.indexId;
                                var node =treeNode.getCheckStatus();

                                console.log(node);
                                console.log(funcIdNum);

                                //判断指标名称是否在同一个指标分组里面
                                if(Data.i==1){
                                	var pcmd = "indexId="+funcIdNum;
									Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfk'), pcmd, function(json, status) {
										if(status) {
											window.XMS.msgbox.hide();
											Data.isSame=json.data[0].groupId;
											Data.isParent=json.data[0].indexId;
											var parentNode = treeNode.getParentNode();
											Data.isOtherNode[Data.i] = parentNode;
										} else {
											XMS.msgbox.show(json.retMessage, 'error', 2000);
										}
						  			});
						  			Data.i++;
                                }else if(Data.i>=2){
                                	var pcmd = "indexId="+funcIdNum;
									Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfk'), pcmd, function(json, status) {
										if(status) {
											window.XMS.msgbox.hide();
											Data.isOtherSame[Data.i+1]=json.data[0].groupId;
											Data.isOtherParent[Data.i+1]=json.data[0].indexId;
											var parentNode = treeNode.getParentNode();
											Data.isOtherNode[Data.i]=parentNode;
										} else {
											XMS.msgbox.show(json.retMessage, 'error', 2000);
										}
						  			});
						  			Data.i++;
                                }
                                if(Data.isOtherSame[Data.i]==Data.isSame || Data.isOtherSame[Data.i]==Data.isOtherSame[Data.i-1]){
	                                if(node.checked==true){
	                                	if(1001<=funcIdNum<=2010){
	                                		Data.indexIds ="";
	                                		//调用后台接口查询indexIds 返回long[]
	                                		var lkcmd = "groupId="+ funcIdNum;
			                                Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfksb'), lkcmd, function(json, status) {
												if(status) {
													window.XMS.msgbox.hide();
													for(var i=0;i<json.data.length;i++){
														Data.indexIds += json.data[i].indexId+",";
														console.log(Data.indexIds);
														Data.indexId = Data.indexIds;
													}
												} else {
													XMS.msgbox.show(json.retMessage, 'error', 2000);
												}
								  			});
	                                	}else if(300001<=funcIdNum<=300010 || funcIdNum<=3000){
	                                		XMS.msgbox.show('您选择的指标范围太大，请选择二级、三级指标查询展示', 'error', 6000);
	                                	}
	                                	if(1001>funcIdNum || funcIdNum>2010){
			                                Data.indexId += funcIdNum + ",";
	                                	}
	                                }else{
	                                	var gg = Data.indexId.indexOf(funcIdNum);
	                                	if(gg>=0){
		                                	var tou = Data.indexId.substring(0,gg);
		                                	var one = funcIdNum.toString();
		                                	var pg = gg+one.length;
	                                		var wei = Data.indexId.substring(pg+1);
		                                	Data.indexId = tou + wei ;
	                                	}
	                                }
                                }else if(Data.isOtherSame[Data.i]==Data.isParent || Data.isOtherSame[Data.i]==Data.isOtherParent[Data.i-1]){
	                                if(node.checked==true){
	                                	if(1001<=funcIdNum<=2010){
	                                		Data.indexIds ="";
	                                		//调用后台接口查询indexIds 返回long[]
	                                		var lkcmd = "groupId="+ funcIdNum;
			                                Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexListfksb'), lkcmd, function(json, status) {
												if(status) {
													window.XMS.msgbox.hide();
													for(var i=0;i<json.data.length;i++){
														Data.indexIds += json.data[i].indexId+",";
														console.log(Data.indexIds);
														Data.indexId = Data.indexIds;
													}
												} else {
													XMS.msgbox.show(json.retMessage, 'error', 2000);
												}
								  			});
	                                	}else if(300001<=funcIdNum<=300010 || funcIdNum<=3000){
	                                		XMS.msgbox.show('您选择的指标范围太大，请选择二级、三级指标查询展示', 'error', 6000);
	                                	}
	                                	if(1001>funcIdNum || funcIdNum>2010){
			                                Data.indexId += funcIdNum + ",";
	                                	}
	                                }else if(node.checked==false){
	                                	var gg = Data.indexId.indexOf(funcIdNum);
	                                	if(gg>=0){
		                                	var tou = Data.indexId.substring(0,gg);
		                                	var one = funcIdNum.toString();
		                                	var pg = gg+one.length;
	                                		var wei = Data.indexId.substring(pg+1);
		                                	Data.indexId = tou + wei ;
	                                	}
	                                }
                                }else if(Data.isOtherSame[Data.i]!=Data.isSame || Data.isOtherSame[Data.i]!=Data.isOtherSame[Data.i-1]){
                                	var textModal = Page.findId('modall');
									textModal.off('shown.bs.modal').on('shown.bs.modal', function () {
										//是
										textModal.find("[name='pass']").off('click').on('click', function(){
											textModal.modal('hide');
										});
									});
									textModal.modal('show');
									var cancel = Data.isOtherNode[Data.i-2];
									cancel.checked=false;
									cancel.check_Child_State=0;
									//取消勾选全部节点
									var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTreeRR");
									treeObj.checkAllNodes(false);
                                	Data.indexId = '';
                                	Data.indexId = funcIdNum + ",";
                                }
                            }
                        }
                    };
                    $.fn.zTree.init($("#Tree_getRightTreeRR"), setting, json.data);
                    //调用树结构搜索，入参1、树结构容器 2、树搜索容器 3、搜索的key
                    Utils.zTreeSearchInit($("#Tree_getRightTreeRR"),$("#Tree_getRightTreeRRSearch"),'name');
                }
            });

        },
        getStaffRoleList: function() {
            var self = this;
            Rose.ajax.postJson(srvMap.get('fetchdistinct'), '', function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getIndexGroupList);
                    console.log(json.data)
                    $(Mod.getIndexGroupR).html(template(json.data));

                    Utils.eventTrClickIfChanged($(Dom.getRoleFuncTable),function(){
                        var _data = self.getCheckedRole();
                        var _indexGroup = _data.indexGroup;
                        var cmd = "indexGroup=" + _indexGroup;
                        Data.indexGroup = _indexGroup;
                        console.log(cmd);
                        self.getRoleFuncCheckedList(cmd);
                    })
                    
                    // 滚动条
                    $(Dom.getRoleFuncTable).parent().slimScroll({
                        "height": '500px'
                    });
                }
            });
        },
        getRoleFuncCheckedList: function(cmd) {
            var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTreeR");
            treeObj.checkAllNodes(false);
            Rose.ajax.postJson(srvMap.get('getAmCoreIndexList'), cmd, function(json, status) {
                if (status) {
                    var _json = json.data;
                    console.log(_json);
                    var zTree_Menu = $.fn.zTree.getZTreeObj("Tree_getRightTreeR");

                    for (i in _json) {
                        var node = zTree_Menu.getNodeByParam('indexId', _json[i].indexId);
                        zTree_Menu.checkNode(node, true); //指定选中ID的节点  
                        zTree_Menu.expandNode(node, true, false); //指定选中ID节点展开  
                    }

                }
            })
        },
        getCheckedRole: function() {
            // funcIdNum = treeNode.funcId;
            var _obj = $('#Page_getIndexGroupR').find("input[type='radio']:checked").parents("tr");
            var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTreeR");
            console.log(treeObj);
            var nodes = treeObj.getCheckedNodes(true);
            console.log(nodes);
            var indexIds = "";
            for (var i = 0; i < nodes.length; i++) {
                if (i == 0) {
                    indexIds = nodes[i].indexId;
                } else {
                    indexIds += ',' + nodes[i].indexId;
                }
            }
            console.log(indexIds);
            var _indexGroup = _obj.find("input[name='indexGroup']")
            var data = {
                indexGroup: "",
                indexIds: ""
            }
            if (_indexGroup.length == 0) {
                window.XMS.msgbox.show('请先选择一个角色！', 'error', 2000);
                return;
            } else {
                data.indexGroup = _indexGroup.val();
                data.indexIds = indexIds;
            }
            return data;
        },
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form,true);
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(this.formatDate(now));
			_form.find('input[name="endMonth"]').val(this.formatDate(now));
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {
				
				Page.findId('getDataMaintainListSec').attr({style:"display:display;height:460px;"});      
				Page.findId('sysMessageView').attr({style:"display:display"});      

				var cmd = _form.serialize();
				var _cmd = Page.findId('queryDataMaintainForm').serialize();
				if(Data.indexId){
					cmd += "&indexId=" + Data.indexId;
					_cmd += "&indexId=" + Data.indexId;
					cmd = cmd.substring(0,cmd.length-1);
					_cmd = cmd.substring(0,_cmd.length-1);
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
				self.getDataMaintainList(cmd);
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				if(cache.tableName){
					var task2 = "";
					if(cache.tableName=="ARCH_DB_CONNECT"){
						task2 = "listDbConnects2";
					}else if(cache.tableName=="ARCH_SRV_MANAGE"){
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
//			_queryBtn.click();
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
			var tcmd = _cmd.split(",")[0];
			Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexList2'), tcmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getAmCoreIndexList);
				_dom.find("[name='content']").html(template(json.data));
				cache.tableName = json.data[0].schId;
				cache.tableIndex= json.data[0].indexId;
				//美化单机
				Utils.eventTrClickCallback(_dom);
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
			option = {
				title : {
			        text: '指标情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	    orient: 'vertical', //注意
					    right:0,
					    top: 0, //注意
					    //bottom:0,
					    //left:0,
					    //width:200,
					    pagemode: true, //注意,自定义的字段，开启图例分页模式，只有orient: 'vertical'时才有效
					    height:"100%",
					    itemHeight:18,
					    itemWidth: 18,
					    textStyle: {
					        fontWeight: 'bolder',
					        fontSize: 12,
					        color:'#666666'
					    },
					    inactiveColor:'#aaa',
					    padding: [20, 30,20,2],
					    backgroundColor: 'rgba(0, 0, 0, 0)',
					    shadowColor: 'rgba(0, 0, 0, 0.5)',
					    shadowBlur: 5,
					    zlevel: 100,
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
			            type:'bar',
			            data:[0, 0, 0, 0, 16970, 14747, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库B',
			            type:'bar',
			            data:[0, 0, 0, 0, 18045, 15594, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库C',
			            type:'bar',
			            data:[0, 0, 0, 0, 17468, 15024, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库D',
			            type:'bar',
			            data:[0, 0, 0, 0, 17909, 15358, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'渠道资源库',
			            type:'bar',
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
						option.series[indexSeries].markLine = {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            };
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
				myChart.setOption(option);			
	        	var clickCount = 0;
				/*=====legend 的分页控制 事件=s===*/
		        var PageEvent = function (i) {
		            var percent = -i * 98 + '%';
		            myChart.setOption({
		                legend: {
		                    top: percent
		                }
		            });
		        };
		
		        if (option.legend.pagemode) {
		            $("#initLegengds").on('click', '.js-prePage', function () {
		
		                if (clickCount > 0) {
		                    clickCount = clickCount - 1;
		                    PageEvent(clickCount);
		                    //console.log(clickCount+'上一页');
		                    $('.js-prePage img').attr({'src': 'images/up-icon.png', 'title': '上一页'});
		                    $('.js-prePage img').css('cursor','pointer');
		                    //$('.js-nextPage img').attr('src','images/down-icon.png');
		                    if(clickCount==0){
		                        $('.js-prePage img').attr({'src': 'images/up-disable.png', 'title': '已经是第一页'});
		                        $('.js-prePage img').css('cursor','no-drop');
		                    }
		                } else {
		                    //console.log(clickCount+'已经是第一页');
		                    $('.js-prePage img').attr({'src': 'images/up-disable.png', 'title': '已经是第一页'});
		                    $('.js-prePage img').css('cursor','no-drop');
		                }
		            });
		            $("#initLegengds").on('click', '.js-nextPage', function () {
		                clickCount = clickCount + 1;
		                //console.log(clickCount);
		                PageEvent(clickCount);
		                $('.js-prePage img').attr({'src': 'images/up-icon.png', 'title': '上一页'});
		                $('.js-prePage img').css('cursor','pointer');
		            });
		        }
		        /*=====legend 的分页控制 事件=e===*/				
				window.onresize = myChart.resize;
  			});
			
		},
/* --------------------------------------------------PAGE--2--------------------------------------------------------------- */
		getRightTreeR2: function(cmd) {
            Rose.ajax.postJson(srvMap.get('findAllAmCores2'), cmd, function(json, status) {
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
                                funcIdNum = treeNode.indexId;
                                var node =treeNode.getCheckStatus();
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
                    //调用树结构搜索，入参1、树结构容器 2、树搜索容器 3、搜索的key
                    Utils.zTreeSearchInit($("#Tree_getRightTreeRR2"),$("#Tree_getRightTreeRR2Search"),'name');
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

				var cmd = _form.serialize();
				var _cmd = Page.findId('queryDataMaintainForm2').serialize();
				if(Data.indexId2){
					cmd += "&indexId=" + Data.indexId2;
					_cmd += "&indexId=" + Data.indexId2;
					cmd = cmd.substring(0,cmd.length-1);
					_cmd = cmd.substring(0,_cmd.length-1);
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
				self.getDataMaintainList2(cmd);
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
				//美化单机
				Utils.eventTrClickCallback(_domSec);
			}, _domPaginationSec);
		}
	};
	module.exports = Query;
});