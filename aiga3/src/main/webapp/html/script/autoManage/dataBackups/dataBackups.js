define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";

	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('dataBackupsView');

	//分页根据条件查询功能点归属
	srvMap.add("getDataBackupsList", pathAlias + "dataBackups.json", "sys/backup/getBackupDealList");
	//新增备份
	srvMap.add("addDataBackups", pathAlias + "retMessage.json", "sys/backup/addBackup");
	//删除备份
	srvMap.add("delDataBackups", pathAlias + "retMessage.json", "sys/backup/delBackup");
	//属性下拉菜单
	srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");

	// 模板对象
	var Tpl = {
		getDataBackupsTemp: $('#JS_getDataBackupsTemp'),
	};

	// 容器对象
	var Dom = {
		querydataBackupsForm: '#JS_queryDataBackupsForm',
		getDataBackupsList: '#JS_getDataBackupsList',
		addDataBackupsModal: "#JS_addDataBackupsModal",
		addDataBackupInfo: "#JS_addDataBackupInfo",
	};

	var Data = {
		queryListCmd: null
	}

	var Query = {
		init: function() {
			// 默认查询所有
			this.getDataBackupList();
			// 初始化查询表单
			this.queryDataBackupForm();
			//映射
			this.hdbarHelp();
		},
		// 按条件查询
		queryDataBackupForm: function() {
			var self = this;
			var _form = $(Dom.querydataBackupsForm);
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getDataBackupList(cmd);
			});

		},
		// 查询数据备份
		getDataBackupList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _dom = Page.findId('getDataBackupsList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getDataBackupsList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				var template = Handlebars.compile($("#TPL_getDataBackupsTemp").html());
				_dom.find("[name='content']").html(template(json.data.content));
				// 添加
				self.addDataBackup();
				// 废弃删除
				self.delDataBackups();
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);

		},
		//新增备份
		addDataBackup: function() {
			var self = this;
			var _list = $(Dom.getDataBackupsList);
			var _addBt = _list.find("[name='add']");
			_addBt.unbind('click');
			_addBt.bind('click', function() {
				$(Dom.addDataBackupsModal).modal('show');
				var _form = $(Dom.addDataBackupInfo);
				$(Dom.addDataBackupsModal).on('hide.bs.modal', function() {
					Utils.resetForm(Dom.addDataBackupInfo);
				});
				Utils.setSelectData(_form);
				var _saveBt = $(Dom.addDataBackupsModal).find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('addDataBackups'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								setTimeout(function() {
									self.getDataBackupList();
								}, 1000);
								// 关闭弹出层
								$(Dom.addDataBackupsModal).modal('hide');
							}
						});
					});
				});

			});

		},
		//删除备份
		delDataBackups: function() {
			var self = this;
			var _dom = $(Dom.getDataBackupsList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'dealId=' + data.dealId;
					//alert(cmd);
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delDataBackups'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.queryDataBackupForm(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		//映射处理
		hdbarHelp: function() {
			Handlebars.registerHelper("stateTran", function(value) {
				if (value == 1) {
					return "成功";
				} else if (value == 2) {
					return "失败";
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
		}
	};
	module.exports = Query;
});