define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('changeReview');

    // 路径重命名
    var pathAlias = "netFlowManage/changeReview/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //获取变更计划列表
    srvMap.add("getOnlineReviewTaskList", pathAlias + "getOnlineReviewTaskList.json", "accept/changePlan/list");
    //获取任务分派列表
    srvMap.add("getOnlineReviewTaskDistributeList", pathAlias + "getOnlineReviewTaskDistributeList.json", "accept/changePlanRun/taskList");
    //下拉菜单获取所有处理人
    srvMap.add("getDealOpIdList", pathAlias + "getDealOpIdList.json", "accept/onlineTask/dealOp");
    //保存或修改分派任务
    srvMap.add("saveOnlineReviewTask", pathAlias + "retMessage.json", "accept/changePlanRun/save");
    //删除任务
    srvMap.add("delOnlineReviewTask", pathAlias + "retMessage.json", "accept/changePlanRun/delete");

    // // 模板对象
    // var Tpl = {
    //     getOnlineTaskList: $("#TPL_getOnlineReviewTaskList").html(),
    //     getOnlineTaskDistributeList: $("#TPL_getOnlineReviewTaskDistributeList").html()

    // };

    // // 容器对象
    // var Dom = {
    //     queryOnlineTaskForm: '#JS_queryOnlineTaskForm',
    //     getOnlineTaskList: '#JS_getOnlineTaskList',
    //     getOnlineTaskDistributeModal: '#JS_getOnlineTaskDistributeModal',
    //     addOnlineTaskDistributeForm: '#JS_addOnlineTaskDistributeForm',
    //     getOnlineTaskDistributeList: '#JS_getOnlineTaskDistributeList'

    // };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new"
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getOnlineReviewTaskList();
            // 初始化查询表单
            this.queryOnlineReviewForm();
            //注册helper
            this.registerHelper();
        },

        // 按条件查询
        queryOnlineReviewForm: function() {
            var self = this;
            var _form = Page.findId('queryOnlineReviewForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getOnlineReviewTaskList(cmd);
            });
        },
        // 查询计划变更列表
        getOnlineReviewTaskList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getOnlineReviewTaskList');
            //真分页时解开
            var _domPagination = _dom.find("[name='pagination']");

            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getOnlineReviewTaskList'), _cmd, function(json) {
                window.XMS.msgbox.hide();
                //alert(Page.findTpl('getOnlineReviewTaskList'))
                var template = Handlebars.compile(Page.findTpl('getOnlineReviewTaskList'));
                _dom.find("[name='content']").html(template(json.data.content));


                Utils.eventTrClickCallback(_dom)
                    //设置滚动条
                    //self.initPaging(_dom, true);
            }, _domPagination);

            // 分派任务
            self.distribute(_dom);

        },
        distribute: function(dom) {
            var self = this;
            var _distribute = $(dom).find("[name='distribute']");
            _distribute.unbind('click');
            _distribute.bind('click', function() {
                // alert($(dom).html())
                var data = self.getRadioCheckedRow($(dom));
                if (data) {
                    var cmd = 'onlinePlan=' + data.onlinePlan + '&type=1';
                    //存储到全局变量
                    Data.onlinePlan = data.onlinePlan;
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    if (data.planState == "1" || data.planState == "2") {
                        var _dom = Page.findModalCId('getOnlineReviewTaskDistributeList');
                        var _domPagination = _dom.find("[name='pagination']");
                        Utils.getServerPage(srvMap.get('getOnlineReviewTaskDistributeList'), cmd, function(json) {
                            window.XMS.msgbox.hide();
                            var _form = Page.findModalCId('addOnlineReviewTaskDistributeForm');
                            Utils.setSelectData(_form);

                            //_form.find("[name='onlinePlanId']").val(data.onlinePlanId);
                            // 显示弹框

                            var _modal = Page.findModal('getOnlineReviewTaskDistributeModal');
                            // alert(1);
                            _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Page.findTpl('getOnlineReviewTaskDistributeList'));
                                _dom.find("[name='content']").html(template(json.data.content));
                                // 初始化步骤
                                Utils.initStep(_modal);
                                self.addOnlineTask();
                                self.updateOnlineTask();
                                self.delOnlineTask();
                                var _close = _modal.find("[name='close']");
                                _close.unbind('click');
                                _close.bind('click', function() {
                                    self.getOnlineReviewTaskList();
                                })
                                var _rest = _form.find("[name='reset']");
                                _rest.unbind('click');
                                _rest.bind('click', function() {
                                        _form.find("[name='taskType']").attr("disabled", false);
                                        Data.opreation = 'new';
                                    })
                                    //alert(_dom)
                                Utils.eventTrClickCallback(_dom)
                                    ////设置横向滚动条
                                    // self.initPaging(_dom, true);
                            })
                        }, _domPagination, 5);
                    } else {
                        window.XMS.msgbox.show('计划状态只有是新增或者处理中的计划才能分派!', 'error', 1000);
                        return;
                    }
                }
            });
        },
        // 查询自动化执行结果详细信息
        getOnlineReviewTaskDistributeList: function() {
            var self = this;
            var _dom = Page.findId('getOnlineReviewTaskList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = self.getRadioCheckedRow(_dom);
            if (data) {
                var cmd = 'onlinePlan=' + data.onlinePlan + '&type=1';
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Utils.getServerPage(srvMap.get('getOnlineReviewTaskDistributeList'), cmd, function(json) {
                    window.XMS.msgbox.hide();
                    var _form = Page.findModalCId('addOnlineReviewTaskDistributeForm');
                    Utils.setSelectData(_form);
                    var _dom = Page.findModalCId('getOnlineReviewTaskDistributeList');
                    var template = Handlebars.compile(Page.findTpl('getOnlineReviewTaskDistributeList'));
                    _dom.find("[name='content']").html(template(json.data.content));
                    self.updateOnlineTask();
                    self.delOnlineTask();
                    Utils.eventTrClickCallback(_dom)
                        //     //设置分页
                        // self.initPaging(_dom, 5, true);
                }, _domPagination, 5);
            }
        },
        //新建回归子任务
        addOnlineTask: function() {
            var self = this;
            var _form = Page.findModalCId('addOnlineReviewTaskDistributeForm');
            var _dom = Page.findId('getOnlineReviewTaskList');
            var _table = Page.findModalCId('getOnlineReviewTaskDistributeList');
            var _data = Utils.getTableDataRows(_table);
            //alert(_data)
            console.log(_data)


            _save = _form.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var data = self.getRadioCheckedRow(_dom);
                console.log(data.length)
                if (data) {
                    var cmd = '';
                    var taskType = _form.find("[name='taskType']").val();
                    var dealOpId = _form.find("[name='dealOpId']").val();
                    var taskId = _form.find("[name='taskId']").val();
                    cmd = "taskType=" + taskType + "&dealOpId=" + dealOpId + "&onlinePlan=" + data.onlinePlan + "&onlinePlanName=" + data.onlinePlanName;
                    if (Data.opreation == "update") {
                        cmd = cmd + "&taskId=" + taskId;
                        Utils.checkForm(_form, function() {
                            Rose.ajax.postJson(srvMap.get("saveOnlineReviewTask"), cmd, function(json, status) {
                                if (status) {
                                    window.XMS.msgbox.show('保存成功！', 'success', 2000);
                                    setTimeout(function() {
                                        self.getOnlineReviewTaskDistributeList();
                                        _form.find("[name='taskType']").attr("disabled", false);
                                        _form.find("[name='reset']").click();
                                        Data.opreation = 'new';
                                    }, 1000)
                                }
                            });
                        })
                    } else {
                        Utils.checkForm(_form, function() {
                            for (var i = 0; i < _data.length; i++) {
                                if (taskType == _data[i].taskType) {
                                    window.XMS.msgbox.show('同一计划下已分派的任务类型不能再次分派！', 'error', 2000)
                                    return;
                                }
                            }
                            Rose.ajax.postJson(srvMap.get("saveOnlineReviewTask"), cmd, function(json, status) {
                                if (status) {
                                    window.XMS.msgbox.show('保存成功！', 'success', 2000);
                                    setTimeout(function() {
                                        self.getOnlineReviewTaskDistributeList();
                                        _form.find("[name='taskType']").attr("disabled", false);
                                        _form.find("[name='reset']").click();
                                        Data.opreation = 'new';
                                    }, 1000)
                                }
                            });
                        })
                    }

                }
            });
        },
        //修改已分派子任务
        updateOnlineTask: function() {
            var self = this;
            var _dom = Page.findModalCId('getOnlineReviewTaskDistributeList');
            var _form = Page.findModalCId('addOnlineReviewTaskDistributeForm');
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function() {
                var dataTemp = self.getCheckboxCheckedRow(_dom);
                console.log(dataTemp)
                if (dataTemp) {
                    _form.find("[name='taskType']").val(dataTemp.taskType).attr("disabled", true);
                    _form.find("[name='dealOpId']").val(dataTemp.dealOpId);
                    _form.find("[name='taskId']").val(dataTemp.taskId);
                    Data.opreation = "update";

                }
            })
        },
        //
        // 删除已关联用例
        delOnlineTask: function() {
            var self = this;
            var dom = Page.findId('getOnlineReviewTaskList');
            var _dom = Page.findModalCId('getOnlineReviewTaskDistributeList');
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
                        Rose.ajax.postJson(srvMap.get("delOnlineReviewTask"), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('删除成功！', 'success', 2000);
                                setTimeout(function() {
                                    self.getOnlineReviewTaskDistributeList();
                                    _form.find("[name='reset']").click();
                                }, 1000)
                            }
                        });
                    }
                }
            })
        },
        //获取选中变更计划的数据
        getRadioCheckedRow: function(obj) {
            //alert($(obj).html());
            var _obj = $(obj).find("input[type='radio']:checked");
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
            var _obj = Page.findModalCId('getOnlineReviewTaskDistributeList').find("input[type='checkbox']:checked").parents("tr");
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
        // 事件：假分页(滚动条)
        initPaging: function(obj, scrollX) {
            obj.find("table").DataTable({
                "paging": false,
                "lengthChange": false,
                "searching": false,
                "ordering": false,
                "autoWidth": false,
                "info": false,
                "language": {
                    "emptyTable": "暂无数据...",
                    "infoEmpty": "第0-0条，共0条"
                },
                "scrollX": scrollX
            });
        },
        registerHelper: function() {
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
            Handlebars.registerHelper('getPlanState', function(value, fn) {
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
                    return "取消";
                }
            });
            Handlebars.registerHelper('getTaskType', function(value, fn) {
                if (value == "4") {
                    return "生产回归";
                }
                if (value == "9") {
                    return "发布任务分派";
                }
                if (value == "11") {
                    return "监控部署任务";
                }
            });
            Handlebars.registerHelper('getDealState', function(value, fn) {
                if (value == "0") {
                    return "新增";
                }
                if (value == "1") {
                    return "处理中";
                }
                if (value == "2") {
                    return "完成";
                }
                if (value == "3") {
                    return "取消";
                }
            });
        },

    };
    module.exports = Query;
});
