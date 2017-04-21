define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/publicTaskProcess/";

	var Utils = require("global/utils.js");


	srvMap.add("getRstInfo", pathAlias + "publicTaskList.json", "accept/otherTask/getBossTestResultById");
	// 功能验收子任务列表显示
	srvMap.add("publicTaskList", pathAlias + "publicTaskList.json", "accept/otherTask/getOtherTask");

	srvMap.add("deleResult", pathAlias + "publicTaskList.json", "accept/otherTask/deleteOtherTask");

	srvMap.add("submitPublicRst", pathAlias + "publicTaskList.json", "accept/otherTask/saveOtherTask");

	//变更计划下拉框
	srvMap.add("getOnlinePlanList", pathAlias + "publicTaskList.json", "sys/cache/changePlan");

	srvMap.add("getOtherPlan", pathAlias + "publicTaskList.json", "accept/otherTask/getOtherPlan");
	srvMap.add("getOtherTaskInfo", pathAlias + "publicTaskList.json", "accept/otherTask/getOtherTaskInfo");
	srvMap.add("getOtherFlowName", pathAlias + "publicTaskList.json", "accept/otherTask/getOtherFlowName");


	// 模板对象
	var Tpl = {
		publicTaskList: $("#TPL_publicTaskList").html(), //计划列表
		testReportForm: $("#TPL_testReportForm").html()

	};

	// 容器对象
	var Dom = {
		publicTaskList: '#Js_publicTaskList',
		QueryTaskForm: '#Js_queryPublicTaskForm', //查询表单
		testReportForm: '#JS_testReportForm',

		modalTestReport: '#modal_testReport', //modal

		//提交结果按钮
		btnAddReport: '#JS_addReport',
		btnUpdateReport: "#JS_updateReport",
		btnDelReport: '#JS_delReport',


	};
	var taskType = 1;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {

			this.hdbarHelp();
			this.getpublicTaskList("taskType=1");
			this.querypublicTask();
			this.addReport();
			this.updateReport();
			this.delReport();
		},

		hdbarHelp: function() {
			Handlebars.registerHelper("transformatIf", function(value) {
				if (value == 0) {
					return "否";
				} else if (value == 1) {
					return "是";
				} else {
					return " ";
				}
			});

		},
		getpublicTaskList: function(cmd) {
			var self = this;

			Rose.ajax.postJson(srvMap.get('publicTaskList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.publicTaskList);
					console.log(json.data)
					$(Dom.publicTaskList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.publicTaskList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					self.initPaging($(Dom.publicTaskList), 5, true);
				}
			});
		},
		querypublicTask: function() {
			var self = this;
			var _form = $(Dom.QueryTaskForm);
			Utils.setSelectData(_form);
			// 表单提交

			_form.find('button[name="query"]').bind('click', function() {
					var cmd = _form.serialize();
					self.getpublicTaskList(cmd);
				})
				// 表单重置
			_form.find('button[name="reset"]').bind('click', function() {
				_form.find("input").html("");
				_form.find("select").html("");
			});
		},
		// 新增
		addReport: function() {
			var self = this;
			var btn = $(Dom.btnAddReport);
			btn.unbind('click');
			btn.bind('click', function() {
				var _modal = $(Dom.modalTestReport);
				_modal.find(".modal-title").html("新增测试结果报告");
				_modal.modal('show');
				var template = Handlebars.compile(Tpl.testReportForm);
				$(Dom.testReportForm).find(".modal-body").html(template());
				Utils.setSelectData(_modal, "type=0");
				var sel = _modal.find("select[name='planId']");
				self.getSelect(sel, taskType);
				self.saveTestReport();

			});
		},

		updateReport: function(cmd) {
			var self = this;
			var btn = $(Dom.btnUpdateReport);
			btn.unbind();
			btn.bind('click', function() {
				/* Act on the event */
				var data = Utils.getRadioCheckedRow($(Dom.publicTaskList).find("table"));
				if (data) {
					var _modal = $(Dom.modalTestReport);
					_modal.find(".modal-title").html("修改测试结果报告");
					_modal.modal('show');
					var _form = $(Dom.testReportForm);
					var template = Handlebars.compile(Tpl.testReportForm);
					_form.find(".modal-body").html(template(data));

					Utils.setSelectData(_modal, "type=1", function() {
						var _cmd = "resultId="+data.resultId;
						Rose.ajax.getJson(srvMap.get('getRstInfo'),_cmd,  function(json, status) {
							if (status) {
								console.log(json.data);
								console.log(_form.find("select"));
								var sel = _modal.find("select[name='planId']");
								self.getSelect(sel, taskType);
								_form.find("select").each(function(index, el) {
									console.log(el);
									$(el).val(json.data[$(el).attr("name")]);
								});
							}
						});
						

					});
					
					self.saveTestReport();
				}
			});
		},

		delReport: function() {
			var self = this;
			var btn = $(Dom.btnDelReport);
			btn.unbind();
			btn.bind('click', function() {
				/* Act on the event */
				var data = Utils.getRadioCheckedRow($(Dom.publicTaskList).find("table"));
				if (data) {
					var cmd = "resultId=" + data.resultId;
					Rose.ajax.postJson(srvMap.get('deleResult'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功', 'success', 2000);
							self.getpublicTaskList("");
						}
					});
				}
			});
		},
		//保存结果
		saveTestReport: function(resultId) {
			var self = this;
			var btnSave = $(Dom.testReportForm).find("button[name='save']");
			var btnCancel = $(Dom.testReportForm).find("button[name='cancel']");
			btnSave.unbind('click');
			btnSave.bind('click', function() {
				var _modal = $(Dom.modalTestReport);
				var _form = _modal.find("form");
				var cmd = _form.serialize();
				cmd += "&BossName=" + _form.find("select[name='planId']").find("option:selected").text();
				cmd += "&type" + taskType;
				console.log(cmd);
				Rose.ajax.postJson(srvMap.get('submitPublicRst'), cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('保存成功', 'success', 2000);
						_modal.modal('hide');
						self.getpublicTaskList("");
					}
				});

			});
			btnCancel.bind('click', function() {
				var _modal = $(Dom.modalTestReport);
				_modal.modal('hide');
			})
		},

		setSelectData: function(obj, data) {
			var sel = obj.find("select");

			sel.each(function(index, el) {
				var key = $(el).attr("name");
				$(el).val(data[key]);
			});

		},

		getSelect: function(obj, type) {
			Rose.ajax.getJson(srvMap.get('getOtherFlowName'), "type=" + type, function(json, status) {
				if (status) {
					var _html = '<option value="">请选择</option>{{#each this}}<option value="{{plantaskId}}">{{plantaskName}}</option>{{/each}}';
					var template = Handlebars.compile(_html);
					obj.html(template(json.data));
				}

			});
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