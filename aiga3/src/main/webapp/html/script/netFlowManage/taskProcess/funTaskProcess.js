define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/funTaskProcess/";

	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("funTaskList", pathAlias + "funTaskList.json", "accept/subTask/list");

	srvMap.add("caseResultList", pathAlias + "funTaskList.json", "accept/subTask/caseResult");

	srvMap.add("submitRst", pathAlias + "funTaskList.json", "accept/subTask/caseResultSave");


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

			this.hdbarHelp();
			this.getFunTaskList("");
			this.queryFunTask();
			this.submitResult();
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
		getFunTaskList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('funTaskList'), cmd+"&taskType=1", function(json, status) {
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
		submitResult: function() {
			var self = this;
			var btn = $(Dom.btnSubmitRst);
			btn.unbind('click');
			btn.bind('click', function() {
				var data = self.getSelectedInfo();
				if (data) {
					var _modal = $(Dom.modalSubmitResult);
					_modal.modal('show');
					var cmd = data.taskId;
					self.getTaskProcessList(cmd);
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
				var cmd = [];
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
						cmd.push(paramData);
					})
				});
				Rose.ajax.postJson(srvMap.get('submitRst'), cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('保存成功', 'success', 2000);
						var _modal = $(Dom.modalSubmitResult);
						_modal.modal('hide');
					}
				});

			});
		},

		getTaskProcessList: function(cmd) {
			var self = this;
			var _table = $(Dom.taskProcessList);
			cmd = "taskId=" + cmd;
			Rose.ajax.postJson(srvMap.get('caseResultList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.taskProcessList);
					console.log(json.data)
					_table.html(template(json.data.content));
					var da=json.data.content;
					var i=0
					_table.find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();

						tdArr.eq(1).find("select").val(da[i].result);
						i++;
					});

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