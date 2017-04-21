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


    var activePane = $("#JS_mainTabsContent").find(".active[id^='JS_childTab_']");
    var menuData = Rose.browser.mapQuery(activePane.data("cmd"));
    console.log(menuData);
    console.log(activePane);
    
    // 模板对象
    var Tpl = {
        publicTaskList: activePane.find("#TPL_publicTaskList").html(), //计划列表
        testReportForm: activePane.find("#TPL_testReportForm").html()
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
    var taskType = menuData.taskType;

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
        	cmd += "&taskType="+taskType;
            var self = this;
            var _dom = $(activePane).find(Dom.publicTaskList);
            var _tbody = _dom.find("tbody");
            var pagination = _dom.parent().find(".dataTables_paginate");
            Utils.getServerPage(srvMap.get('publicTaskList'), cmd, function(json) {
                var template = Handlebars.compile(Tpl.publicTaskList);
                console.log(json.data);

                _tbody.html(template(json.data.content));
                Utils.eventTrClickCallback(_dom);
            }, pagination);
        },

        querypublicTask: function() {
            var self = this;
            var _form = $(activePane).find(Dom.QueryTaskForm);
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
            var btn = $(activePane).find(Dom.btnAddReport);
            btn.unbind('click');
            btn.bind('click', function() {
                var _modal = $(activePane).find(Dom.modalTestReport);
                _modal.find(".modal-title").html("新增测试结果报告");
                _modal.modal('show');
                var template = Handlebars.compile(Tpl.testReportForm);
                $(activePane).find(Dom.testReportForm).find(".modal-body").html(template());
                //type=0代表这是新增
                Utils.setSelectData(_modal, "type=0");
                var sel = _modal.find("select[name='planId']");
                self.getSelect(sel, taskType);
                self.saveTestReport();

            });
        },

        updateReport: function(cmd) {
            var self = this;
            var btn = $(activePane).find(Dom.btnUpdateReport);
            btn.unbind();
            btn.bind('click', function() {
                /* Act on the event */
                var data = Utils.getRadioCheckedRow($(activePane).find(Dom.publicTaskList).find("table"));
                if (data) {
                    var _modal = $(activePane).find(Dom.modalTestReport);
                    _modal.find(".modal-title").html("修改测试结果报告");
                    _modal.modal('show');
                    var _form = $(activePane).find(Dom.testReportForm);
                    var template = Handlebars.compile(Tpl.testReportForm);
                    _form.find(".modal-body").html(template(data));
                    var _cmd = "resultId=" + data.resultId;

                    Rose.ajax.getJson(srvMap.get('getRstInfo'), _cmd, function(json, status) {
                    	//type=0代表这是新增
                        self.getOtherPlan(_form, "type=1", json.data);
                        var sel = _modal.find("select[name='planId']");
                        self.getSelect(sel, taskType, function() {
                            _form.find("select").each(function(index, el) {
                                console.log(el);
                                $(el).val(json.data[$(el).attr("name")]);
                            });
                        });
                    });

                    self.saveTestReport(data.resultId);
                }
            });
        },

        delReport: function() {
            var self = this;
            var btn = $(activePane).find(Dom.btnDelReport);
            btn.unbind();
            btn.bind('click', function() {
                /* Act on the event */
                var data = Utils.getRadioCheckedRow($(activePane).find(Dom.publicTaskList).find("table"));
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
            var btnSave = $(activePane).find(Dom.testReportForm).find("button[name='save']");
            var btnCancel = $(activePane).find(Dom.testReportForm).find("button[name='cancel']");
            btnSave.unbind('click');
            btnSave.bind('click', function() {
                var _modal = $(activePane).find(Dom.modalTestReport);
                var _form = _modal.find("form");
                var cmd = _form.serialize();
                cmd += "&BossName=" + _form.find("select[name='planId']").find("option:selected").text();
                cmd += "&type=" + taskType;
                if (resultId) {
                    cmd += "&resultId=" + resultId;
                }

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
                var _modal = $(activePane).find(Dom.modalTestReport);
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

        getSelect: function(obj, type, callback) {
            Rose.ajax.getJson(srvMap.get('getOtherFlowName'), "type=" + type, function(json, status) {
                if (status) {
                    var _html = '<option value="">请选择</option>{{#each this}}<option value="{{plantaskId}}">{{plantaskName}}</option>{{/each}}';
                    var template = Handlebars.compile(_html);
                    obj.html(template(json.data));
                    if (callback) {
                        callback();
                    }
                }

            });
        },
        //计划下拉框
        getOtherPlan: function(obj, cmd, data) {
            var self = this;
            var _select = $(obj).find("select[name='onlinePlan']");
            Rose.ajax.getJson(srvMap.get('getOtherPlan'), cmd, function(json, status) {
                if (status) {
                    var _html = '<option value="">请选择</option>{{#each this}}<option value="{{onlinePlan}}">{{onlinePlanName}}</option>{{/each}}';
                    var template = Handlebars.compile(_html);
                    _select.html(template(json.data));
                    self.getOtherPlanSelected(obj, data)
                    _select.val(data.onlinePlan);
                    _select.change();
                }
            });
        },
        //计划下拉框选择事件
        getOtherPlanSelected: function(obj, data) {
            var self = this;
            $(obj).find("select[name='onlinePlan']").change(function() {
                var id = $(obj).find("select[name='onlinePlan']").val();
                console.log(id);
                self.getOtherTaskInfo(id, obj, data);
            });
        },

        getOtherTaskInfo: function(id, obj, data) {
            var self = this;
            var _select = $(obj).find("select[name='taskId']");
            Rose.ajax.getJson(srvMap.get('getOtherTaskInfo'), "onlinePlan=" + id, function(json, status) {
                if (status) {
                    var _html = '<option value="">请选择</option>{{#each this}}<option value="{{taskId}}">{{taskName}}</option>{{/each}}';
                    var template = Handlebars.compile(_html);
                    _select.html(template(json.data));
                    _select.val(data.taskId);
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

        testCallback: function(callback) {
            if (callback) {
                callback();
            }
        }

    };
    module.exports = Init;
});
