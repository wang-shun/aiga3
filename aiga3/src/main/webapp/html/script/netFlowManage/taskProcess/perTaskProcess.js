define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/perTaskProcess/";

	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("perTaskList", "netFlowManage/taskProcess/funTaskProcess/funTaskList.json", "accept/subTask/list");
	//关联接口列表显示
	srvMap.add("interfaceList", pathAlias + "interfaceList.json", "accept/subTask/caseResult");

	// srvMap.add("submitRst", pathAlias + "funTaskList.json", "accept/subTask/caseResultSave");


	// 模板对象
	var Tpl = {
		perTaskList: $("#TPL_perTaskList").html(), //计划列表
		interfaceList: $("#TPL_interfaceList").html(),
		perTaskProcessList: $("#TPL_perTaskProcessList").html()

	};

	// 容器对象
	var Dom = {
		perTaskList: '#Js_perTaskList',
		interfaceList: '#Js_interfaceList', 
		queryPerTaskForm:'#Js_queryPerTaskForm',
	};
	var busiData;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {

			this.hdbarHelp();
			this.getPerTaskList("");
			this.getInterfaceList("");
		},

		hdbarHelp: function() {
			Handlebars.registerHelper("transformatTaskType", function(value) {
				if (value == 2) {
					return "自动化用例";
				} else if (value == 1) {
					return "手工用例";
				} else {
					return "未定义";
				}
			});
			Handlebars.registerHelper("transformatState", function(value) {
				if (value == 0) {
					return "未处理";
				} else if (value == 1) {
					return "处理中";
				} else if (value == 2) {
					return "处理完成";
				} else {
					return "未定义";
				}
			});
			Handlebars.registerHelper("transformatCaseState", function(value) {
				if (value == 0) {
					return "未处理";
				} else if (value == 1) {
					return "处理完成";
				}  else {
					return "未定义";
				}
			});
		},
		//显示任务列表
		getPerTaskList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('perTaskList'), cmd, function(json, status) {
				if (status) {

					var template = Handlebars.compile(Tpl.perTaskList);
					console.log(json.data)
					$(Dom.perTaskList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.perTaskList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					self.initPaging($(Dom.perTaskList), 5, true);
				}
			});
		},
		queryPerTaskList:function(){
			var self = this;
			var _form = $(Dom.queryPerTaskForm);
			// 表单提交
			_form.find('button[name="query"]').bind('click', function() {

					var cmd = _form.serialize();
					self.getPerTaskList(cmd);
				})
				// 表单重置
			_form.find('button[name="reset"]').bind('click', function() {

			});
		},
		//显示接口列表
		getInterfaceList:function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('interfaceList'), cmd, function(json, status) {
				if (status) {

					var template = Handlebars.compile(Tpl.interfaceList);
					console.log(json.data)
					$(Dom.interfaceList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.interfaceList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					self.initPaging($(Dom.interfaceList), 5, true);
				}
			});
		},

		//获取选中当前行数据
		getSelectedInfo: function() {
			var obj = this.getCheckedRow(Dom.funTaskList);
			var data = {
				taskId: "",
				dealState: "",
				taskType: ""
			};
			if (obj.length == 0) {
				window.XMS.msgbox.show('请先选择一个任务！', 'info', 2000);
				return;
			} else {
				data.taskId = obj.find("[name='taskId']").val();
				data.dealState = obj.find("[name='dealState']").val();
				data.taskType = obj.find("[name='taskType']").val();
			}

			return data;
		},
		// 获取复选列表当前选中行
		getCheckedRow: function(obj) {
			var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
			return _obj;
		},

		initPaging: function(obj, length, scrollX) {
			obj.find("table").DataTable({
				"iDisplayLength": length,
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"autoWidth": false,
				"info": true,
				"language": {
					"emptyTable": "暂无数据...",
					"infoEmpty": "第0-0条，共0条"
				},
				"scrollX": scrollX
			});
		},

	};
	module.exports = Init;
});