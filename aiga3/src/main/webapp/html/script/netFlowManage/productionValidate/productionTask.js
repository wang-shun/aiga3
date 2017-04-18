define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('Page_productionTaskDistribute');

    // 路径重命名
    var pathAlias = "netFlowManage/netFlow/onlineTaskDistribute/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //获取生产任务列表
    srvMap.add("getProductionTaskList", pathAlias + "getOnlineTaskList.json", "accept/onlineTask/list");
    //获取子任务分派列表
    srvMap.add("getProductionTaskDistributeList", pathAlias + "getOnlineTaskDistributeList.json", "accept/onlineTask/childList");
    //下拉菜单获取所有用力集
    srvMap.add("getCollectIdList", pathAlias + "getCollectIdList.json", "accept/onlineTask/collect");
    //下拉菜单获取所有处理人
    srvMap.add("getDealOpIdList", pathAlias + "getDealOpIdList.json", "accept/onlineTask/dealOp");
    //保存回归子任务
    srvMap.add("saveOnlineTask", pathAlias + "retMessage.json", "accept/onlineTask/save");
    //删除回归子任务
    srvMap.add("delOnlineTask", pathAlias + "retMessage.json", "accept/onlineTask/delete");
    //查看手工用例执行结果列表
    srvMap.add("getManualResultList", pathAlias + "getManualResultList.json", "accept/subTask/caseResult");
    //查看自动化用例执行结果列表
    srvMap.add("getAutoResultList", pathAlias + "getAutoResultList.json", "accept/subTask/autoResult");


    // 模板对象
    var Tpl = {
        getProductionTaskList: $("#TPL_getProductionTaskList").html(),
        getProductionTaskDistributeList: $("#TPL_getProductionTaskDistributeList").html(),
        getPManualResultList: $("#TPL_getPManualResultList").html(),
        getPAutoResultList: $("#TPL_getPAutoResultList").html(),

        getPCollectId: $("#TPL_getPCollectId").html()


    };

    // 容器对象
    var Dom = {
        queryProductionTaskForm: '#JS_queryProductionTaskForm',
        getProductionTaskList: '#JS_getProductionTaskList',
        getProductionTaskDistributeModal: '#JS_getProductionTaskDistributeModal',
        addProductionTaskDistributeForm: '#JS_addProductionTaskDistributeForm',
        getProductionTaskDistributeList: '#JS_getProductionTaskDistributeList',
        getAutoResultList: "#JS_getPAutoResultList",
        getAutoResultListModal: "#JS_getPAutoResultListModal",
        getManualResultListModal: '#JS_getPManualResultListModal',
        getManualResultList: '#JS_getPManualResultList',
        collectIdss: '#SELT_collectIdss'

    };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new",
        data: [],
        cm: "",
        taskIdl: "0",
        taskid: null,
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.registerHelper();
            this.getProductionTaskList();
            // 初始化查询表单
            this.queryProductionTaskForm();

            this.distributeTask();
        },

        // 按条件查询
        queryProductionTaskForm: function() {
            var self = this;
            var _form = $(Dom.queryProductionTaskForm);
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getProductionTaskList(cmd);
            });
        },
        // 分派任务
        distributeTask: function() {
            var self = this;
            var _dom = $(Dom.getProductionTaskList);
            var btn_distribute = _dom.find("[name='distribute']");
            btn_distribute.unbind('click');
            btn_distribute.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom.find("table"));
                if (data) {
                    var cmd = 'taskId=' + data.taskId;
                    //存储到全局变量
                    Data.onlinePlanId = data.onlinePlan;
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('getProductionTaskDistributeList'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.hide();
                            var _form = $(Dom.addProductionTaskDistributeForm);
                            Utils.setSelectData(_form);

                            var _modal = $(Dom.getProductionTaskDistributeModal);
                            _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Tpl.getProductionTaskDistributeList);
                                var _dom = $(Dom.getProductionTaskDistributeList);
                                _dom.html(template(json.data));
                                // 初始化步骤
                                Utils.initStep(_modal);
                                //加载用例集类型下拉框
                                Rose.ajax.postJson(srvMap.get('getCollectIdList'), "caseType=1", function(json, status) {
                                    if (true) {
                                        // _dom.find("[name='dealOpId']");
                                        console.log(json.data);
                                        var template = Handlebars.compile(Tpl.getPCollectId);
                                        $(Dom.collectIdss).html(template(json.data));
                                    }
                                });

                                self.addProductionTask();
                                self.updateProductionTask();
                                self.delProductionTask();
                                self.getRunResultList();
                                var _close = _modal.find("[name='close']");
                                _close.unbind('click');
                                _close.bind('click', function() {
                                    self.getProductionTaskList();
                                })
                                var _rest = _form.find("[name='reset']");
                                _rest.unbind('click');
                                _rest.bind('click', function() {
                                    _form.find("[name='collectId']").attr("disabled", false);
                                    Data.opreation = 'new';
                                })
                                Utils.eventTrClickCallback($(Dom.getProductionTaskDistributeList))
                                    //设置分页
                                self.initPaging(_dom, 5, true);
                            })
                        }
                    });

                }
            });
        },

        // 查询任务列表
        getProductionTaskList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = $(Dom.getProductionTaskList);
            var btn_distribute = _dom.find("[name='distribute']");
            var pagination = _dom.find(".dataTables_paginate");

            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getProductionTaskList'), _cmd + "&taskType=4", function(json) {
                window.XMS.msgbox.hide();
                console.log(json.data);
                console.log(_dom.find("tbody"));
                var template = Handlebars.compile(Tpl.getProductionTaskList);
                _dom.find("tbody").html(template(json.data))

                // self.getProductionTaskDistributeList();
                Utils.eventTrClickCallback(_dom.find("table"));
            }, pagination);

        },


        // 查询自动化执行结果详细信息
        getProductionTaskDistributeList: function() {
            var self = this;
            var _dom = $(Dom.getProductionTaskList);
            var data = self.getRadioCheckedRow(_dom);
            if (data) {
                var cmd = 'taskId=' + data.taskId;
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(srvMap.get('getProductionTaskDistributeList'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.hide();
                        var _form = $(Dom.addProductionTaskDistributeForm);
                        Utils.setSelectData(_form);
                        //_form.find("[name='onlinePlanId']").val(data.onlinePlanId);
                        var template = Handlebars.compile(Tpl.getProductionTaskDistributeList);
                        var _dom = $(Dom.getProductionTaskDistributeList);
                        _dom.html(template(json.data));
                        self.updateProductionTask();
                        self.delProductionTask();
                        self.getRunResultList();
                        Utils.eventTrClickCallback($(Dom.getProductionTaskDistributeList))
                            //设置分页
                        self.initPaging(_dom, 5, true);
                    }
                });
            }
        },
        //新建回归子任务
        addProductionTask: function() {
            var self = this;
            var _form = $(Dom.addProductionTaskDistributeForm);
            var _dom = $(Dom.getProductionTaskList);
            _save = _form.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom);
                console.log(data.length)
                if (data) {
                    var cmd = '';
                    var taskName = _form.find("[name='taskName']").val();
                    var collectId = _form.find("[name='collectId']").val();
                    var dealOpId = _form.find("[name='dealOpId']").val();
                    var taskId = _form.find("[name='taskId']").val();
                    var onlinePlanId = _form.find("[name='onlinePlanId']").val();
                    cmd = "taskName=" + taskName + "&collectId=" + collectId + "&parentTaskId=" + data.taskId + "&dealOpId=" + dealOpId;
                    if (Data.opreation == "update") {
                        cmd = cmd + "&taskId=" + taskId;
                    }
                    if (taskName != "" && collectId != "" && dealOpId != "") {
                        Rose.ajax.postJson(srvMap.get("saveProductionTask"), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('保存成功！', 'success', 2000);
                                setTimeout(function() {
                                    self.getProductionTaskDistributeList();
                                    _form.find("[name='collectId']").attr("disabled", false);
                                    _form.find("[name='reset']").click();
                                    Data.opreation = 'new';
                                }, 1000)
                            }
                        });
                    } else {
                        window.XMS.msgbox.show('保存失败!', 'error', 1000);
                        return;
                    }
                }
            });
        },
        //修改已分派子任务
        updateProductionTask: function() {
            var self = this;
            var _dom = $(Dom.getProductionTaskDistributeList);
            var _form = $(Dom.addProductionTaskDistributeForm);
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function() {
                var dataTemp = self.getCheckboxCheckedRow(_dom);
                console.log(dataTemp)
                if (dataTemp) {
                    _form.find("[name='taskName']").val(dataTemp.taskName);
                    _form.find("[name='collectId']").val(dataTemp.collectId).attr("disabled", true);
                    _form.find("[name='dealOpId']").val(dataTemp.dealOpId);
                    _form.find("[name='taskId']").val(dataTemp.taskId);
                    Data.opreation = "update";

                }
            })
        },
        //
        // 删除已关联用例
        delProductionTask: function() {
            var self = this;
            var dom = $(Dom.getProductionTaskList);
            var _dom = $(Dom.getProductionTaskDistributeList);
            var _del = _dom.find("[name='del']");
            _del.unbind('click');
            _del.bind('click', function() {
                var data = self.getRadioCheckedRow(dom);
                if (data) {
                    var _data = self.getCheckedTask();
                    if (_data) {
                        var _taskIdsArray = [];
                        _data.each(function() {
                            _taskIdsArray.push($(this).val());
                        })
                        var cmd = "taskIds=" + _taskIdsArray.join(",");
                        console.log(cmd);
                        Rose.ajax.postJson(srvMap.get("delProductionTask"), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('删除成功！', 'success', 2000);
                                setTimeout(function() {
                                    self.getProductionTaskDistributeList();;
                                }, 1000)
                            }
                        });
                    }
                }
            })
        },
        // 查询自动化执行结果详细信息
        getRunResultList: function() {
            var self = this;
            var _dom = $(Dom.getProductionTaskDistributeList);
            var _checkResult = _dom.find("[name='checkResult']");
            _checkResult.unbind('click');
            _checkResult.bind('click', function() {
                var data = self.getCheckboxCheckedRow(_dom);
                if (data) {
                    if (data.taskType == 1) {
                        var cmd = 'taskId=' + data.taskId;
                        XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                        Rose.ajax.postJson(srvMap.get('getManualResultList'), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.hide();

                                // 到第二步骤
                                Utils.goStep(Dom.getProductionTaskDistributeModal, 2);
                                // 显示弹框
                                // var _modal = $(Dom.getManualResultListModal);
                                // _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Tpl.getPManualResultList);
                                var _dom = $(Dom.getManualResultList);
                                _dom.html(template(json.data));
                                //设置分页
                                self.initPaging(_dom, 5, true);
                                //})
                            }
                        });
                    } else {
                        var cmd = 'taskId=' + data.taskId;
                        XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                        Rose.ajax.postJson(srvMap.get('getAutoResultList'), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.hide();
                                // 到第三步骤
                                Utils.goStep(Dom.getProductionTaskDistributeModal, 3);
                                // 显示弹框
                                // var _modal = $(Dom.getAutoResultListModal);
                                // _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Tpl.getPAutoResultList);
                                var _dom = $(Dom.getAutoResultList);
                                _dom.html(template(json.data));
                                //设置分页
                                self.initPaging(_dom, 5, true);
                                // })
                            }
                        });
                    }
                }
            });
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
        //获取选中子任务的数据
        getCheckboxCheckedRow: function(obj) {
            var _obj = obj.find("input[type='checkbox']:checked");
            if (_obj.length == 0 || _obj.length > 1) {
                window.XMS.msgbox.show('请选择一行数据！', 'info', 2000);
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
        //获取选中用例
        getCheckedTask: function() {
            var _obj = $(Dom.getProductionTaskDistributeList).find("input[type='checkbox']:checked").parents("tr");
            var _taskId = _obj.find("input[name='taskId']");
            console.log(_taskId);
            if (_taskId.length == 0) {
                window.XMS.msgbox.show('请先选择一个子任务！', 'error', 2000);
                return;
            } else {
                var _data = $(_taskId);
                data = _data;
                console.log(data);
            }
            return data;
        },
        // 事件：双击绑定事件
        eventDClickCallback: function(obj, callback) {
            obj.find("tr").bind('dblclick ', function(event) {
                if (callback) {
                    callback();
                }
            });
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
            Handlebars.registerHelper('getState', function(value, fn) {
                if (value == "1") {
                    return "新增";
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
            Handlebars.registerHelper('getParentTaskType', function(value, fn) {
                if (value == "1") {
                    return "前台功能验收";
                }
                if (value == "2") {
                    return "后台功能验收";
                }
                if (value == "3") {
                    return "非功能验收";
                }
                if (value == "4") {
                    return "生产回归验证";
                }
            });
            Handlebars.registerHelper('getTaskType', function(value, fn) {
                if (value == "1") {
                    return "手工用例";
                }
                if (value == "2") {
                    return "自动化用例";
                }
                if (value == "3") {
                    return "用例组";
                }
            });

            Handlebars.registerHelper('getCollectName', function(value, fn) {
                if (value == "0") {
                    return "未分派";
                }
                if (value == "1") {
                    return "处理中";
                }
                if (value == "2") {
                    return "完成";
                }
            });
        },

    };
    module.exports = Query;
});
