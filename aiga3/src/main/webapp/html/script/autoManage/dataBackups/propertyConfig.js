define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";


	//分页根据条件查询功能点归属
	srvMap.add("getPropertyConfigList", pathAlias + "propertyConfig.json", "sys/propertyConfig/getpropertyConfig");
	//新增备份
	srvMap.add("addPropertyConfig", pathAlias + "retMessage.json", "sys/dataBackups/list");
	//删除备份
	srvMap.add("delPropertyConfig", pathAlias + "retMessage.json", "sys/dataBackups/list");
	//修改备份
	srvMap.add("updatePropertyConfig", pathAlias + "retMessage.json", "sys/dataBackups/list");
	//属性下拉菜单
	srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");
	// 模板对象
	var Tpl = {
		getPropertyConfigTemp: $('#JS_getPropertyConfigTemp'),
	};

	// 容器对象
	var Dom = {
		queryPropertyConfigForm: '#JS_queryPropertyConfigForm',
		getPropertyConfigList: '#JS_getPropertyConfigList',
		addPropertyConfigModal: "#JS_addPropertyConfigModal",
		addPropertyConfigInfo: "#JS_addPropertyConfigInfo",
		updatePropertyConfigModal: "#JS_updatePropertyConfigModal",
		updateMaintainInfo: "#JS_updatePropertyConfigInfo",
	};

	var Data = {
		queryListCmd: null
	}

	var Query = {
		init: function() {
			// 默认查询所有
			this.getPropertyConfigList();
			// 初始化查询表单
			this.queryPropertyConfigForm();
			//映射
			this.hdbarHelp();
		},
		// 按条件查询
		queryPropertyConfigForm: function() {
			var self = this;
			var _form = $(Dom.queryPropertyConfigForm);
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getPropertyConfigList(cmd);
			});

		},
		// 查询数据维护
		getPropertyConfigList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getPropertyConfigList'), _cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getPropertyConfigTemp.html());
					$(Dom.getPropertyConfigList).html(template(json.data.content));
					//美化单机
					Utils.eventTrClickCallback($(Dom.getPropertyConfigList));
					//新增
					self.addPropertyConfig();
					//删除
					self.delPropertyConfig();
					//双击修改
					self.eventDClickCallback($(Dom.getPropertyConfigList), function() {
						var _dom = $(Dom.getPropertyConfigList);
						//获得当前单选框值
						var data = Utils.getRadioCheckedRow(_dom);
						self.updatePropertyConfig(data.correlationId);
					});
				}
			});
			//设置分页
			self.initPaging($(Dom.getPropertyConfigList), 3)
		},
		//新增数据备份
		addPropertyConfig: function() {
			var self = this;
			var _list = $(Dom.getPropertyConfigList);
			var _addBt = _list.find("[name='add']");
			_addBt.unbind('click');
			_addBt.bind('click', function() {
				$(Dom.addPropertyConfigModal).modal('show');
				var _form = $(Dom.addPropertyConfigInfo);

				var _saveBt = $(Dom.addPropertyConfigModal).find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('addPropertyConfig'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								setTimeout(function() {
									self.getPropertyConfigList();
								}, 1000);
								// 关闭弹出层
								$(Dom.addPropertyConfigModal).modal('hide');
							}
						});
					});
				});

			});

		},
		//删除数据备份
		delPropertyConfig: function() {
			var self = this;
			var _dom = $(Dom.getPropertyConfigList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'correlationId=' + data.correlationId;
					//alert(cmd);
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delPropertyConfig'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.queryPropertyConfigForm(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		updatePropertyConfig: function(Id) {
			var _dom = Dom.updatePropertyConfigModal;
			$(_dom).modal('show');
			var _save = $(_dom).find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Dom.updateMaintainInfo;
				var _cmd = $(_form).serialize();
				alert(_cmd);
					XMS.msgbox.show('执行中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('updatePropertyConfig'), _cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('更新成功！', 'success', 2000)
							setTimeout(function() {
								self.queryPropertyConfigForm(Data.queryListCmd);
							}, 1000)
						}
					});
			});

		},
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tr").bind('dblclick ', function(event) {
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