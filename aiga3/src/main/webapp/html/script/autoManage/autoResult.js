define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 路径重命名
    var pathAlias = "autoManage/autoResult/";

    // 分页根据条件查询自动化用例模板信息
    srvMap.add("getAutoResultList", pathAlias + "getAutoResultList.json", "auto/autoRunResult/list");
    //获取详细信息
    srvMap.add("getAutoResultInfoList", pathAlias + "getAutoResultInfoList.json", "auto/autoRunResult/caseByTaskList");
    //获取所选用例的执行信息
    srvMap.add("showRunInfo", pathAlias + "showRunInfo.json", "auto/autoRunResult/runInfo");
    //获取所选用例的执行日志
    srvMap.add("showRunLog", pathAlias + "showRunLog.json", "auto/autoRunResult/runLog");
    //获取任务明细
    srvMap.add("getTaskDetailForm", pathAlias + "getTaskDetailForm.json", "auto/autoRunResult/findOne");
    //获取任务执行结果列表
    srvMap.add("getReportDetailList", pathAlias + "getReportDetailList.json", "auto/autoRunResult/taskDetail");
    //保存报告
    srvMap.add("saveReport", pathAlias + "retMessage.json", "auto/autoRunResult/reportSave");
    //保存明细
    srvMap.add("saveDetail",pathAlias + "retMessage.json","auto/autoRunResult/detailSave");

    // 模板对象
    var Tpl = {
        getAutoResultList: require('tpl/autoManage/autoResult/getAutoResultList.tpl'),
        getAutoResultInfoList: require('tpl/autoManage/autoResult/getAutoResultInfoList.tpl'),
        getTaskDetailForm: require('tpl/autoManage/autoResult/getTaskDetailForm.tpl'),
        getReportDetailList: require('tpl/autoManage/autoResult/getReportDetailList.tpl')

    };

    // 容器对象
    var Dom = {
        queryAutoResultForm: '#JS_queryAutoResultForm',
        getAutoResultList: '#JS_getAutoResultList',
        queryAutoResultInfoForm: '#JS_queryAutoResultInfoForm',
        getAutoResultInfoList: '#JS_getAutoResultInfoList',
        getAutoResultInfoModal: '#JS_getAutoResultInfoModal',
        showRunInfoModal: '#JS_showRunInfoModal',
        showRunLogModal: '#JS_showRunLogModal',
        generateReportModal: '#JS_generateReportModal',
        getTaskDetailForm: '#JS_getTaskDetailForm',
        getReportDetailList: '#JS_getReportDetailList'
        
    };

    var Data = {
        queryListCmd: null,
        taskId: null
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getAutoResultList();
            // 初始化查询表单
            this.queryAutoResultForm();
        },
        // 按条件查询
        queryAutoResultForm: function() {
            var self = this;
            var _form = $(Dom.queryAutoResultForm);
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getAutoResultList(cmd);
            });
        },
        // 查询自动化执行结果
        getAutoResultList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            //alert(_cmd);
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getAutoResultList'), _cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Tpl.getAutoResultList);
                    $(Dom.getAutoResultList).html(template(json.data))
                    var _successCase = json.data.successCase;
                    var _failCase = json.data.failCase;
                    var _successRate = parseInt(_successCase) * 100 /parseInt(_successCase + _failCase)+'%';
                    $(Dom.getAutoResultList).find('td[id=successRate]').val(_successRate);

                        //设置分页
                    self.initPaging($(Dom.getAutoResultList), 8)

                    // 生成报告
                    self.generateReport();
                    //Utils.eventTrClickCallback($(Dom.getAutoResultList));
                    Utils.eventTrClickCallback($(Dom.getAutoResultList), function() {
                        var _data = self.getCheckedAutoResult();
                        Data.taskId = _data.taskId;
                        console.log(Data.taskId);
                        var cmd = "taskId=" + _data.taskId;
                        console.log(cmd);
                        $(Dom.getAutoResultInfoModal).modal('show');
                        self.getAutoResultInfoList(cmd);
                        self.queryAutoResultInfoForm();
                    })

                }
            });

        },
        // 按条件查询
        queryAutoResultInfoForm: function() {
            var self = this;
            var _form = $(Dom.queryAutoResultInfoForm);
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='queryAuto']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getAutoResultInfoList(cmd);
            });
        },
        // 查询自动化执行结果详细信息
        getAutoResultInfoList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            _cmd1 = "&taskId=" + Data.taskId;
            cmd = _cmd + _cmd1;
            //alert(_cmd);
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getAutoResultInfoList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Tpl.getAutoResultInfoList);
                    $(Dom.getAutoResultInfoList).html(template(json.data));

                    //设置分页
                    self.initPaging($(Dom.getAutoResultInfoList).find("table"), 8);

                    // Utils.eventClickChecked($(Dom.getAutoResultInfoList));

                    self.showRunInfo();
                    self.showRunLog();
                }
            });

        },
        //点击显示执行信息
        showRunInfo: function() {
            var self = this;
            var _dom = $(Dom.getAutoResultInfoList);
            _dom.find("[name='runInfo']").each(function() {
                $(this).bind("click", function() {
                    var _resultid = $(this).data("resultid");
                    var cmd = 'resultId=' + _resultid;
                    console.log(cmd);
                    var _modal = $(Dom.showRunInfoModal);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('showRunInfo'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.hide();
                            // 显示弹框
                            _modal.modal('show');
                            // 赋值模板名称
                            _modal.find("[name='runInfoArea']").html(json.data.runInfo);

                        }
                    });
                })
            })
        },
        //点击显示执行信息
        showRunLog: function() {
            var self = this;
            var _dom = $(Dom.getAutoResultInfoList);
            _dom.find("[name='runLog']").each(function() {
                $(this).bind("click", function() {
                    var _resultid = $(this).data("resultid");
                    var cmd = 'resultId=' + _resultid;
                    console.log(cmd);
                    var _modal = $(Dom.showRunLogModal);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('showRunLog'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.hide();
                            // 显示弹框
                            _modal.modal('show');
                            // 赋值模板名称
                            _modal.find("[name='runLogArea']").html(json.data.runLog);

                        }
                    });
                })
            })
        },
        // 生成报告
        generateReport: function() {
            var self = this;
            var _dom = $(Dom.getAutoResultList);
            var _edit = _dom.find("[name='generate']");
            _edit.unbind('click');
            _edit.bind('click', function() {
                var data = Utils.getRadioCheckedRow(_dom);
                if (data) {
                    var cmd = 'taskId=' + data.taskId;
                    var _modal = $(Dom.generateReportModal);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    //调查询任务报告明细接口
                    self.getReportDetailList(cmd);
                    Rose.ajax.postJson(srvMap.get('getTaskDetailForm'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.hide();
                            var template = Handlebars.compile(Tpl.getTaskDetailForm);
                            var _table = $(Dom.getTaskDetailForm);
                            _table.html(template(json.data))
                            _modal.modal('show');

                            var _form = $(Dom.getTaskDetailForm);
                            var _saveBtn = _form.find("[name='save']");
                            _saveBtn.unbind('click');
                            _saveBtn.bind('click', function() {
                                var _cmd = _form.serialize();
                                var _reportName = _form.find("[name='reportName']").val();
                                Rose.ajax.postJson(srvMap.get('saveReport'), _cmd, function(json, status) {
                                    if (status&&_reportName!=="") {
                                        window.XMS.msgbox.show('保存成功！', 'success', 2000);
                                    } else{
                                        window.XMS.msgbox.show('报告名称不能为空！','error',2000);
                                        return;
                                    }
                                });
                            });
                        }
                    });
                } else {
                    XMS.msgbox.show('请选择一个自动化测试任务！', 'error', 2000);
                    return;
                }
            });
        },
        // 查询任务报告明细
        getReportDetailList: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getReportDetailList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Tpl.getReportDetailList);
                    $(Dom.getReportDetailList).html(template(json.data));
                    var _form = $(Dom.getReportDetailList);
                    var _saveBtn = _form.find("[name='saveDetail']");
                    _saveBtn.unbind('click');
                    _saveBtn.bind('click', function() {
                        var dataArray = Utils.getTableDataRows(_form);
                        var _cmd = dataArray;
                        console.log(_cmd);
                        Rose.ajax.postJson(srvMap.get('saveDetail'), _cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('保存成功！', 'success', 2000)
                            }
                        });


                        //设置分页
                        self.initPaging($(Dom.getReportDetailList).find("table"), 8);
                    });
                }
            });
        },
        //获取选中自动化测试任务
        getCheckedAutoResult: function() {
            var _obj = $(Dom.getAutoResultList).find("input[type='radio']:checked").parents("tr");
            var _taskId = _obj.find("input[name='taskId']")
            if (_taskId.length == 0) {
                window.XMS.msgbox.show('请先选择一个自动化测试任务！', 'error', 2000);
                return;
            } else {
                var data = {
                    taskId: ""
                }
                data.taskId = _taskId.val();
            }
            console.log(data);
            return data;
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
                "autoWidth": true,
                "scrollX": true,
                "scrollY": false
            });
        }
    };
    module.exports = Query;
});
