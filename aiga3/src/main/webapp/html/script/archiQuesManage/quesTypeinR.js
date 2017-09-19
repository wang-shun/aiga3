define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesTypeinRView');
    srvMap.add("getQueryByCondition", pathAlias+"getSysMessageList.json", "archi/event/queryByCondition");
    //选择分表
    srvMap.add("distinctdbname", "", "archi/index/distinctdbname");
    //分表查询
    srvMap.add("queryDbsByCondition", "", "index/typein/queryDbsByCondition");
    //分表新增
    srvMap.add("saveDbs", "", "index/typein/saveDbs");
    //分表删除
    srvMap.add("deleteDbs", "", "index/typein/deleteDbs");
    //分表修改
    srvMap.add("updateDbs", "", "index/typein/updateDbs");
    //分表查询
    srvMap.add("querySrvsByCondition", "", "index/typein/querySrvsByCondition");
    //分表新增
    srvMap.add("saveSrvs", "", "index/typein/saveSrvs");
    //分表删除
    srvMap.add("deleteSrvs", "", "index/typein/deleteSrvs");
    //分表修改
    srvMap.add("updateSrvs", "", "index/typein/updateSrvs");
    //分表查寻
    srvMap.add("findAllAmCoreExts", "", "index/typein/findAllAmCoreExts");
    //分表查寻
    srvMap.add("queryAmCoreExts", "", "index/typein/queryAmCoreExts");
    //分表新增
    srvMap.add("saveAmCoreExts", "", "index/typein/saveAmCoreExts");
    //分表修改
    srvMap.add("updateAmCoreExts", "", "index/typein/updateAmCoreExts");
    //分表删除
    srvMap.add("deleteAmCoreExts", "", "index/typein/deleteAmCoreExts");
	var cache = {
		datas : ""	,
		tableName:"",
	};
    // 模板对象
	var Tpl = {
		AmCoreIndexExt: require('tpl/archiQuesManage/AmCoreIndexExt.tpl'),
	};
	var Data = {
		queryListCmd: null
	};

	var Query = {
		init: function() {
			this.initialise();
			// 默认查询所有
			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			//映射
			this.hdbarHelp();
		},
		initialise: function(){
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
		},

		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectDataPost(_form,true);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click').bind('click', function() {
				Utils.checkForm(_form, function() {
					var _cmd = _form.serialize();
					console.log(_cmd);				
					Rose.ajax.postJson(srvMap.get('queryAmCoreExts'), _cmd, function(json, status) {
						if (status) {
							setTimeout(function() {
								self.getDataMaintainList(_cmd);
							}, 1000);
						}
					});
				});	
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
			Utils.getServerPage(srvMap.get('queryAmCoreExts'), _cmd, function(json, status) {//getQuestionInfoList
				window.XMS.msgbox.hide();
				var template = Handlebars.compile(Tpl.AmCoreIndexExt);
				_dom.find("[name='content']").html(template(json.data.content));
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

					self.updateDataMaintain(data.indexId, json.data);
				});
			}, _domPagination);
		},
		//新增数据维护
		addDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _addBt = _dom.find("[name='add']");

			_addBt.unbind('click');
			_addBt.bind('click', function() {
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
						Rose.ajax.postJson(srvMap.get('saveAmCoreExts'), _cmd, function(json, status) {
							if (status) {
								XMS.msgbox.show('新增记录成功！！', 'success', 2000);
								setTimeout(function() {
									self.getDataMaintainList();
								}, 1000);
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
					var cmd = 'indexId=' + data.indexId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleteAmCoreExts'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self.getDataMaintainList();
							}, 1000);
						}
					});
				}
			});
		},
		updateDataMaintain: function(indexId, json) {		
			var self = this;
			var i=0;
			while(json.content[i].indexId != indexId){
				i++;
			}
			var data = json.content[i];
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd = _cmd + "&indexId=" + indexId;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('updateAmCoreExts'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('更新成功！', 'success', 2000);
						setTimeout(function() {
							self.getDataMaintainList();
							_dom.modal('hide');
						}, 1000);
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
		hdbarHelp: function() {},

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
		}
	};
	module.exports = Query;
});