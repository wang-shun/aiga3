define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesIndexView');
	//分页根据条件查询功能点归属
	srvMap.add("getDataMaintainList", pathAlias + "dataMaintain.json", "sys/property/getPropertyCorrelationList");
	//新增备份
	srvMap.add("addDataMaintain", pathAlias + "retMessage.json", "sys/property/addPropertyCorrelation");
	//删除备份
	srvMap.add("delDataMaintain", pathAlias + "retMessage.json", "sys/property/delPropertyCorrelation");
	//修改备份
	srvMap.add("updateDataMaintain", pathAlias + "retMessage.json", "sys/property/updatePropertyCorrelation");
	//属性下拉菜单
	srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");
	//数据库下拉菜单
	srvMap.add("getDbList", pathAlias + "retMessage.json", "sys/property/getDbList");
	//cfgId下拉菜单

	srvMap.add("getCfgIdList", pathAlias + "retMessage.json", "sys/property/getCigIdList");

	srvMap.add("getPropertyConfigList", pathAlias + "propertyConfig.json", "sys/property/getPropertyFieldList");

	//问题展示
	srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
	//新增问题
	srvMap.add("saveQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/save");
	//修改问题
	srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update")
	//刪除問題
	srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/delete");
    //问题分类下拉框
    srvMap.add("getRootList", "", "sys/cache/listRootid");
    //一级分类下拉框
    srvMap.add("getFirstList", "", "sys/cache/listFirstid");
    //二级分类下拉框
    srvMap.add("getSecondList", "", "sys/cache/listSecondid");
    //三级分类下拉框
    srvMap.add("getThirdList", "", "sys/cache/listThirdid");
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
    //
    srvMap.add("fetchindexGroup", "", "sys/maplist/indexGroup");
    srvMap.add("fetchindexName", "", "sys/maplist/indexName");
    srvMap.add("fetchkey1", "", "sys/maplist/key1");
    srvMap.add("fetchdistinct", "", "archi/index/distinct");
    srvMap.add("fetchselectName", "", "archi/index/selectName");
    srvMap.add("fetchselectKey123", "", "archi/index/selectKey123");
    
	// 模板对象
	var Tpl = {
		//getDataMaintainTemp: $('#JS_getDataMaintainTemp'),
		getQuestionInfoList: require('tpl/archiQuesManage/quesTemplate.tpl'),
		getAmCoreIndexList: require('tpl/archiQuesManage/AmCoreIndex.tpl'),
		getArchDbConnectList: require('tpl/archiQuesManage/ArchDbConnect.tpl')
		//modifyQuesIdentifiedInfo: $("#TPL_modifyQuesIdentifiedInfo").html()

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
			//映射
			this.hdbarHelp();
			//
//			this.getIndexEcharts();
		},
		//判断下拉框indexName值
		judgeIndexName: function(){
			$("#indexName").unbind('click');
			$("#indexName").bind('click',function(){
				var checkValue=$("#indexName").val();  //获取Select选择的Value 
				if(checkValue=="系统模块数据库连接"){
					$("#categoryKey1").attr({style:"display:display"}); 
					$("#categoryKey2").attr({style:"display:display"});      
					$("#categoryKey3").attr({style:"display:display"});           
				}else{
					$("#categoryKey1").attr({style:"display:none"}); 
					$("#categoryKey2").attr({style:"display:none"});       
					$("#categoryKey3").attr({style:"display:none"}); 
				}
			});
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var init = true;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {
				var cmd = _form.serialize();
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
				self.getDataMaintainList(cmd);
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				var task2 = "listDbConnects2";
				if(cache.tableName){
					switch(cache.tableName){
						case "ARCH_DB_CONNECT":
							task2 = "listDbConnects2";
							break;
						case "ARCH_SRV_MANAGE":
							task2 = "listSrvManages2";
							break;
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
			_queryBtn.click();
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
//					alert(data.quesId);
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
					//alert(cmd);//////////
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
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
//			var _dom = Page.findModal('updateDataMaintainModal');
			
//			var index = _dom.attr("temp");
//			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
//			Page.findId('updateModal').html(template(json.data[index]));
//			var _modal = Page.findId('updateDataMaintainModal');
//			_modal.modal('show');
//			Utils.setSelectData(_modal);
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			
//			_dom.modal('show');
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
						window.XMS.msgbox.show('更新成功！', 'success', 2000)
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
			option = {
				title : {
			        text: '架构问题指标情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis',
			    },
			    legend: {
					y:'bottom',
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
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
			            data:[0, 0, 0, 0, 16970, 14747, 4012, 0, 0, 0, 0, 0]
			        },
			        {
			            name:'营业库B',
			            type:'bar',
			            data:[0, 0, 0, 0, 18045, 15594, 4012, 0, 0, 0, 0, 0]
			        },
			        {
			            name:'营业库C',
			            type:'bar',
			            data:[0, 0, 0, 0, 17468, 15024, 4012, 0, 0, 0, 0, 0]
			        },
			        {
			            name:'营业库D',
			            type:'bar',
			            data:[0, 0, 0, 0, 17909, 15358, 4012, 0, 0, 0, 0, 0]
			        },
			        {
			            name:'渠道资源库',
			            type:'bar',
			            data:[0, 0, 0, 0, 19932, 19793, 4012, 0, 0, 0, 0, 0],
			        }
			    ]
			};
			if(json && json.data) {
				option.legend.data = json.data.legend;
				option.series = json.data.series;
				if(json.data.xAxis) {
					option.xAxis[0].data = json.data.xAxis;
				}
			}
			myChart.setOption(option);
			window.onresize = myChart.resize;
		}
		
	};
	module.exports = Query;
});