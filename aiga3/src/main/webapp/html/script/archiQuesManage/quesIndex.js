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

	var Data = {
		queryListCmd: null
	}

	var Query = {
		init: function() {
			
			// 默认查询所有
			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			//映射
			this.hdbarHelp();
			//
			this._querydomain();
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getDataMaintainList(cmd);
			});

		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getAmCoreIndexList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getAmCoreIndexList);
				_dom.find("[name='content']").html(template(json.data));
				//美化单机
				Utils.eventTrClickCallback(_dom);
				//新增
				self.addDataMaintain();
				//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_dom, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_dom);

//					alert(data.quesId);
					self.updateDataMaintain(data.quesId, json.data);
				});
			}, _domPagination);
			
			var _domSec = Page.findId('getDataMaintainListSec');
			var _domPaginationSec = _domSec.find("[name='paginationSec']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getArchDbConnectList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getArchDbConnectList);
				_domSec.find("[name='content']").html(template(json.data));
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
		
		_querydomain: function() {
			var self = this;
			var myChart = echarts.init(Page.findId('archiIndexView')[0]);
			//柱状 模版
//			self._graphfir(myChart);
			//柱状堆叠模版
			self._graphSec(myChart);
		},
		
		_graphfir: function(myChart) {
			option = {
			    title : {
			        text: '相关系统域变更情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['业务支撑域','大数据域','公共域','网络域','管信域','地市域','安全域','总量']
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
			            name:'业务支撑域',
			            type:'bar',
			            data:[2.0, 4.9, 7.0, 23.2, 500, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
			        },
			        {
			            name:'大数据域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'公共域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'网络域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'管信域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'地市域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        }
			    ]
			};
			myChart.setOption(option);
		},
		
		_graphSec: function(myChart) {
			option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
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
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
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
			myChart.setOption(option);
		}
		
	};
	module.exports = Query;
});