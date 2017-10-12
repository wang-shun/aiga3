define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesTypeinView');
    //级联查询
    srvMap.add("getQueryQuesInfo", "", "archi/question/queryInfo");
    //静态数据  
	srvMap.add("staticEventState", pathAlias+"getSysMessageList.json", "archi/static/eventState");
	//显示系统信息表
    srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
    
    srvMap.add("getEventFindALL", pathAlias+"getSysMessageList.json", "archi/event/findAll");
    srvMap.add("getEventFindALLByPage", pathAlias+"getSysMessageList.json", "archi/event/findAllByPage");
    srvMap.add("getEventSave", pathAlias+"getSysMessageList.json", "archi/event/save");
    srvMap.add("getEventDelete", pathAlias+"getSysMessageList.json", "archi/event/delete");
    srvMap.add("getEventUpdate", pathAlias+"getSysMessageList.json", "archi/event/update");
    srvMap.add("getQueryByCondition", pathAlias+"getSysMessageList.json", "archi/event/queryByCondition");
    //指标分组
    srvMap.add("fetchdistinctsec", "", "archi/index/distinctsec");
    //日指标分组
    srvMap.add("fetchdistinct", "", "archi/index/distinct");
    //月指标分组
    srvMap.add("fetchdistinctMonth", "", "archi/index/distinctMonth");
    //指标名称
    srvMap.add("fetchselectName", "", "archi/index/selectName");
    //指标主表
    srvMap.add("getAmCoreIndexListByPage", "", "archi/index/listByPage");
    //指标分表
    srvMap.add("getArchDbConnectList", "", "archi/dbconnect/list");
    //主表新增
    srvMap.add("saveAmCores", "", "index/typein/saveAmCores");
    //主表修改
    srvMap.add("updateAmCores", "", "index/typein/updateAmCores");
    //主表删除
    srvMap.add("deleteAmCores", "", "index/typein/deleteAmCores");
	var cache = {
		datas : ""	
	};
    // 模板对象
	var Tpl = {
		getQuestionInfoList: $('#TPL_getSysMessageList'),
		getAmCoreIndexList: require('tpl/archiQuesManage/AmCoreIndex.tpl'),
		getArchDbConnectList: require('tpl/archiQuesManage/ArchDbConnect.tpl')
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
					//XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					if(_cmd!=null){
						if(_cmd.charAt(_cmd.length-1)=='=') {
							_cmd=_cmd.replace("groupId=","groupId=0");
						}
					}
					self.getDataMaintainList(_cmd);
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
			Utils.getServerPage(srvMap.get('getAmCoreIndexListByPage'), _cmd, function(json, status) {//getQuestionInfoList
				window.XMS.msgbox.hide();
				var template = Handlebars.compile(Tpl.getAmCoreIndexList);
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
						Rose.ajax.postJson(srvMap.get('saveAmCores'), _cmd, function(json, status) {
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
					Rose.ajax.getJson(srvMap.get('deleteAmCores'), cmd, function(json, status) {
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
				Rose.ajax.postJson(srvMap.get('updateAmCores'), _cmd, function(json, status) {
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