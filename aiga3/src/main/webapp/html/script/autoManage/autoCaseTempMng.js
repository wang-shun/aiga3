define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('autoCaseTempMng');
	var data = Page.getParentCmd();
	alert(data.username);
	alert(data.age);

	// 路径重命名
	var pathAlias = "autoManage/autoCaseTempMng/";

	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");

	// 分页根据条件查询自动化用例模板信息
	srvMap.add("getCaseTempList", pathAlias + "getCaseTempList.json", "auto/template/listInfo");
	//获取模板信息
	srvMap.add("getCaseTempInfo", pathAlias + "getCaseTempInfo.json", "case/template/get");
	// 新增
	srvMap.add("addCaseTempInfo", pathAlias + "retMessage.json", "auto/template/save");
	// 保存
	srvMap.add("updateCaseTempInfo", pathAlias + "retMessage.json", "auto/template/update");
	// 删除角色
	srvMap.add("delCaseTempInfo", pathAlias + "retMessage.json", "auto/template/delete");
	// 根据模板ID获取组件列表
	srvMap.add("getTempCompList", pathAlias + "getTempCompList.json", "auto/templateComp/listInfo");
	// 保存模板与组件关系(批量)
	srvMap.add("saveTempCompList", pathAlias + "getTempCompList.json", "auto/template/saveList");
	//获取组件信息
	srvMap.add("getTempCompInfo", pathAlias + "getTempCompInfo.json", "sys/component/findone");
	// 获取组件树
	srvMap.add("getCompTree", pathAlias + "getCompTree.json", "sys/cache/commenCompTree");
	//请求参数列表
	srvMap.add("getParameterList", pathAlias + "getParameterList.json", "sys/component/compParamList");
	//请求参数列表
	srvMap.add("saveAutoCompParam", pathAlias + "retMessage.json", "auto/case/saveAutoCompParam");


	/*// 模板对象
	var Tpl = {
	    getCaseTempList: '#TPL_getCaseTempList',
	    getTempCompList: require('tpl/autoManage/autoCaseTempMng/getTempCompList.tpl'),
	    getSideTempCompList: require('tpl/autoManage/autoCaseTempMng/getSideTempCompList.tpl'),
	    getCaseTempInfo: require('tpl/autoManage/autoCaseTempMng/getCaseTempInfo.tpl'),
	    getParameterList: require('tpl/autoManage/autoCaseTempMng/getParameterList.tpl')
	};*/


	/*// 容器对象
	var Dom = {
	    queryCaseTempForm: '#JS_queryCaseTempForm',
	    getCaseTempList: '#JS_getCaseTempList',
	    getTempCompList: '#JS_getTempCompList',
	    getSideTempCompList: '#JS_getSideTempCompList',
	    getCaseTempInfo: '#JS_getCaseTempInfo',
	    updateCaseTempInfoModal: '#JS_updateCaseTempInfoModal',
	    generateCaseInfoModal:'#JS_generateCaseInfoModal',
	    getParameterList:'#JS_getParameterList'
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
				self.getCaseTempList(cmd);
			});
		},
		// 查询自动化用例模板
		getCaseTempList: function(cmd) {
			var self = this;
			var _cmd = '';
			if (cmd) {
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('getCaseTempList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getCaseTempList'), _cmd, function(json) {
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getCaseTempList'));
				_dom.find("[name='content']").html(template(json.data.content));

				// 编辑模板
				self.updateCaseTempInfo();
				// 生成用例
				self.generateCaseInfo();
				// 废弃模板
				self.delCaseTempInfo();
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);
		},
		// 编辑模板
		updateCaseTempInfo: function() {
			var self = this;
			var _dom = Page.findId('getCaseTempList');
			var _edit = _dom.find("[name='edit']");
			_edit.unbind('click');
			_edit.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					var cmd = 'tempId=' + data.tempId;
					var _modal = Page.findModal('updateCaseTempInfo');
					// 赋值模板名称
					_modal.find("[name='tempName']").val(data.tempName);
					// 显示弹框
					_modal.modal('show');
					// 获取组件树
					self.getCompTree(_modal);
					//获取已选组件
					self.getTempCompList(cmd);
					// 获取模板基本信息
					self.getCaseTempInfo('caseId=' + data.caseId);
					// 保存编辑模板
					self.saveTempCompList(data.caseId, data.tempId);
				}
			});
		},
		//保存模板
		saveTempCompList: function(_caseId, _tempId) {
			var self = this;
			var _dom = Page.findModal('updateCaseTempInfo');
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _table = Page.findModalCId('getTempCompList').find("table");
				var _tempName = _dom.find("[name='tempName']").val();
				if (_tempName == '') {
					XMS.msgbox.show('用例模板名称不能为空！', 'error', 2000);
					return;
				}
				var cmd = {};
				cmd["caseId"] = _caseId;
				cmd["tempId"] = _tempId;
				cmd["tempName"] = _tempName;
				var hasData = Utils.getTableDataRows(_table);
				if (hasData) {
					cmd["compRequestList"] = hasData;
					console.log(cmd);
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.postJson(srvMap.get('saveTempCompList'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('保存成功！', 'success', 2000)
							_dom.modal('hide');
							setTimeout(function() {
								self.getCaseTempList(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		// 生成用例信息
		generateCaseInfo: function(cmd) {
			var self = this;
			var _dom = Page.findId('getCaseTempList');
			var _edit = _dom.find("[name='generate']");
			_edit.unbind('click');
			_edit.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					var cmd = 'tempId=' + data.tempId;
					var _modal = Page.findModal('generateCaseInfo');
					_modal.modal('show');
					_modal.find("[name='autoName']").val('');
					// 获取组件列表
					self.getSideTempCompList('tempId=' + data.tempId);
					//保存组件
					self.saveAutoCompParam(data.tempId);
					// 先清空
					Page.findModalCId('getParameterList').find("tbody").remove();
				}
			});
		},
		// 获取侧边组件列表
		getSideTempCompList: function(cmd) {
			// alert('获取侧边组件列表'+cmd);
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompList'), cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var _dom = Page.findModalCId('getSideTempCompList');
					var template = Handlebars.compile(Page.findTpl('getSideTempCompList'));
					_dom.html(template(json.data));
					Utils.eventClickChecked(_dom, function(isChecked, thisDom) {
						var _name = thisDom.attr("name");
						var _val = thisDom.val();
						if (isChecked == "true") {
							var cmd = _name + '=' + _val;
							// 获取参数列表
							self.getParameterList(cmd, _val);
						} else {
							var _table = Page.findModalCId('getParameterList');
							_table.find("tbody[name=compId_" + _val + "]").remove();
							// 设置滚动条高度
							Utils.setScroll(_table.parent(".box-body"), '250px');
						}
					})
				}
			});
		},
		// 获取参数列表
		getParameterList: function(cmd, compId) {
			// alert('参数列表'+cmd);
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getParameterList'), cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					json.data["compId"] = compId;
					var template = Handlebars.compile(Page.findTpl('getParameterList'));
					var _table = Page.findModalCId('getParameterList');
					_table.append(template(json.data))
						// 设置滚动条高度
					Utils.setScroll(_table.parent(".box-body"), '250px');

				}
			});
		},
		// 保存生成用例
		saveAutoCompParam: function(_tempId) {
			var self = this;
			var _dom = Page.findModal('generateCaseInfo');
			var _table = Page.findModal('getParameterList');
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _autoName = _dom.find("[name='autoName']").val();
				var _environmentType = _dom.find("[name='environmentType']").val();
				if (_autoName == '') {
					XMS.msgbox.show('用例模板名称不能为空！', 'error', 2000);
					return;
				}
				if (_environmentType == '') {
					XMS.msgbox.show('请选择环境类型！', 'error', 2000);
					return;
				}
				var hasData = Utils.getCheckboxCheckedRow(Page.findModalCId('getSideTempCompList'));
				if (hasData) {
					var cmd = {
						"tempId": _tempId,
						"autoName": _autoName,
						"environmentType": _environmentType,
						"compList": []
					};
					// 抓取参数列表
					_table.find("tbody").each(function() {
						var data = {};
						data["compId"] = $(this).find("[name='compId']").val();
						data["paramList"] = []
						$(this).find("tr").each(function() {
							var paramData = {}
							$(this).find("input").each(function() {
								var key = $(this).attr("name");
								var value = $(this).val();
								paramData[key] = value;
							});
							data["paramList"].push(paramData);
						})
						cmd.compList.push(data);
					});
					console.log("参数测试")
					console.log(cmd)
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.postJson(srvMap.get('saveAutoCompParam'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('保存成功！', 'success', 2000)
							_dom.modal('hide');
							setTimeout(function() {
								self.getCaseTempList(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		// 模板基本信息
		getCaseTempInfo: function(cmd) {
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('getCaseTempInfo'));
					Page.findModalCId('getCaseTempInfo').html(template(json.data))
				}
			});
		},
		// 获取已选组件
		getTempCompList: function(cmd) {
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompList'), cmd, function(json, status) {
				if (status) {
					var _dom = Page.findModalCId('getTempCompList');
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('getTempCompList'));
					_dom.html(template(json.data))

					Utils.eventTrClickCallback(_dom);
					// 设置滚动条
					self.setCompListScroll();
					// 绑定删除
					self.delTempCompInfo();
				}
			});
		},
		setCompListScroll: function() {
			// 设置滚动条高度
			Utils.setScroll(Page.findModalCId('getTempCompList').find(".box-body"), '200px');
		},
		getTempCompInfo: function(cmd) {
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompInfo'), cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var _dom = Page.findModalCId('getTempCompList');
					var template = Handlebars.compile(Page.findTpl('getTempCompList'));
					var dataArray = Utils.getTableDataRows(_dom);
					if (dataArray) {
						json.data["compOrder"] = dataArray.length + 1;
						dataArray.push(json.data);
						console.log(dataArray);
						_dom.html(template(dataArray))
							// 设置滚动条
						self.setCompListScroll();
						self.delTempCompInfo();
						Utils.eventTrClickCallback(_dom);
					}
				}
			});
		},
		delTempCompInfo: function() {
			var self = this;
			var _dom = Page.findModalCId('getTempCompList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				_dom.find("input[type='radio']:checked").parents("tr").remove();
			});
			// 设置滚动条
			self.setCompListScroll();

		},
		// 获取组件树
		getCompTree: function(obj) {
			var self = this
			Rose.ajax.getJson(srvMap.get('getCompTree'), '', function(json, status) {
				if (status) {
					console.log(json.data)
					var setting = {
						check: {
							enable: false
						},
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pid"
							}
						},
						callback: {
							beforeClick: function(treeId, treeNode, clickFlag) {
								return (!treeNode.isParent);
							},
							onClick: function(event, treeId, treeNode) {
								console.log(treeNode);
								var cmd = "compId=" + treeNode.id;
								self.getTempCompInfo(cmd);
							}
						}
					};
					$.fn.zTree.init(obj.find("[name='tree']"), setting, json.data);
					// 设置滚动条高度
					Utils.setScroll(obj.find("[name='scroll']"), '370px');
				}
			});
		},
		// 删除自动化用例模板信息
		delCaseTempInfo: function() {
			var self = this;
			var _dom = Page.findId('getCaseTempList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'tempId=' + data.tempId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delCaseTempInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getCaseTempList(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});

		}
	};
	module.exports = Query;
});