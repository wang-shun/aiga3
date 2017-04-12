define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/publicTaskProcess/";

	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("publicTaskList", pathAlias + "publicTaskList.json", "");

	srvMap.add("caseResultList", pathAlias + "publicTaskList.json", "");

	srvMap.add("submitRst", pathAlias + "publicTaskList.json", "");


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
	var busiData;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {

			this.hdbarHelp();
			this.getpublicTaskList("");
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
					var template = Handlebars.compile(Tpl.testReportForm);
					$(Dom.testReportForm).find(".modal-body").html(template(data));
					self.setSelectData(_modal,data);
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
					var cmd = "resultId="+data.resultId;
					Rose.ajax.postJson(srvMap.get('submitRst'), cmd, function(json, status) {
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
				console.log(cmd);
				Rose.ajax.postJson(srvMap.get('submitRst'), cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('保存成功', 'success', 2000);
						_modal.modal('hide');
					}
				});

			});
			btnCancel.bind('click', function() {
				var _modal = $(Dom.modalTestReport);
				_modal.modal('hide');
			})
		},

		setSelectData:function(obj,data) {
			var sel = obj.find("select");

			sel.each(function(index, el) {
				var key = $(el).attr("name");
				$(el).val(data[key]);
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