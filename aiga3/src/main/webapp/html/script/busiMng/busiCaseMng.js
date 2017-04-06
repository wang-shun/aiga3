define(function(require, exports, module) {

	// 路径重命名
	var pathAlias = "busiMng/";


	// 初始化菜单列表
	srvMap.add("getModulList", pathAlias + "getModulList.json", "");
	
	//获取保存结果
	srvMap.add("getCaseList", pathAlias + "getBusiInfo.json", "");
	//修改
	srvMap.add("updateCase", pathAlias + "getBusiInfo.json", "");

	// 模板对象
	var Tpl = {
		getCaseInfo: $("#TPL_busiCaseList").html(),
	};

	// 容器对象
	var Dom = {
		getModulInfo: '#JS_getModulInfoForm',
		treeModuls: "#JS_busiModulTree",
	};

	//当前菜单id
	var currentModul = 0;

	var setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "funcId",
				pIdKey: "parentId"
			}
		},

		callback: {
			onClick: function(event, treeId, treeNode) {

				currentModul = treeNode.funcId;
				currentModulName = treeNode.name;		 
				var cmd = "funcId=" + currentModul;
				Rose.ajax.getJson(srvMap.get('getCaseList'), 'cmd', function(json, status) {
					if (status) {
						OperateState = "update";
						var template = Handlebars.compile(Tpl.getModulInfo);
						console.log(json.data)
						$(Dom.getModulInfo).find('.box-body').html(template(json.data));
					}
				});

			}
		}
	};

	var initFunction = {


		init: function() {
			this._render();
		},
		_render: function() {

			this.initTree();
			this.getCaseList();
			this.updateCase();

		},

		initTree: function() {
			Rose.ajax.getJson(srvMap.get('getModulList'), '', function(json, status) {
				if (status) {
					console.log(json.data);
					$.fn.zTree.init($(Dom.treeModuls), setting, json.data);
					var treeObj = $.fn.zTree.getZTreeObj(Dom.treeModuls);
					console.log(treeObj);
				}
			});
		},

		getCaseList: function() {
			$("#Js_Add").bind('click', function() {
				var _form = $(Dom.getModulInfo);
				OperateState = "new";
				_form.find('input').val("");
				_form.find("input[name='parentId']").val(currentModul);
				_form.find("input[name='parentName']").val(currentModulName);
			})
		},

		updateCase: function() {
			var self = this;
			var _form = $(Dom.getModulInfo);
			_form.find('button[name="save"]').bind('click', function() {
				var cmd = _form.serialize();
				var modulName = _form.find("input[name = 'modulName']").val();
						Rose.ajax.postJson(srvMap.get('addModul'), cmd, function(json, status) {
							if (status) {
								OperateState = "update";
								self.iniModulList();
								XMS.msgbox.show('添加菜单成功！', 'success', 3000)
							}
						});
					
			});

		},

		modulDel: function() {
			var _form = $(Dom.getModulInfo);
			var self = this;
			_form.find('button[name="del"]').bind('click', function() {
				var cmd = "funcId=" + currentModul;
				console.log(cmd);
				Rose.ajax.postJson(srvMap.get('deleModul'), cmd, function(json, status) {
					if (status) {
						self.iniModulList();
						OperateState = null;
						currentModul = 0;
						XMS.msgbox.show('删除菜单成功！', 'success', 3000)
						_form.find('input').val("");
						console.log(json.data);
						_form.find('button[name="del"]').attr({
							"disabled": "disabled"
						});
						_form.find('button[name="save"]').attr({
							"disabled": "disabled"
						});
						// _form.find('button[name="save"]').removeAttr("disabled");//将按钮可用
						// _form.find('button[name="del"]').removeAttr("disabled");//将按钮可用
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 3000)
					}
				});

			})
		},

	};


	module.exports = MenuMng;
});