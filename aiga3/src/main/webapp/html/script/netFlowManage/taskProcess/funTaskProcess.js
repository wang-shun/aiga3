define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/funTaskProcess/";

	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("funTaskList", pathAlias + "funTaskList.json", "");
	//系统大类下拉框显示
	srvMap.add("submitRst", pathAlias + "funTaskList.json", "");


	// 模板对象
	var Tpl = {
		funTaskList: $("#TPL_funTaskList").html(), //计划列表
		taskProcessList: $("#TPL_taskProcessList").html()

	};

	// 容器对象
	var Dom = {
		funTaskList: '#Js_funTaskList',
		QueryTaskForm: '#Js_queryTaskForm', //查询表单

		taskProcessList: '#Js_taskProcessList',
		modalSubmitResult: '#modal_submitResult', //modal

		//提交结果按钮
		btnSubmitRst: '#Js_submitRst',
		btnSaveRst: "#Js_saveRst",
		btnDealAutoCase: 'Js_dealAutoCase',


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
					Utils.eventTrClickCallback($(Dom.funTaskList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					self.initPaging($(Dom.funTaskList), 5, true);
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
				var data = self.getSelectedInfo();
				if (data) {
					var _modal = $(Dom.modalSubmitResult);
					_modal.modal('show');
					var cmd = data.taskId;
					self.getTaskProcessList();
					self.saveResult(cmd);
				}
			});
		},
		//保存结果
		saveResult: function(taskId) {
			var self = this;
			var btn = $(Dom.btnSaveRst);
			var _table = $(Dom.taskProcessList);
			btn.unbind('click');
			btn.bind('click', function() {
				var cmd = {
					"taskId": taskId,
					"result": []
				};
				_table.find("tbody").each(function() {
					$(this).find("tr").each(function() {
						var paramData = {}
						$(this).find("input").each(function() {
							var key = $(this).attr("name");
							var value = $(this).val();
							paramData[key] = value;
						});
						$(this).find("select").each(function() {
							var key = $(this).attr("name");
							var value = $(this).val();
							paramData[key] = value;
						});
						cmd.result.push(paramData);
					})
				});
				Rose.ajax.postJson(srvMap.get('submitRst'), cmd, function(json, status) {
					if (status) {
						var template = Handlebars.compile(Tpl.taskProcessList);
						console.log(json.data)
						_table.html(template(json.data.content));
					}
				});

			});
		},

		getTaskProcessList: function(cmd) {
			var self = this;
			var _table = $(Dom.taskProcessList);
			Rose.ajax.postJson(srvMap.get('funTaskList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.taskProcessList);
					console.log(json.data)
					_table.html(template(json.data.content));


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