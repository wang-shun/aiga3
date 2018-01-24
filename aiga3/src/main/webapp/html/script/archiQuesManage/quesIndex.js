define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesIndexView');
    //指标主表
    srvMap.add("getAmCoreIndexList", "", "archi/index/list");
    //指标分表
    srvMap.add("getArchDbConnectList", "", "archi/dbconnect/list");
    //指标分表---table
    srvMap.add("listDbConnects", "", "arch/index/listDbConnects");
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
    srvMap.add("onlineTimeFind", "", "webservice/archiOnline/timeFind");
    srvMap.add("fetchselectKey1", "", "archi/index/selectKey1");
    srvMap.add("fetchselectKey2", "", "archi/index/selectKey2");
    srvMap.add("fetchselectKey3", "", "archi/index/selectKey3");
    srvMap.add("fetchselectKey123", "", "archi/index/selectKey123");

	// 模板对象
	var Tpl = {
		getQuestionInfoList: require('tpl/archiQuesManage/quesTemplate.tpl'),
		getAmCoreIndexList: require('tpl/archiQuesManage/AmCoreIndex.tpl'),
		getArchDbConnectList: require('tpl/archiQuesManage/ArchDbConnect.tpl')

	};

	/*// 容器对象
	var Dom = {
		queryDataMaintainForm: '#JS_queryDataMaintainForm',
		getDataMaintainList: '#JS_getDataMaintainList',
		addDataMaintainModal: "#JS_addDataMaintainModal",
		addDataMaintainInfo: "#JS_addDataMaintainInfo",
		updateDataMaintainModal: "#JS_updateDataMaintainModal",
		updateMaintainInfo: "#JS_updateDataMaintainInfo",
	};*/
	var cache = {
		datas : "",
		tableName : "",
		tableName2 : "",
		tableIndex:""
	};
	
	var Data = {
		queryListCmd: null
	};

	var Query = {
		init: function() {
			//判断是否查询key1/key2/key3
			this.judgeIndexName();
			// 初始化查询表单
			this.queryDataMaintainForm();
			this.queryDataMaintainForm2();
			//映射
			this.hdbarHelp();
		},
		//判断下拉框indexName值
		judgeIndexName: function(){
			$("#indexName").unbind('click');
			$("#indexName").bind('click',function(){
				var checkValue=$("#indexName").val();  //获取Select选择的Value 
			});
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var init = true;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form,true);
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(this.formatMonthFirst(now));
			_form.find('input[name="endMonth"]').val(this.formatDate(now));
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {		
				Page.findId('getDataMaintainListSec').attr({style:"display:display"});      
				Page.findId('sysMessageView').attr({style:"display:display"});      
				var cmd = _form.serialize();
				var _cmd = Page.findId('queryDataMaintainForm').serialize();
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
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;

			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//隐藏的主表获取分表表名tableName;
			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexList'), _cmd, function(json, status) {
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
			var task = 'listDbConnects';
			if(cache.tableName){
				switch(cache.tableName){
            		case "ARCH_DB_CONNECT":
            			task = 'listDbConnects';
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
				//新增
				self.addDataMaintain();
				//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_domSec, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_domSec);
					self.updateDataMaintain(data.quesId, json.data);
				});
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
		formatMonthFirst: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				year = d.getFullYear(), 
				day = '01';
			if (month.length < 2) month = '0' + month;
			return [year, month, day].join('-');	
		},
		formatMonthFirst2: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				year = d.getFullYear(), 
				day = '01';
			if (month.length < 2) month = '0' + month;
			return [year, month].join('-');	
		},
		//新增数据维护
		addDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _addBt = _dom.find("[name='add']");
			_addBt.unbind('click');
			_addBt.bind('click', function() {
				//alert(Page.findModal('addDataMaintainModal').html());
				Page.findModal('addDataMaintainModal').modal('show');
				Page.findModal('addDataMaintainModal').on('hide.bs.modal', function() {
					Utils.resetForm(Page.findId('addDataMaintainInfo'));
				});
				var _form = Page.findId('addDataMaintainInfo');
				Utils.setSelectData(_form);
				var _saveBt = Page.findModal('addDataMaintainModal').find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('saveQuestionInfo'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								setTimeout(function() {
									self.getDataMaintainList();
								}, 1000);
								// 关闭弹出层
								Page.findModal('addDataMaintainModal').modal('hide');
							}
						});
					});
				});
			});
		},
		//删除数据备份
		delDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'quesId=' + data.quesId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		updateDataMaintain: function(Id, json) {
			var self = this;
			var i=0;
			while(json[i].quesId != Id){
				i++;
			}
			var data = json[i];
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			var html = "<input readonly='readonly' type='text' class='form-control' value='" + Id + "' />";
			_dom.find("#JS_name").html(html);

			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd = _cmd + "&quesId=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.getJson(srvMap.get('updateQuestionInfo'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('更新成功！', 'success', 2000);
						setTimeout(function() {
							self.queryDataMaintainForm(Data.queryListCmd);
							_dom.modal('hide');
						}, 1000)
					}
				});
			});

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
		// 事件：分页
		initPaging: function(obj, length) {
			obj.find("table").DataTable({
				"iDisplayLength": length,
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": true,
				"autoWidth": false
			});
		},
		
