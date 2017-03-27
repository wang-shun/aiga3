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
    //保存报告
    srvMap.add("saveReport", pathAlias + "retMessage.json", "auto/autoRunResult/reportSave");
    //导出报告判断
    //导出报告

    // 模板对象
    var Tpl = {
        getAutoResultList: require('tpl/autoManage/autoResult/getAutoResultList.tpl'),
        getAutoResultInfoList: require('tpl/autoManage/autoResult/getAutoResultInfoList.tpl')

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
        generateReportModal: '#JS_generateReportModal'

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
                        // 生成报告
                    self.generateReport();
                    //Utils.eventTrClickCallback($(Dom.getAutoResultList));
                    Utils.eventTrClickCallback($(Dom.getAutoResultList), function() {
                        var _data = self.getCheckedAutoResult();
                        Data.taskId = _data.taskId;
                        console.log(Data.taskId);
                        var cmd = "taskId=" + _data.taskId;
                        console.log(cmd);
                        $(Dom.getAutoResultInfoModal).modal('show').on('shown.bs.modal', function () {
                            self.queryAutoResultInfoForm(); 
                            self.getAutoResultInfoList(cmd);   
                        })
                    })
                    //设置分页
                    self.initPaging($(Dom.getAutoResultList), 8, true)

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
                    var _dom = $(Dom.getAutoResultInfoList);
                    _dom.html(template(json.data));
                  
                    //setTimeout(function(){
                        //设置分页
                        self.initPaging(_dom, 5, true);    
                    //},600)
                   
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
                    Data.taskId = data.taskId;
                    var _modal = $(Dom.generateReportModal);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('saveReport'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('生成报告成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getAutoResultList("taskId=" + Data.taskId);
                            }, 1000)
                        }
                    });
                } else {
                    XMS.msgbox.show('请选择一个自动化测试任务！', 'error', 2000);
                    return;
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
        initPaging: function(obj, length, scrollX) {
            obj.find("table").DataTable({
                "iDisplayLength": length,
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": false,
                "autoWidth": false,
                "info": true,
                "scrollX": scrollX
            });
        }
    };
    module.exports = Query;
});
