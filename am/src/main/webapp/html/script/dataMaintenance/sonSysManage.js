define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "dataMaintenance/";
	//分页
	var Page = Utils.initPage('sonSysView');

	//分页根据条件查询功能点归属
	srvMap.add("getSubAsciptionList", pathAlias + "getAsciptionList.json", "sys/subsysfolder/listByName");
	// 删除所选条目
	srvMap.add("delsubSysInfo", pathAlias + "retMessage.json", "sys/subsysfolder/del");
	//新增条目
	srvMap.add("addsubSysInfo", pathAlias + "retMessage.json", "sys/subsysfolder/save");
	//归属系统
	srvMap.add("getSysList", "autoManage/autoCaseTempMng/getSysList.json", "sys/cache/listSysid");

	/*// 模板对象
	var Tpl = {
		getSonSysList: require('tpl/dataMaintenance/getSonSysList.tpl')
	};
*/
	/*// 容器对象
	var Dom = {
		queryCaseTempForm: '#JS_queryCaseTempForm',
		getsubInfoList: '#JS_getsubInfoList',
		//弹出框
		addsubSysInfoModel: '#JS_addsubSysInfoModel',
		//新增表单
		addSysInfo: '#JS_addSysInfo',
	};*/

	var Data = {
		queryListCmd: null
	}

	var Query = {
		init: function() {
			// 默认查询所有
			this.getCaseTempList();
			// 初始化查询表单
			this.queryCaseTempForm();
		},
		// 按条件查询
		queryCaseTempForm: function() {
			var self = this;
			var _form = Page.findId('queryCaseTempForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				console.log(cmd);
				self.getCaseTempList(cmd);
			});

		},
		// 查询
		getCaseTempList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getsubInfoList');
			var _domPagination = _dom.find("[name='pagination']");

			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSubAsciptionList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				var template = Handlebars.compile($("#TPL_getSonSysTemp").html());
				_dom.find("[name='content']").html(template(json.data.content));
				//删除所选条目
				self.delSubCaseSysInfo();
				//新增条目
				self.addSubSysInfo();
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);


		},
		// 删除所选条目
		delSubCaseSysInfo: function() {
			var self = this;
			var _dom = Page.findId('getsubInfoList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'subsysId=' + data.subsysId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delsubSysInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getCaseTempList(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		//新增子类
		addSubSysInfo: function() {
			var self = this;
			var _dom = Page.findId('getsubInfoList');
			var _add = _dom.find("[name='add']");

			_add.unbind('click');
			_add.bind('click', function() {
				// 弹出层
				Page.findModal('addsubSysInfoModel').modal('show');
				//组件表单校验初始化
				var _form = Page.findId('addSysInfo');
				Utils.setSelectData(_form);
				Page.findModal('addsubSysInfoModel').on('hide.bs.modal', function() {
					Utils.resetForm('#JS_addSysInfo');
				});
				// 表单提交
				Page.findModal('addsubSysInfoModel').find("#addSysInfoButton").unbind('click');
				Page.findModal('addsubSysInfoModel').find("#addSysInfoButton").bind('click', function() {
					Utils.checkForm(_form, function() {
						var cmd = _form.serialize();
						console.log(cmd);
						Rose.ajax.postJson(srvMap.get('addsubSysInfo'), cmd, function(json, status) {
							if (status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
									// 关闭弹出层
								Page.findModal('addsubSysInfoModel').modal('hide');

								setTimeout(function() {
									self.getCaseTempList();
								}, 1000)
							}
						});
					});
				})
			})
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