define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "newFlowManage/taskProcess/";
	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("funTaskList", pathAlias + "funTaskList.json", "");



	// 模板对象
	var Tpl = {
		funTaskList: require('tpl/netFlowManage/taskProcess/funTaskProcess/funTaskList.tpl'), //计划列表
		submitRstList: require('tpl/netFlowManage/taskProcess/funTaskProcess/submitRstList.tpl'),

	};

	// 容器对象
	var Dom = {
		funTaskList: '#Js_funTaskList',
		QueryTaskForm: '#Js_queryTaskForm', //查询表单
		
		getParameterList: '#JS_getParameterList', //参数列表
		getSideAutoCompList: '#JS_sideAutoCompList', //侧边组件栏

		modalSubmitResult: '#modal_submitResult', //modal

		//提交结果按钮
		btnSubmitRst: '#JS_submitRst',


	};
	var busiData;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {
			$("#Js_contentWrapper").find('h1').html("功能验收任务处理");
			$("#Js_contentWrapper").find('li.active').html("功能验收任务处理");
			this.hdbarHelp();
			this.getFunTaskList();
			this.submitResult('');
		},

		hdbarHelp: function() {
			
		},
		getFunTaskList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('funTaskList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.funTaskList);
					console.log(json.data)
					$(Dom.funTaskList).html(template(json.data.content));
					Utils.eventClickChecked($(Dom.funTaskList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
				}
			});
		},
		queryFunTask: function() {
			var self = this;
			var _form = $(Dom.QueryTaskForm);
			// 表单提交
			_form.find('button[name="query"]').bind('click', function() {

					var cmd = _form.serialize();
					self.getFunTaskList(cmd);
				})
				// 表单重置
			_form.find('button[name="reset"]').bind('click', function() {

			});
		},

		

		// 提交结果
		submitResult: function(cmd) {
			var self = this;
			var btn = $(Dom.btnSubmitRst);
			btn.unbind('click');
			btn.bind('click', function() {
				// var data = self.getSelectedInfo();
				// if (data) {
				alert()
					var _modal = $(Dom.modalSubmitResult);
					_modal.modal('show');

				// }
			});
		},


		//获取选中当前行数据
		getSelectedInfo: function() {
			var obj = this.getCheckedRow(Dom.getAutoCaseList);
			var data = {
				autoId: "",
				autoName: "",
				environmentType: "",
			};
			if (obj.length == 0) {
				window.XMS.msgbox.show('请先选择一个用例！', 'error', 2000);
				return;
			} else {
				data.autoId = obj.find("[name='autoId']").val();

				data.autoName = obj.find("[name='autoName']").val();
				data.environmentType = obj.find("[name='environmentType']").val();
			}

			return data;
		},
		// 获取复选列表当前选中行
		getCheckedRow: function(obj) {
			var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
			return _obj;
		},
		eventClickChecked: function(obj, callback) {
			obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue'
			});
			obj.find("tr").bind('click', function(event) {
				$(this).find('.minimal').iCheck('check');
				if (callback) {
					callback();
				}
			});
		},

	};
	module.exports = Init;
});