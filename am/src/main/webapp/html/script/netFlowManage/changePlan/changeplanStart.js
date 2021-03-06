define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('changeplanStart');

    // 路径重命名
    var pathAlias = "netFlowManage/changePlan/changePlanStart/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //获取变更计划列表
    srvMap.add("getChangePlanList", pathAlias + "getChangePlanList.json", "accept/changePlan/list");
    //获取自动化用例执行结果列表
    srvMap.add("getAutoResultList", pathAlias + "getAutoResultList.json", "accept/changePlanRun/caseResult");
    //获取编译发布结果列表
    srvMap.add("getPublishResultList", pathAlias + "getPublishResultList.json", "accept/changePlanRun/compileList");
    //下拉菜单获取所有处理人
    srvMap.add("getDealOpIdList", pathAlias + "getDealOpIdList.json", "accept/onlineTask/dealOp");
    //启动变更的接口
    srvMap.add("startChange", pathAlias + "retMessage.json", "accept/changePlanRun/changStart");
    //启动上线获取验收任务列表接口
    srvMap.add("getTaskResultList", pathAlias + "getTaskResultList.json", "accept/changePlanRun/taskList");
    //保存验收任务
    srvMap.add("saveTaskResult", pathAlias + "retMessage.json", "accept/changePlanRun/save");
    //删除验收任务
    srvMap.add("delTaskResult", pathAlias + "retMessage.json", "accept/changePlanRun/delete");

    // // 模板对象
    // var Tpl = {
    //     getChangePlanList: $("#TPL_getChangePlanList").html(),
    //     getAutoResultList: $("#TPL_getAutoResultListC").html(),
    //     getTaskResultList: $("#TPL_getTaskResultList").html(),
    //     getPublishResultList: $("#TPL_getPublishResultList").html()
    // };

    // // 容器对象
    // var Dom = {
    //     queryChangePlanForm: '#JS_queryChangePlanForm',
    //     getChangePlanList: '#JS_getChangePlanList',
    //     getAutoResultList: '#JS_getAutoResultListC',
    //     getAutoResultModal: '#Modal_getAutoResultModal',
    //     saveTaskResultForm: '#JS_saveTaskResultForm',
    //     getTaskResultList: '#JS_getTaskResultList',
    //     getTaskResultModal: '#Modal_getTaskResultModal',
    //     getPublishResultList: '#JS_getPublishResultList',
    //     getPublishResultModal: '#Modal_getPublishResultModal'

    // };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new"
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getChangePlanList();
            // 初始化查询表单
            this.queryChangePlanForm();
            //注册helper
            this.registerHelper();
        },

        // 按条件查询
        queryChangePlanForm: function() {
            var self = this;
            var _form = Page.findId('queryChangePlanForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getChangePlanList(cmd);
            });
        },
        // 查询计划变更列表
        getChangePlanList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            //alert(_cmd);
            var _dom = Page.findId('getChangePlanList');
            var _domPagination = _dom.find("[name='pagination']");
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getChangePlanList'), _cmd, function(json) {
                window.XMS.msgbox.hide();
                var template = Handlebars.compile(Page.findTpl('getChangePlanList'));
                _dom.find("[name='content']").html(template(json.data.content))

                self.getAutoResultList();
                self.changePlanStart();
                self.getPublishResultList();
                Utils.eventTrClickCallback(_dom)

                //设置分页
                //self.initPaging(Page.findId('getChangePlanList'), 8, true)
            }, _domPagination);

        },
        // 查询自动化执行结果详细信息
        getAutoResultList: function() {
            var self = this;
            var _dom = Page.findId('getChangePlanList');
            var _checkResultA = _dom.find("[name='checkResultA']");
            _checkResultA.unbind('click');
            _checkResultA.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom);
                if (data) {
                    var cmd = 'onlinePlan=' + data.onlinePlan;
                    var _dom1 = Page.findModalCId('getAutoResultListC');
                    var _domPagination = _dom1.find("[name='pagination']");
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Utils.getServerPage(srvMap.get('getAutoResultList'), cmd, function(json) {
                        window.XMS.msgbox.hide();
                        // 显示弹框

                        var _modal = Page.findModal('getAutoResultModal');
                        _modal.modal('show').on('shown.bs.modal', function() {
                            var template = Handlebars.compile(Page.findTpl('getAutoResultListC'));
                            _dom1.find("[name='content']").html(template(json.data.content));
                            //设置分页
                            //self.initPaging(_dom, 5, true);
                        })
                    }, _domPagination);
                }
            });
        },
        // 查询自动化执行结果详细信息
        getPublishResultList: function() {
            var self = this;
            var _dom = Page.findId('getChangePlanList');
            var _checkResultC = _dom.find("[name='checkResultC']");
            _checkResultC.unbind('click');
            _checkResultC.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom);
                if (data) {
                    var cmd = 'planDate=' + data.planDate;
                    var _dom1 = Page.findId('getPublishResultList');
                    var _domPagination = _dom1.find("[name='pagination']");
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Utils.getServerPage(srvMap.get('getPublishResultList'), cmd, function(json) {
                        window.XMS.msgbox.hide();
                        // 显示弹框
                        var _modal = Page.findModal('getPublishResultModal');
                        _modal.modal('show').on('shown.bs.modal', function() {
                            var template = Handlebars.compile(Page.findTpl('getPublishResultList'));
                            _dom1.find("[name='content']").html(template(json.data.content));
                            //设置分页
                            //self.initPaging(_dom, 5, true);
                        })
                    }, _domPagination);
                }
            });
        },
        // 查询自动化执行结果详细信息
        getTaskResultList: function(cmd, data) {
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            var _dom1 = Page.findId('getTaskResultList');    
            var _domPagination = _dom1.find("[name='pagination']");        
            Utils.getServerPage(srvMap.get('getTaskResultList'), cmd, function(json) {
                    window.XMS.msgbox.hide();
                    var _form = Page.findId('saveTaskResultForm');
                    Utils.setSelectData(_form);
                    _form.find("[name='onlinePlan']").val(data.onlinePlan);
                    _form.find("[name='onlinePlanName']").val(data.onlinePlanName);
                    var template = Handlebars.compile(Page.findTpl('getTaskResultList'));
                    _dom1.find("[name='content']").html(template(json.data.content));
                    self.saveTaskResult(data);
                    self.delTaskResult(data);
                    self.updateTaskResult(data);
                    Utils.eventTrClickCallback(_dom1)
                        //设置分页
                    //self.initPaging(_dom, 5, true);
            },_domPagination);
        },
        //启动(弹出自任务分派页面 判断如果是上线就弹出页面 如果是变更就调接口)
        changePlanStart: function() {
            var self = this;
            var _dom = Page.findId('getChangePlanList');
            var _start = _dom.find("[name='start']");
            _start.unbind('click');
            _start.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom);
                if (data) {
                    var cmd = "onlinePlan=" + data.onlinePlan;
                    if (data.planState != 3 && data.planState != 4) {
                        if (data.types == 2 || data.types == 3) {
                            Rose.ajax.postJson(srvMap.get('startChange'), cmd, function(json, status) {
                                if (status) {
                                    window.XMS.msgbox.show('启动成功！', 'success', 2000);
                                    setTimeout(function() {
                                        self.getChangePlanList();
                                    }, 1000)
                                } else {
                                    window.XMS.msgbox.show('启动失败！', 'error', 2000);
                                }
                            })
                        } else {
                            var cmd = 'onlinePlan=' + data.onlinePlan;
                            //存储到全局变量
                            Data.onlinePlan = data.onlinePlan;
                            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                            var _dom1 = Page.findId('getTaskResultList');
                            var _domPagination = _dom1.find("[name='pagination']");
                            Utils.getServerPage(srvMap.get('getTaskResultList'), cmd, function(json) {
                                    window.XMS.msgbox.hide();
                                    // 显示弹框
                                    var _modal = Page.findModal('getTaskResultModal');
                                    var _form = Page.findId('saveTaskResultForm');
                                    Utils.setSelectData(_form);
                                    _form.find("[name='onlinePlan']").val(data.onlinePlan);
                                    _form.find("[name='onlinePlanName']").val(data.onlinePlanName);
                                    _modal.modal('show').on('shown.bs.modal', function() {
                                        var template = Handlebars.compile(Page.findTpl('getTaskResultList'));
                                        _dom1.find("[name='content']").html(template(json.data.content));
                                        self.saveTaskResult(data);
                                        self.delTaskResult(data);
                                        self.updateTaskResult(data);
                                        var _close = _modal.find("[name='close']");
                                        _close.unbind('click');
                                        _close.bind('click', function() {
                                            self.getChangePlanList();
                                        })
                                        Utils.eventTrClickCallback(_dom1)
                                            //设置分页
                                        //self.initPaging(_dom, 5, true);

                                    })
                            },_domPagination);
                        }
                    } else {
                        window.XMS.msgbox.show('计划状态只有是新增和处理中的计划才能启动！', 'error', 2000);
                        return;
                    }
                }
            })
        },
        //保存验收任务分派
        saveTaskResult: function(data) {
            var self = this;
            var _form = Page.findId('saveTaskResultForm');
            var _save = _form.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var cmd = '';
                var taskType = _form.find("[name='taskType']").val();
                var dealOpId = _form.find("[name='dealOpId']").val();
                var taskId = _form.find("[name='taskId']").val();
                var onlinePlan = _form.find("[name='onlinePlan']").val();
                var onlinePlanName = _form.find("[name='onlinePlanName']").val();
                cmd = "taskType=" + taskType + "&dealOpId=" + dealOpId + "&onlinePlan=" + onlinePlan + "&onlinePlanName=" + onlinePlanName;
                if (Data.opreation == "update") {
                    cmd = cmd + "&taskId=" + taskId;
                }
                var _taskType = _form.find("[name='taskType']").val();
                if (_taskType != "") {
                    Rose.ajax.postJson(srvMap.get("saveTaskResult"), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getTaskResultList("onlinePlan=" + Data.onlinePlan, data);
                                _form.find("[name='reset']").click();
                                Data.opreation = 'new';
                            }, 1000)
                        }
                    });
                } else {
                    window.XMS.msgbox.show('请选择验收任务类型!', 'info', 1000);
                    return;
                }

            })
        },
        //删除验收任务分派
        delTaskResult: function(data) {
            var self = this;
            var _dom = Page.findId('getTaskResultList');
            var _del = _dom.find("[name='del']");
            _del.unbind('click');
            _del.bind('click', function() {
                var dataTemp = self.getRadioCheckedRow(_dom);
                if (dataTemp) {
                    var cmd = "taskIds=" + dataTemp.taskId;
                    Rose.ajax.postJson(srvMap.get("delTaskResult"), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getTaskResultList("onlinePlan=" + Data.onlinePlan, data);
                            }, 1000)
                        }
                    });
                } else {
                    window.XMS.msgbox.show('请选择一个验收任务！', 'info', 1000);
                    return;
                }
            })
        },
        //修改验收任务
        updateTaskResult: function(data) {
            var self = this;
            var _dom = Page.findId('getTaskResultList');
            var _form = Page.findId('saveTaskResultForm');
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function() {
                var dataTemp = self.getRadioCheckedRow(_dom);
                console.log(dataTemp)
                if (dataTemp) {
                    _form.find("[name='taskType']").val(dataTemp.taskType);
                    _form.find("[name='dealOpId']").val(dataTemp.dealOpId);
                    _form.find("[name='taskId']").val(dataTemp.taskId);
                    Data.opreation = "update";

                } else {
                    window.XMS.msgbox.show('请选择一个验收任务！', 'info', 1000);
                    return;
                }

            })
        },
        //获取选中变更计划的数据
        getRadioCheckedRow: function(obj) {
            var _obj = obj.find("input[type='radio']:checked");
            if (_obj.length == 0) {
                window.XMS.msgbox.show('请先选择一个变更计划！', 'info', 2000);
                return;
            }
            var data = {};
            _obj.parents("tr").find("input").each(function() {
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key] = value;
            });
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
                "language": {
                    "emptyTable": "暂无数据...",
                    "infoEmpty": "第0-0条，共0条"
                },
                "scrollX": scrollX
            });
        },
        registerHelper: function() {
            Handlebars.registerHelper('getPlanState', function(value, fn) {
                if (value == "1") {
                    return "新建";
                }
                if (value == "2") {
                    return "处理中";
                }
                if (value == "3") {
                    return "完成";
                }
                if (value == "4") {
                    return "取消";
                }
            });
            Handlebars.registerHelper('getTypes', function(value, fn) {
                if (value == "0") {
                    return "计划上线";
                }
                if (value == "1") {
                    return "紧急上线";
                }
                if (value == "2") {
                    return "计划变更";
                }
                if (value == "3") {
                    return "紧急变更";
                }
            });
            Handlebars.registerHelper('getTimely', function(value, fn) {
                if (value == "1") {
                    return "是";
                }
                if (value == "2") {
                    return "否";
                }
            });
            Handlebars.registerHelper('getCaseState', function(value, fn) {
                if (value == "0") {
                    return "未处理";
                }
                if (value == "1") {
                    return "处理完成";
                }
            });
            Handlebars.registerHelper('getDealState', function(value, fn) {
                if (value == "1") {
                    return "未分派";
                }
                if (value == "2") {
                    return "处理中";
                }
                if (value == "3") {
                    return "完成";
                }
                if (value == "4") {
                    return "不需分派";
                }

            });
            Handlebars.registerHelper('getTaskType', function(value, fn) {
                if (value == "1") {
                    return "前台功能验收";
                }
                if (value == "2") {
                    return "后台功能验收";
                }
                if (value == "3") {
                    return "非功能验收";
                }
            });
            Handlebars.registerHelper('getResulr', function(value, fn) {
                if (value == "0") {
                    return "成功";
                }
                if (value == "1") {
                    return "失败";
                }
                if (value == "2") {
                    return "未执行";
                }
                if (value == "3") {
                    return "中断";
                }
            });
            Handlebars.registerHelper('getIsFinished', function(value, fn) {
                if (value == "0") {
                    return "是";
                }
                if (value == "1") {
                    return "否";
                }
            });
        },

    };
    module.exports = Query;
});
