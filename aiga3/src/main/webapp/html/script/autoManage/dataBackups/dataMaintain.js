define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";


	//分页根据条件查询功能点归属
	srvMap.add("getDataMaintainList", pathAlias + "dataMaintain.json", "sys/property/getPropertyMaintainList");
	//新增备份
	srvMap.add("addDataMaintain", pathAlias + "retMessage.json", "sys/property/addPropertyMaintain");
	//删除备份
	srvMap.add("delDataMaintain", pathAlias + "retMessage.json", "sys/property/delPropertyMaintain");
	//修改备份
	srvMap.add("updateDataMaintain", pathAlias + "retMessage.json", "sys/property/updatePropertyMaintain");

	// 模板对象
	var Tpl = {
		getDataMaintainTemp: $('#JS_getDataMaintainTemp'),
	};

	// 容器对象
	var Dom = {
		queryDataMaintainForm: '#JS_queryDataMaintainForm',
		getDataMaintainList: '#JS_getDataMaintainList',
		addDataMaintainModal: "#JS_addDataMaintainModal",
		addDataMaintainInfo: "#JS_addDataMaintainInfo",
		updateDataMaintainModal: "#JS_updateDataMaintainModal",
		updateMaintainInfo: "#JS_updateDataMaintainInfo",
	};

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
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = $(Dom.queryDataMaintainForm);
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
			Rose.ajax.postJson(srvMap.get('getDataMaintainList'), _cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getDataMaintainTemp.html());
					$(Dom.getDataMaintainList).html(template(json.data.content));
					//美化单机
					Utils.eventTrClickCallback($(Dom.getDataMaintainList));
					//新增
					self.addDataMaintain();
					//删除
					self.delDataMaintain();
					//双击修改
					self.eventDClickCallback($(Dom.getDataMaintainList), function() {
						var _dom = $(Dom.getDataMaintainList);
						//获得当前单选框值
						var data = Utils.getRadioCheckedRow(_dom);
						self.updateDataMaintain(data.correlationId);
					});
				}
			});
			//设置分页
			self.initPaging($(Dom.getDataMaintainList), 3)
		},
		//新增数据备份
		addDataMaintain: function() {
			var self = this;
			var _list = $(Dom.getDataMaintainList);
			var _addBt = _list.find("[name='add']");
			_addBt.unbind('click');
			_addBt.bind('click', function() {
				$(Dom.addDataMaintainModal).modal('show');
				var _form = $(Dom.addDataMaintainInfo);

				var _saveBt = $(Dom.addDataMaintainModal).find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('addDataMaintain'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								setTimeout(function() {
									self.getDataMaintainList();
								}, 1000);
								// 关闭弹出层
								$(Dom.addDataMaintainModal).modal('hide');
							}
						});
					});
				});

			});

		},
		//删除数据备份
		delDataMaintain: function() {
			var self = this;
			var _dom = $(Dom.getDataMaintainList);
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
					Rose.ajax.getJson(srvMap.get('delDataMaintain'), cmd, function(json, status) {
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
		updateDataMaintain: function(Id) {
			var _dom = Dom.updateDataMaintainModal;
			$(_dom).modal('show');
			var _save = $(_dom).find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Dom.updateMaintainInfo;
				var _cmd = $(_form).serialize();
				alert(_cmd);
					XMS.msgbox.show('执行中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('updateDataMaintain'), _cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('更新成功！', 'success', 2000)
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
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