/*		getIndexEcharts: function() {
			var self = this;
			var init = true;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {
				var _cmd = Page.findId('queryDataMaintainForm').serialize();
				if(init) {
					var date = self.formatDate(new Date()); 		
					_cmd = 'startMonth='+date+'&endMonth='+date;
					init = false;			
				}
				if(_cmd.indexOf('startMonth=&')>-1) {
					XMS.msgbox.show('请输入开始时间！', 'error', 2000);
					return
				}
				if(_cmd.indexOf('endMonth=&')>-1) {
					XMS.msgbox.show('请输入结束时间！', 'error', 2000);
					return
				}
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get("listDbConnects2"), _cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.hide();
						self._graphSec(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
//			_queryBtn.click();
		},*/
		
		_graphSec: function(json) {
			var myChart = echarts.init(Page.findId('archiIndexView')[0]);
			myChart.showLoading({
                text: '读取数据中...' //loading，是在读取数据的时候显示
            });
			option = {
				title : {
			        text: '指标情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
					y:'bottom',
                    type: 'scroll',
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
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
		                },  
			            saveAsImage : {show: true}
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
/*						option.series[indexSeries].markLine = {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            };*/
						var markData = [];
/*	                    for(var onlineIndex in onlinejson.data) {
	                    	var onlineDate = onlinejson.data[onlineIndex];
	                    	var datePosition = json.data.xAxis.indexOf(onlineDate);
	                    	if(datePosition) {
	                    		var plan =  {name : '上线', xAxis: datePosition, yAxis: option.series[indexSeries].data[datePosition]};
	                    		markData.push(plan);
	                    	}                    
	                    }*/
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
				myChart.clear();
				myChart.setOption(option);
				myChart.hideLoading();//隐藏loading
				window.onresize = myChart.resize;
  			});
			
		},
/* --------------------------------------------------PAGE--2--------------------------------------------------------------- */
		// 按条件查询
		queryDataMaintainForm2: function() {
			var self = this;
			var init = true;
			var _form = Page.findId('queryDataMaintainForm2');
			Utils.setSelectDataPost(_form,true);
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(this.formatMonthFirst2(now));
			_form.find('input[name="endMonth"]').val(this.formatDate2(now));
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {		
				Page.findId('getDataMaintainListSec2').attr({style:"display:display"});      
				Page.findId('sysMessageView2').attr({style:"display:display"});      
				var cmd = _form.serialize();
				var _cmd = Page.findId('queryDataMaintainForm2').serialize();
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
/*					switch(cache.tableName){
						case "ARCH_DB_CONNECT":
							task2 = "listDbConnects2";
							break;
						case "ARCH_SRV_MANAGE":
							task2 = "listSrvManages2";
							break;
					}*/
/*					if(cache.tableName=="ARCH_DB_CONNECT"&&cache.tableIndex==2){
						task2 = "listDbConnects22";
					}else if(cache.tableName=="ARCH_DB_CONNECT"&&cache.tableIndex!=2){
						task2 = "listDbConnects2";
					}else if(cache.tableName=="ARCH_SRV_MANAGE"){
						task2 = "listSrvManages2";
					}*/
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
						self._graphSec2(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
		},
		// 查询数据维护
		getDataMaintainList2: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;

			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//隐藏的主表获取分表表名tableName;
			var _dom = Page.findId('getDataMaintainList2');
			var _domPagination = _dom.find("[name='pagination']");
			Rose.ajax.postJsonSync(srvMap.get('getAmCoreIndexList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getAmCoreIndexList);
				_dom.find("[name='content']").html(template(json.data));
				cache.tableName2 = json.data[0].schId;
				cache.tableIndex= json.data[0].indexId;
				//美化单机
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);
			
			var _domSec = Page.findId('getDataMaintainListSec2');
			var _domPaginationSec = _domSec.find("[name='paginationSec2']");
			// 设置服务器端分页listDbConnects
			var task = 'listMonthIndex';
			if(cache.tableName2){
				switch(cache.tableName2){
					case "ARCH_MONTH_INDEX":
						task = 'listMonthIndex';
						break;
            		case "ARCH_DB_CONNECT":
            			task = 'listDbConnects';
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
				//新增
				self.addDataMaintain();
				//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_domSec, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_domSec);
					self.updateDataMaintain(data.quesId, json.data);
				});
			}, _domPaginationSec);
		},
		_graphSec2: function(json) {
			var myChart = echarts.init(Page.findId('archiIndexView2')[0]);
			myChart.showLoading({
                text: '读取数据中...' //loading，是在读取数据的时候显示
            });
			option = {
				title : {
			        text: '指标情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
					y:'bottom',
                    type: 'scroll',
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            myButtons:{//自定义按钮,这里增加必须以my开头
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
		                },  
			            saveAsImage : {show: true}

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
						option.xAxis[0].data = json.data.xAxis;
					}
					for(var indexSeries in option.series) {
/*						option.series[indexSeries].markLine = {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            };*/
						var markData = [];
	                    for(var onlineIndex in onlinejson.data) {
	                    	var onlineDate = onlinejson.data[onlineIndex];
	                    	var datePosition = json.data.xAxis.indexOf(onlineDate);
	                    	if(datePosition) {
	                    		var plan =  {name : '上线', xAxis: datePosition, yAxis: option.series[indexSeries].data[datePosition]};
	                    		markData.push(plan);
	                    	}                    
	                    }
			            option.series[indexSeries].markPoint = {
			                data : markData
			            };
					}
				}
				myChart.clear();
				myChart.setOption(option);
				myChart.hideLoading();//隐藏loading
				window.onresize = myChart.resize;
  			});		
		}
	};
	module.exports = Query;
});