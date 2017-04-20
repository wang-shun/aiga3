define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('onlineTaskDistribute');

    // 路径重命名
    var pathAlias = "netFlowManage/netFlow/onlineTaskDistribute/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //获取验收任务列表
    srvMap.add("getOnlineTaskList", pathAlias + "getOnlineTaskList.json", "accept/onlineTask/list");
    //获取子任务分派列表
    srvMap.add("getOnlineTaskDistributeList", pathAlias + "getOnlineTaskDistributeList.json", "accept/onlineTask/childList");
    //下拉菜单获取所有用例集
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
    //查看性能测试子任务列表JS_getInterfaceList
    srvMap.add("getPerSubtaskAssignmentList", pathAlias + "getOnlineTaskDistributeList.json", "accept/performanceTask/childList");
    //查看已关联接口addPerSubtaskAssignmentForm
    srvMap.add("getInterfaceList", pathAlias + "getInterfaceList.json", "accept/performanceTask/taskRequireList");
    //保存性能子任务addPerSubtaskAssignmentForm
    srvMap.add("addPerSubtaskAssignment", pathAlias + "retMessage.json", "accept/performanceTask/save");
    //删除性能子任务
    srvMap.add("delPerSubtaskAssignment", pathAlias + "retMessage.json", "accept/onlineTask/delete");
    //查找接口getInterfaceList
    srvMap.add("queInterfaceList", pathAlias + "getInterfaceList.json", "accept/performanceTask/interList");
    //delRelation关联接口
    srvMap.add("relation", pathAlias + "retMessage.json", "accept/performanceTask/taskRequireReal");
    //删除关联接口
    srvMap.add("delRelation", pathAlias + "retMessage.json", "accept/performanceTask/taskRequireDel");
    //分派addpProcessing
    srvMap.add("addpProcessing", pathAlias + "retMessage.json", "accept/performanceTask/perTaskDeal");

    // 模板对象
    var Tpl = {
        getOnlineTaskList: $("#TPL_getOnlineTaskList").html(),
        getOnlineTaskDistributeList: $("#TPL_getOnlineTaskDistributeList").html(),
        getManualResultList: $("#TPL_getManualResultList").html(),
        getAutoResultList: $("#TPL_getAutoResultList").html(),
        getPerSubtaskAssignmentList: $("#TPL_getPerSubtaskAssignmentList").html(),
        getInterfaceList:$("#TPL_getInterfaceList").html(),
        getCollectId:$("#TPL_getCollectId").html()
        

    };

    // 容器对象
    var Dom = {
        queryOnlineTaskForm: '#JS_queryOnlineTaskForm',
        getOnlineTaskList: '#JS_getOnlineTaskList',
        getOnlineTaskDistributeModal: '#JS_getOnlineTaskDistributeModal',
        addOnlineTaskDistributeForm: '#JS_addOnlineTaskDistributeForm',
        getOnlineTaskDistributeList: '#JS_getOnlineTaskDistributeList',
        getAutoResultList: "#JS_getAutoResultList",
        getAutoResultListModal: "#JS_getAutoResultListModal",
        getManualResultListModal: '#JS_getManualResultListModal',
        getManualResultList: '#JS_getManualResultList',
        getPerSubtaskAssignmentModal:'#JS_getPerSubtaskAssignmentModal',
        getPerSubtaskAssignmentList:'#JS_getPerSubtaskAssignmentList',
        addPerSubtaskAssignmentForm:'#JS_addPerSubtaskAssignmentForm',
        getInterfaceList:"#JS_getInterfaceList",
        queInterfaceForm:"#JS_queInterfaceForm",
        collectIdss:'#SELT_collectIdss'

    };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new",
        data:[],
        cm:"",
        taskIdl:"0",
        taskid:null,
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getOnlineTaskList();
            // 初始化查询表单
            this.queryOnlineTaskForm();
            //注册helper
            this.registerHelper();
        },
        // 按条件查询
        queryOnlineTaskForm: function() {
            var self = this;
            var _form = Page.findId('queryOnlineTaskForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getOnlineTaskList(cmd);
            });
        },
        // 查询计划变更列表
        getOnlineTaskList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getOnlineTaskList'), _cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Tpl.getOnlineTaskList);
                    $(Dom.getOnlineTaskList).html(template(json.data))
                        // self.getOnlineTaskDistributeList();
                    var _dom = $(Dom.getOnlineTaskList);
                    var _distribute = _dom.find("[name='distribute']");
                    _distribute.unbind('click');
                    _distribute.bind('click', function() {
                        var data = self.getRadioCheckedRow(_dom);
                        if (data) {
                            console.log("data="+data.taskType);
                            var cmd = 'taskId=' + data.taskId;
                            //存储到全局变量
                            Data.onlinePlanId = data.onlinePlan;
                            console.log(Data.onlinePlanId);

                            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                            if(data.taskType=="1"){
                            Rose.ajax.postJson(srvMap.get('getOnlineTaskDistributeList'), cmd, function(json, status) {
                                if (status) {
                                    window.XMS.msgbox.hide();
                                    var _form = $(Dom.addOnlineTaskDistributeForm);
                                    Utils.setSelectData(_form);

                                    //_form.find("[name='onlinePlanId']").val(data.onlinePlanId);
                                    // 显示弹框


                                    var _modal = $(Dom.getOnlineTaskDistributeModal);
                                    _modal.modal('show').on('shown.bs.modal', function() {
                                        var template = Handlebars.compile(Tpl.getOnlineTaskDistributeList);
                                        var _dom = $(Dom.getOnlineTaskDistributeList);
                                        
                                        _dom.html(template(json.data));
                                        // 初始化步骤
                                        Utils.initStep(_modal);
                                        
                                        self.addOnlineTask();
                                        self.updateOnlineTask();
                                        self.delOnlineTask();
                                        self.getRunResultList();
                                        var _close = _modal.find("[name='close']");
                                        _close.unbind('click');
                                        _close.bind('click', function() {
                                            self.getOnlineTaskList();
                                        })
                                        var _rest = _form.find("[name='reset']");
                                        _rest.unbind('click');
                                        _rest.bind('click', function() {
                                            _form.find("[name='collectId']").attr("disabled", false);
                                            Data.opreation = 'new';
                                        })
                                        Utils.eventTrClickCallback($(Dom.getOnlineTaskDistributeList))
                                            //设置分页
                                        self.initPaging(_dom, 5, true);
                                    })
                                }
                            });
                        }else if(data.taskType=="3"){
                            Data.data = data;
                            Data.cm = cmd;
                            console.log(Data.cm);
                            console.log(Data.data);
                            self.getPerSubtaskAssignmentList(cmd,data);

                        }
                        }
                    });

                    Utils.eventTrClickCallback($(Dom.getOnlineTaskList))

                    //设置分页
                    self.initPaging($(Dom.getOnlineTaskList), 8, true)

                }
            });

        },
        //显示性能子功能弹框
        getPerSubtaskAssignmentList: function(cmd,date){
            var self = this;
                    // 显示弹框
                    var _modal = $(Dom.getPerSubtaskAssignmentModal);
                    _modal.modal('show').on('shown.bs.modal', function() {
                        self.quePerSubtaskAssignmentList(cmd,date);
                        // 初始化步骤
                        Utils.initStep(_modal);
                        
                        self.getInterfaceList("onlinePlan="+Data.onlinePlanId);

                        var _close = _modal.find("[name='close']");
                        _close.unbind('click');
                        _close.bind('click', function() {
                            self.getOnlineTaskList();
                        })
                        
                    })
        },
        quePerSubtaskAssignmentList:function(cm,date){
            var self=this;
            Rose.ajax.postJson(srvMap.get('getPerSubtaskAssignmentList'), cm, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var _form = $(Dom.addPerSubtaskAssignmentForm);
                    Utils.setSelectData(_form);
                    _form.find("[name='onlinePlan']").val(date.onlinePlan);
                    _form.find("[name='onlinePlanName']").val(date.onlinePlanName);
                    _form.find("[name='parentTaskName']").val(date.taskName);
                    _form.find("[name='parentTaskId']").val(date.taskId);
                    var template = Handlebars.compile(Tpl.getPerSubtaskAssignmentList);
                    var _dom = $(Dom.getPerSubtaskAssignmentList);
                    _dom.html(template(json.data));
                        //新增
                        self.addPerSubtaskAssignmentForm();
                        //修改
                        self.updatePerSubtaskAssignment();
                        //删除
                        self.delPerSubtaskAssignment(cm,date);
                        //分派
                        self.processingPerSubtaskAssignment();
                    // 绑定双击当前行事件
                        self.eventDClickCallback($(Dom.getPerSubtaskAssignmentList),function(){
                            var _date = self.getRadioCheckedRow(_dom);
                            var cmd = "onlinePlan=" + Data.onlinePlanId +"&taskId="+_date.taskId;
                            // 请求：用户基本信息
                            alert(cmd)
                            self.getInterfaceList(cmd);
                        })
                        Utils.eventTrClickCallback($(Dom.getPerSubtaskAssignmentList))
                            //设置分页

                        self.initPaging(_dom, 5, true);
                }
            });
        },
        //显示(查找)接口列表
        getInterfaceList: function(cmd){
            var self = this;
            Rose.ajax.postJson(srvMap.get('getInterfaceList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    // Utils.setSelectData(_form);
                    var template = Handlebars.compile(Tpl.getInterfaceList);
                    var _dom = $(Dom.getInterfaceList);
                    _dom.html(template(json.data));
                    Utils.eventTrClickCallback($(Dom.getInterfaceList))
                    //点击接口查询
                    self.search();
                    //
                    self.relation();
                    //删除关联关系
                    self.delRelation();
                    //设置分页
                    self.initPaging(_dom, 5, true);
                }
            });
        },
        //已关联接口查看
        // relationInterfaceList: function(cmd){
        //     var self = this;
        //     Rose.ajax.postJson(srvMap.get('getInterfaceList'), cmd, function(json, status) {
        //         if (status) {
        //             window.XMS.msgbox.hide();
        //             var template = Handlebars.compile(Tpl.getInterfaceList);
        //             var _dom = $(Dom.getInterfaceList);
        //             _dom.html(template(json.data));
        //             Utils.eventTrClickCallback($(Dom.getInterfaceList))
        //              //设置分页
        //             self.initPaging(_dom, 5, true);
        //         }
        //     });
        // },
        //新增性能子任务
        addPerSubtaskAssignmentForm : function(){
            var self = this;
            var _form = $(Dom.addPerSubtaskAssignmentForm)
            var _save = _form.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var cmd = _form.serialize();
                if (Dom.taskIdl=="1") {
                    Dom.taskIdl="0";
                    cmd += "&taskId="+Dom.taskid;
                }
                Rose.ajax.postJson(srvMap.get('addPerSubtaskAssignment'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('保存成功！', 'success', 2000);
                        setTimeout(function() {
                            self.quePerSubtaskAssignmentList(Data.cm,Data.data);
                        }, 1000)
                    }
                });
            });
        },
        //修改性能子任务
        updatePerSubtaskAssignment : function(){
            var self = this;
            var _dom = $(Dom.getPerSubtaskAssignmentList);
            var _form = $(Dom.addPerSubtaskAssignmentForm);
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function() {
                var _date = self.getRadioCheckedRow(_dom);
                if (_date) {
                    Dom.taskIdl = "1";
                    _form.find("[name='taskName']").val(_date.taskName);
                    _form.find("[name='taskType']").val(_date.taskType);
                     Dom.taskid=_date.taskId
                    // Data.opreation = "update";
                }
            });
        },

         // 分派性能子任务
        processingPerSubtaskAssignment: function() {
            var self = this;
            var _dom = $(Dom.getPerSubtaskAssignmentList);
            var _processing = _dom.find("[name='processing']");
            var _form = $("#JS_addpProcessing");
            Utils.setSelectData(_form);
            _processing.unbind('click');
            _processing.bind('click', function() {
                var _data = self.getRadioCheckedRow(_dom);
                console.log(_data);
                if (_data) {
                        _form.find("[name='taskId']").val(_data.taskId);
                        _form.find("[name='taskName']").val(_data.taskName);
                        _form.find("[name='dealOpId']").val(_data.dealOpId);
                        XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                        Utils.goStep(Dom.getPerSubtaskAssignmentModal, 2);
                        self.addpProcessing();
                }
            });
        },
        addpProcessing: function(){
            var _form = $("#JS_addpProcessing");
            var _save = _form.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
               var cmd = _form.serialize();
               Rose.ajax.postJson(srvMap.get('addpProcessing'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('保存成功！', 'success', 2000);
                        setTimeout(function() {
                            self.quePerSubtaskAssignmentList(Data.cm,Data.data);
                        }, 1000)
                    }
                });
            });
        },
        //删除性能子任务
        delPerSubtaskAssignment : function(cmd,date){
            var self = this;
            var _dom = $(Dom.getPerSubtaskAssignmentList);
            // var _form = $(Dom.addPerSubtaskAssignmentForm)
            var _del = _dom.find("[name='del']");
            _del.unbind('click');
            _del.bind('click', function() {
                var _date = self.getRadioCheckedRow(_dom);
                if (_date) {
                    var cm = "taskIds=" + _date.taskId+"&parentId=" + Data.data.taskId;
                    Rose.ajax.postJson(srvMap.get('delPerSubtaskAssignment'), cm, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000);
                            setTimeout(function() {
                                self.quePerSubtaskAssignmentList(Data.cm,Data.data);
                            }, 1000)
                        }
                    });
                }
            });
        },
        //查询接口点击事件
        search : function(){
            var self = this;
            var _form = $(Dom.queInterfaceForm);
            var _search = _form.find("[name='search']");
            _search.unbind('click');
            _search.bind('click', function() {
                var cmd = "onlinePlan="+Data.onlinePlanId+"&"+_form.serialize();
                self.getInterfaceList(cmd);
            });
        },
        //关联接口
        relation : function(){
            var self = this;
            var _relation = $(Dom.queInterfaceForm).find("[name='relation']");
            _relation.unbind('click');
            _relation.bind('click',function(){
                var _date = self.getRadioCheckedRow($(Dom.getPerSubtaskAssignmentList));
                if (_date) {
                var id;
                var serviceId;
                var list = [];
                var cmd={
                    "taskType":_date.taskType,
                    "taskId":_date.taskId,
                    "list" :[]
                };
                $(Dom.getInterfaceList).find("tbody").find("tr").each(function(){
                    var tdArr = $(this).children();
                    if(tdArr.eq(0).find("input").is(':checked')){
                        id = tdArr.eq(0).find("input").val();
                        serviceId = tdArr.eq(1).find("input").val();
                        cmd.list.push({
                            "id" : id,
                            "serviceId" : serviceId
                        });
                    }
                });
                // cmd.list.push(list);
                Rose.ajax.postJson(srvMap.get('relation'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('关联成功！', 'success', 2000);
                        setTimeout(function() {
                            self.getInterfaceList("onlinePlan=" + Data.onlinePlanId);
                            self.quePerSubtaskAssignmentList(Data.cm,Data.data);
                        }, 1000)
                    }
                });
            }
            });
        },
        //删除关联关系
        delRelation : function(){
            var self = this;
            var _delRelation = $(Dom.queInterfaceForm).find("[name='delRelation']");
            _delRelation.unbind('click');
            _delRelation.bind('click',function(){
                alert("1")
                var _date = self.getRadioCheckedRow($(Dom.getPerSubtaskAssignmentList));
                if (_date) {
                var id="";
                var num =0 ;
                var _checkObj = $(Dom.getInterfaceList).find("input[type='checkbox']:checked");
                if(_checkObj.length==0){
                   window.XMS.msgbox.show('请选择要删除的关联接口！', 'error', 2000);
                   return false;
                }
                _checkObj.each(function (){
                   if(num!=(_checkObj.length-1)){
                       id += $(this).val()+",";      
                   }else{
                       id += $(this).val();      
                   }
                   num ++;
                });
                cmd = "taskId="+ _date.taskId+"&ids="+id;
                Rose.ajax.postJson(srvMap.get('delRelation'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('删除成功！', 'success', 2000);
                        setTimeout(function() {
                            self.getInterfaceList("onlinePlan=" + Data.onlinePlanId);
                            self.quePerSubtaskAssignmentList(Data.cm,Data.data);
                        }, 1000)
                    }
                });
            }
            });
        },
        // 查询自动化执行结果详细信息
        getOnlineTaskDistributeList: function() {
            var self = this;
            var _dom = $(Dom.getOnlineTaskList);
            var data = self.getRadioCheckedRow(_dom);
            if (data) {
                var cmd = 'taskId=' + data.taskId;
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(srvMap.get('getOnlineTaskDistributeList'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.hide();
                        var _form = $(Dom.addOnlineTaskDistributeForm);
                        Utils.setSelectData(_form);
                        //_form.find("[name='onlinePlanId']").val(data.onlinePlanId);
                        var template = Handlebars.compile(Tpl.getOnlineTaskDistributeList);
                        var _dom = $(Dom.getOnlineTaskDistributeList);
                        _dom.html(template(json.data));
                        self.updateOnlineTask();
                        self.delOnlineTask();
                        self.getRunResultList();
                        Utils.eventTrClickCallback($(Dom.getOnlineTaskDistributeList))
                            //设置分页
                        self.initPaging(_dom, 5, true);
                    }
                });
            }
        },
        //新建回归子任务
        addOnlineTask: function() {
            var self = this;
            var _form = $(Dom.addOnlineTaskDistributeForm);
            var _dom = $(Dom.getOnlineTaskList);
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
                        Rose.ajax.postJson(srvMap.get("saveOnlineTask"), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('保存成功！', 'success', 2000);
                                setTimeout(function() {
                                    self.getOnlineTaskDistributeList();
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
        updateOnlineTask: function() {
            var self = this;
            var _dom = $(Dom.getOnlineTaskDistributeList);
            var _form = $(Dom.addOnlineTaskDistributeForm);
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
        delOnlineTask: function() {
            var self = this;
            var dom = $(Dom.getOnlineTaskList);
            var _dom = $(Dom.getOnlineTaskDistributeList);
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
                        Rose.ajax.postJson(srvMap.get("delOnlineTask"), cmd, function(json, status) {
                            if (status) {
                                window.XMS.msgbox.show('删除成功！', 'success', 2000);
                                setTimeout(function() {
                                    self.getOnlineTaskDistributeList();;
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
            var _dom = $(Dom.getOnlineTaskDistributeList);
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
                                Utils.goStep(Dom.getOnlineTaskDistributeModal, 2);
                                // 显示弹框
                                // var _modal = $(Dom.getManualResultListModal);
                                // _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Tpl.getManualResultList);
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
                                Utils.goStep(Dom.getOnlineTaskDistributeModal, 3);
                                // 显示弹框
                                // var _modal = $(Dom.getAutoResultListModal);
                                // _modal.modal('show').on('shown.bs.modal', function() {
                                var template = Handlebars.compile(Tpl.getAutoResultList);
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
            var _obj = $(Dom.getOnlineTaskDistributeList).find("input[type='checkbox']:checked").parents("tr");
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
        eventDClickCallback:function(obj,callback){
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
                if (value == "0") {
                    return "未分派";
                }
                if (value == "1") {
                    return "处理中";
                }
                if (value == "2") {
                    return "完成";
                }
                if (value == "3") {
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
            });
            Handlebars.registerHelper('getTaskType', function(value, fn) {
                if (value == "1") {
                    return "手工用例";
                }
                if (value == "2") {
                    return "自动化用例";
                }
                if (value == "0") {
                    return "用例组";
                }
            });
            Handlebars.registerHelper('getImportant', function(value, fn) {
                if (value == "1") {
                    return "一级用例";
                }
                if (value == "2") {
                    return "二级用例";
                }
                if (value == "3") {
                    return "三级用例";
                }
                if (value == "4") {
                    return "四级用例";
                }

            });
            Handlebars.registerHelper('getCaseState', function(value, fn) {
                if (value == "0") {
                    return "未处理";
                }
                if (value == "1") {
                    return "处理完";
                }
            });
            Handlebars.registerHelper('TaskTypes', function(value, fn) {
                if (value == "4") {
                    return "按需测试";
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
