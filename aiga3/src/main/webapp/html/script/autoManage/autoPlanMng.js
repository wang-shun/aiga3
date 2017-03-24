define(function(require, exports, module) {
    // 路径重命名
    var pathAlias = "autoManage/autoPlanMng/";
    var Utils = require("global/utils.js");
    var planTag; //计划编号
    var nowPlanId;

    // 计划列表显示
    srvMap.add("getAutoPlanList", pathAlias + "autoPlanList.json", "sys/autoPlan/queryList");
    //计划保存接口
    srvMap.add("saveAutoPlan", pathAlias + "autoPlanList.json", "sys/autoPlan/save");
    //删除计划接口
    srvMap.add("deleAutoPlan", pathAlias + "autoPlanList.json", "sys/autoPlan/delete");
    //关联用例
    srvMap.add("linkCase", pathAlias + "autoPlanList.json", "sys/autoPlan/connectCase");
    //关联用例组
    srvMap.add("linkCaseGroup", pathAlias + "autoPlanList.json", "sys/autoPlan/connectGroup");
    //关联用例集
    srvMap.add("linkCaseCollect", pathAlias + "autoPlanList.json", "sys/autoPlan/connectCollect");

    //查询未关联用例
    srvMap.add("unLinkCaseList", pathAlias + "unLinkCaseList.json", "sys/autoPlan/queryUnconnectCase");
    //查询已关联用例
    srvMap.add("linkCaseList", pathAlias + "unLinkCaseList.json", "sys/autoPlan/queryConnectCase");
    //取消关联用例
    srvMap.add("deleLinkCase", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectCase");

    //查询未关联用例组
    srvMap.add("unLinkCaseGroupList", pathAlias + "unLinkCaseGroupList.json", "sys/autoPlan/queryUnconnectGroup");
    //查询关联用例组
    srvMap.add("linkCaseGroupList", pathAlias + "unLinkCaseGroupList.json", "sys/autoPlan/queryConnectGroup");
    //取消关联用例组
    srvMap.add("deleLinkCaseGroup", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectGroup");

    //查询未关联用例集
    srvMap.add("unLinkCaseCollectList", pathAlias + "unLinkCaseCollectList.json", "sys/autoPlan/queryUnconnectCollect");
    //查询关联用例集
    srvMap.add("linkCaseCollectList", pathAlias + "unLinkCaseCollectList.json", "sys/autoPlan/queryConnectCollect");
    //取消关联用例集
    srvMap.add("deleLinkCaseCollect", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectCollect");
    //机器列表查询接口
    srvMap.add("machineList", pathAlias + "machineList.json", "sys/machine/list");
    //生成任务接口
    srvMap.add("newTaskInfo", pathAlias + "machineList.json", "auto/task/start");
    //默认执行
    srvMap.add("runPlan", pathAlias + "machineList.json", "auto/task/defaultStart");

    // 模板对象
    var Tpl = {
        getAutoPlanList: require('tpl/autoManage/autoPlanMng/autoPlanList.tpl'), //计划列表
        modalAutoPlanInfo: require('tpl/autoManage/autoPlanMng/modalAutoPlanInfo.tpl'), //modal新增
        modalLinkCase: require('tpl/autoManage/autoPlanMng/modalLinkCase.tpl'), //modal关联
        modalNewTaskInfo: require('tpl/autoManage/autoPlanMng/modalNewTaskInfo.tpl'), //modal生成任务

        autoCaseList: require('tpl/autoManage/autoPlanMng/autoCaseList.tpl'), //用例列表
        caseGroupList: require('tpl/autoManage/autoPlanMng/caseGroupList.tpl'), //用例组列表
        caseCollectList: require('tpl/autoManage/autoPlanMng/caseCollectList.tpl'), //用例集列表

        machineList: require('tpl/autoManage/autoPlanMng/machineList.tpl'), //主机列表

    };

    // 容器对象
    var Dom = {
        getAutoPlanList: '#JS_getAutoPlanList', //table对象
        queryPlanForm: '#JS_queryPlanForm', //查询表单

        planInfoForm: '#Js_planInfoForm',
        modalPlanForm: '#modal_autoPlanForm',
        //modal

        modalLinkTestCase: '#modal_linkTestCase',
        modalNewTaskForm: '#modal_newTaskForm',


        //按钮组
        addPlan: '#JS_addPlan',
        btnLinkCase: '#JS_linkCase',
        btnRunPlan: '#JS_runPlan',
        btnNewTask: '#JS_newTask',
    };


    var Init = {
        init: function() {
            this._render();
        },
        _render: function() {
            
            this.hdbarHelp();
            this.getPlanList();
            this.queryAutoPlan();
            this.addAutoPlan();
            this.deleAutoPlan();
            this.linkCase();
            this.newTask();
            this.runPlan();
        },

        hdbarHelp: function() {
            Handlebars.registerHelper("transformatRunType", function(value) {
                if (value == 1) {
                    return "立即执行";
                } else if (value == 2) {
                    return "定时执行";
                } else if (value == 3) {
                    return "分布式执行";
                }
            });
            Handlebars.registerHelper("transformatCycleType", function(value) {
                if (value == 1) {
                    return "不轮循";
                } else if (value == 2) {
                    return "轮循";
                }
            });
            Handlebars.registerHelper("transformatStatus", function(value) {
                if (value == 1) {
                    return "离线";
                } else if (value == 2) {
                    return "空闲";
                } else if (value == 3) {
                    return "占用";
                }
            });
        },
        getPlanList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getAutoPlanList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getAutoPlanList);
                    console.log(json.data)
                    $(Dom.getAutoPlanList).html(template(json.data.content));
                    self.eventClickChecked($(Dom.getAutoPlanList));


                    Utils.eventDClickCallback($(Dom.getAutoPlanList), function() {
                        self.editAutoPlan();
                    });
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
        },
        queryAutoPlan: function() {
            var self = this;
            var _form = $(Dom.queryPlanForm);
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                    var cmd = $(Dom.queryPlanForm).serialize();
                    self.getPlanList(cmd);
                })
                // 表单重置
            _form.find('button[name="reset"]').bind('click', function() {
                $("#queryPlanName").val('');
                $("#query_runType").val('');
                $("#createTime").val('');
                $("#updateTime").val('');
            });
        },
        //新增计划
        addAutoPlan: function() {
            var self = this;
            $(Dom.addPlan).bind('click', function() {
                var time = self.getDate();
                $(Dom.modalPlanForm).find("h4").html("新增计划");
                $(Dom.modalPlanForm).find("[name='planTag']").val(time.planTag);
                $(Dom.modalPlanForm).find("[name='planId']").val('');
                $(Dom.modalPlanForm).find("[name='planName']").val('');
                $(Dom.modalPlanForm).find("[name='cycleType']").val('');
                $(Dom.modalPlanForm).find("[name='runType']").val('');
                $(Dom.modalPlanForm).find("[name='machineIp']").val('');
            });
            $(Dom.modalPlanForm).find("button[name='submit']").bind('click', function() {
                var cmd = $(Dom.planInfoForm).serialize();
                self.saveAutoPlan(cmd);
                $(Dom.modalPlanForm).modal('hide');
            });
            $(Dom.modalPlanForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalPlanForm).modal('hide');
            });
        },
        //查看修改计划
        editAutoPlan: function() {
            var data = this.getPlanInfo();
            if (data) {
                $(Dom.modalPlanForm).modal('show');
                $(Dom.modalPlanForm).find("h4").html("修改计划");
                $(Dom.modalPlanForm).find("[name='planId']").val(data.planId);
                $(Dom.modalPlanForm).find("[name='planTag']").val(data.planTag);
                $(Dom.modalPlanForm).find("[name='planName']").val(data.planName);
                $(Dom.modalPlanForm).find("[name='cycleType']").val(data.cycleType);
                $(Dom.modalPlanForm).find("[name='runType']").val(data.runType);
                $(Dom.modalPlanForm).find("[name='machineIp']").val(data.machineIp);
                $(Dom.modalPlanForm).find("[name='creatorId']").val(data.creatorId);
                $(Dom.modalPlanForm).find("[name='createTime']").val(data.createTime);
            }
        },
        //保存计划
        saveAutoPlan: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('saveAutoPlan'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show('计划保存成功！', 'success', 2000);
                    setTimeout(function() {
                        self.getPlanList();
                    }, 1000)
                }
            });
        },
        //删除计划
        deleAutoPlan: function() {
            var self = this;
            $("#JS_delePlan").bind('click', function() {
                var cmd = 'planIds=';
                var id;
                $(Dom.getAutoPlanList).find("tr").each(function() {
                    var tdArr = $(this).children();
                    if (tdArr.eq(0).find("input").is(':checked')) {
                        id = tdArr.eq(0).find("input").val();
                        cmd += id + ',';
                    }
                });
                cmd = cmd.substring(0, cmd.length - 1);
                if (id) {
                    Rose.ajax.getJson(srvMap.get('deleAutoPlan'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('计划删除成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getPlanList();
                            }, 1000)
                        }
                    });
                } else {
                    window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
                }

            });
        },
        //关联用例
        linkCase: function() {
            var self = this;
            var states = 1;
            $(Dom.btnLinkCase).bind('click', function() {
                var data = self.getPlanInfo();
                if (data) {
                    nowPlanId = data.planId;
                    $(Dom.modalLinkTestCase).modal('show');
                    var template = Handlebars.compile(Tpl.modalLinkCase);
                    var id = {
                        planId: data.planId
                    }
                    $(Dom.modalLinkTestCase).find(".modal-content").html(template(id));
                    self.getUnLinkCaseList("planId=" + data.planId);
                    $("#JS_casetable1").bind('click', function() {
                        states = 1;
                        self.getUnLinkCaseList("planId=" + data.planId);
                    });
                    $("#JS_casetable2").bind('click', function() {
                        states = 2;
                        self.getUnLinkGroupList("planId=" + data.planId);
                    });
                    $("#JS_casetable3").bind('click', function() {
                        states = 3;
                        self.getUnLinkCollectList("planId=" + data.planId);
                    });

                    //三个关联按钮
                    $("#JS_queryUnlinkCaseForm").find("button[name='link']").bind('click', function() {
                        var cmd = "planId=" + data.planId + "&caseIds=";
                        var ids = self.getCheckedRowId("#JS_unLinkCaseList", cmd, "请先选择一个用例！");
                        if (ids) {
                            self.linkCasees(ids);
                        }
                    });
                    $("#Js_queryUnlinkCaseGroupForm").find("button[name='link']").bind('click', function() {
                        var cmd = "planId=" + data.planId + "&groupIds=";
                        var ids = self.getCheckedRowId("#Js_unlinkCaseGroupList", cmd, "请先选择一个用例组！");
                        if (ids) {
                            self.linkCaseGroup(ids);
                        }
                    });
                    $("#Js_queryUnlinkCaseCollectForm").find("button[name='link']").bind('click', function() {
                        var cmd = "planId=" + data.planId + "&collectIds=";
                        var ids = self.getCheckedRowId("#Js_unlinkCaseCollectList", cmd, "请先选择一个用例集！");
                        if (ids) {
                            self.linkCaseCollect(ids);
                        }
                    });

                    //三个取消关联
                    $("#delLinked").bind('click', function() {
                        var cmd = '';
                        var ids = self.getCheckedRowId("#Js_linked", cmd, "请选择已关联项！");
                        console.log(ids);
                        if (ids) {
                            self.deleLinked(ids, states);
                        }
                    });


                    //三个查询
                    $("#JS_queryUnlinkCaseForm").find("button[name='submit']").bind('click', function() {
                        var cmd = $("#JS_queryUnlinkCaseForm").serialize();
                        self.getUnLinkCaseList(cmd);
                    });
                    $("#Js_queryUnlinkCaseGroupForm").find("button[name='submit']").bind('click', function() {
                        var cmd = $("#Js_queryUnlinkCaseGroupForm").serialize();
                        self.getUnLinkGroupList(cmd);
                    });
                    $("#Js_queryUnlinkCaseCollectForm").find("button[name='submit']").bind('click', function() {
                        var cmd = $("#Js_queryUnlinkCaseCollectForm").serialize();
                        self.getUnLinkCollectList(cmd);
                    });

                }
            });
        },
        //生成任务
        newTask: function() {
            var self = this;
            var runType;
            $(Dom.btnNewTask).bind('click', function() {
                var data = self.getPlanInfo();
                if (data) {
                    nowPlanId = data.planId;
                    runType = data.runType;
                    $(Dom.modalNewTaskForm).modal('show');
                    var template = Handlebars.compile(Tpl.modalNewTaskInfo);
                    var id = {
                        planId: data.planId
                    }
                    $(Dom.modalNewTaskForm).find(".modal-body").html(template(id));
                    var selctCycleType = $(Dom.modalNewTaskForm).find("select[name='cycleType']");
                    var selctRunType = $(Dom.modalNewTaskForm).find("select[name='runType']");
                    selctCycleType.val(data.cycleType);
                    selctRunType.val(data.runType);

                    selctCycleType.change(function(event) {
                        /* Act on the event */
                        if (selctCycleType.val() == 1) {
                            $("#Js_cycleInput").addClass("hide");
                            $("#inputSmsType").addClass("hide");
                        } else if (selctCycleType.val() == 2) {
                            $("#Js_cycleInput").removeClass("hide");
                            $("#inputSmsType").removeClass("hide");
                        }
                    });
                    selctRunType.change(function(event) {
                        /* Act on the event */
                        runType = selctRunType.val();
                        if (selctRunType.val() == 2) {
                            $("#inputRunTime").removeClass("hide");
                        } else {
                            $("#inputRunTime").addClass("hide");

                        }
                    });
                    selctCycleType.change();
                    selctRunType.change();

                    self.getMachineList("status=2", true);
                    self.getMachineList("status=3", false);


                    $(Dom.modalNewTaskForm).find("button[name='submit']").bind('click', function() {
                        var cmd = $("#Js_queryMachine").serialize();
                        if (cmd) {
                            self.getMachineList(cmd, true);
                        } else {
                            self.getMachineList("status=2", true);
                            self.getMachineList("status=3", false);
                        }
                    });

                    $(Dom.modalNewTaskForm).find("button[name='using']").bind('click', function() {
                        var _obj = self.getCheckedRow("#Js_chooseMachineList");
                        var cmd = '';
                        if (_obj.length) {
                            if (_obj.length > 1 && runType != 3) {
                                window.XMS.msgbox.show("请选择一台主机！", 'error', 2000);
                                return;
                            } else {
                                _obj.each(function() {
                                    var tdArr = $(this).children();
                                    ip = tdArr.eq(1).find("input").val();
                                    cmd += ip + ',';
                                });
                                cmd = cmd.substring(0, cmd.length - 1);
                                $("#taskMachineIp").val(cmd);
                            }
                        } else {
                            window.XMS.msgbox.show("请选择主机！", 'error', 2000);
                            return;
                        }
                    });

                    $(Dom.modalNewTaskForm).find("button[name='save']").bind('click', function() {
                        var cmd = $("#Js_taskForm").serialize();
                        alert(cmd);
                        self.saveNewTaks(cmd);
                        $(Dom.modalNewTaskForm).modal('hide');
                    });
                    $(Dom.modalNewTaskForm).find("button[name='cancel']").bind('click', function() {
                        $(Dom.modalNewTaskForm).modal('hide');
                    });

                }
            });
        },
        //默认执行
        runPlan: function() {
            $(Dom.btnRunPlan).bind('click', function() {
                var self = this;
                var data = self.getPlanInfo();
                if (data) {
                    var cmd = 'planId=' + data.planId;
                    Rose.ajax.getJson(srvMap.get('runPlan'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('任务生成成功！', 'success', 2000);
                        }
                    });
                }
            });
        },
        //生成任务单
        saveNewTaks: function(cmd) {
            Rose.ajax.getJson(srvMap.get('newTaskInfo'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show('任务生成成功！', 'success', 2000);
                }
            });
        },

        //获取主机列表
        getMachineList: function(cmd, empty) {
            var self = this
            if (empty) {
                $(Dom.modalNewTaskForm).find("tbody").empty();
            }
            Rose.ajax.getJson(srvMap.get('machineList'), cmd, function(json, status) {
                if (status) {
                    console.log(json.data);
                    var template = Handlebars.compile(Tpl.machineList);
                    $(Dom.modalNewTaskForm).find("tbody").append(template(json.data.content));
                    Utils.eventClickChecked($(Dom.modalNewTaskForm).find("tbody"));
                }
            });
        },

        //获取选中行id
        getCheckedRowId: function(obj, cmd, message) {
            var _obj = this.getCheckedRow(obj);
            if (_obj.length) {
                _obj.each(function() {
                    var tdArr = $(this).children();
                    id = tdArr.eq(0).find("input").val();
                    alert(id);
                    cmd += id + ',';
                });
                cmd = cmd.substring(0, cmd.length - 1);
                return cmd;
            } else {
                window.XMS.msgbox.show(message, 'error', 2000);
                return;
            }
        },
        //删除关联
        deleLinked: function(cmd, state) {
            var self = this;
            if (state == 1) {
                cmd = "planId=" + nowPlanId + "&caseIds="+cmd;

                Rose.ajax.getJson(srvMap.get('deleLinkCase'), cmd, function(json, status) {
                    if (status) {
                        setTimeout(function() {
                            self.getUnLinkCaseList("planId=" + nowPlanId);
                            self.getLinkCaseList("planId=" + nowPlanId);
                        }, 1000)
                    }
                });
            } else if (state == 2) {
                cmd = "planId=" + nowPlanId + "&groupIds="+cmd;
                Rose.ajax.getJson(srvMap.get('deleLinkCaseGroup'), cmd, function(json, status) {
                    if (status) {
                        setTimeout(function() {
                            self.getUnLinkGroupList("planId=" + nowPlanId);
                            self.getLinkGroupList("planId=" + nowPlanId);
                        }, 1000)
                    }
                });
            } else if (state == 3) {
                cmd = "planId=" + nowPlanId + "&collectIds="+cmd;
                Rose.ajax.getJson(srvMap.get('deleLinkCaseCollect'), cmd, function(json, status) {
                    if (status) {
                        setTimeout(function() {
                            self.getUnLinkCollectList("planId=" + nowPlanId);
                            self.getLinkCollectList("planId=" + nowPlanId);
                       
                        }, 1000)
                    }
                });
            }
            window.XMS.msgbox.show('取消关联成功！', 'success', 2000)

        },
        //关联用例
        linkCasees: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCase'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show('关联成功！', 'success', 2000);
                    self.getUnLinkCaseList("planId=" + nowPlanId);
                    self.getLinkCaseList("planId=" + nowPlanId);
                }
            });

        },
        //关联用例组
        linkCaseGroup: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCaseGroup'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show(JSON.stringify(json.data.info), 'success', 2000);
                    self.getUnLinkGroupList("planId=" + nowPlanId);
                    self.getLinkGroupList("planId=" + nowPlanId);
                }
            });

        },
        //关联用例集
        linkCaseCollect: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCaseCollect'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show(JSON.stringify(json.data.info), 'success', 2000);
                    self.getUnLinkCollectList("planId=" + nowPlanId);
                    self.getLinkCollectList("planId=" + nowPlanId);
                }
            });

        },

        //未关联用例列表
        getUnLinkCaseList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('unLinkCaseList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.autoCaseList);
                    console.log(json.data)
                    var list = $("#JS_unLinkCaseList");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                }
            });
            self.getLinkCaseList("planId=" + nowPlanId);

        },
        //关联用例列表
        getLinkCaseList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCaseList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.autoCaseList);
                    console.log(json.data)
                    var list = $("#Js_linked");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
        },
        //未关联用例组列表
        getUnLinkGroupList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('unLinkCaseGroupList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.caseGroupList);
                    console.log(json.data)
                    var list = $("#Js_unlinkCaseGroupList");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
            self.getLinkGroupList("planId=" + nowPlanId);
        },
        //关联用例组列表
        getLinkGroupList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCaseGroupList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.caseGroupList);
                    console.log(json.data)
                    var list = $("#Js_linked");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
        },
        //未关联用例集列表
        getUnLinkCollectList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('unLinkCaseCollectList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.caseCollectList);
                    console.log(json.data)
                    var list = $("#Js_unlinkCaseCollectList");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
            self.getLinkCollectList("planId=" + nowPlanId);
        },
        //关联用例集列表
        getLinkCollectList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('linkCaseCollectList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.caseCollectList);
                    console.log(json.data)
                    var list = $("#Js_linked");
                    list.html(template(json.data.content));
                    Utils.eventClickChecked(list);
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
        },

        //获取选中当前计划数据
        getPlanInfo: function() {
            var obj = this.getCheckedRow(Dom.getAutoPlanList);
            var data = {
                planId: "",
                planTag: "",
                planName: "",
                cycleType: "",
                runType: "",
                creatorId: "",
                machineIp: "",
                createTime: "",
                updateTime: "",
            };
            if (obj.length == 0) {
                window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
                return;
            } else if (obj.length > 1) {
                window.XMS.msgbox.show('请选择一个计划！', 'error', 2000);
                return;

            } else {
                data.planId = obj.find("[name='planId']").val();
                data.planTag = obj.find("[name='planTag']").val();
                data.planName = obj.find("[name='planName']").val();
                data.cycleType = obj.find("[name='cycleType']").val();
                data.runType = obj.find("[name='runType']").val();
                data.creatorId = obj.find("[name='creatorId']").val();
                data.machineIp = obj.find("[name='machineIp']").val();
                data.createTime = obj.find("[name='createTime']").val();
                data.updateTime = obj.find("[name='updateTime']").val();
            }
            return data;
        },
        // 获取复选列表当前选中行
        getCheckedRow: function(obj) {
            var _obj = $(obj).find("input[type='checkbox']:checked").parents("tr");
            return _obj;
        },
        eventClickChecked: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });
            obj.find("tr").bind('click', function(event) {
                $(this).find('.minimal').iCheck('check');
                if (callback) {
                    callback();
                }
            });
        },
        getDate: function() {
            var myDate = new Date();
            //获取当前年
            var year = myDate.getFullYear();
            //获取当前月
            var month = myDate.getMonth() + 1;
            //获取当前日
            var date = myDate.getDate();
            var h = myDate.getHours(); //获取当前小时数(0-23)
            var m = myDate.getMinutes(); //获取当前分钟数(0-59)
            var s = myDate.getSeconds();
            var ms = myDate.getMilliseconds(); //获取当前毫秒数(0-999)

            var MM = this.prefixInteger(month, 2);
            var DD = this.prefixInteger(date, 2);
            var hh = this.prefixInteger(h, 2);
            var mm = this.prefixInteger(m, 2);
            var ss = this.prefixInteger(s, 2);
            var mms = this.prefixInteger(ms, 3);

            var planTag = 'AP' + year + MM + DD + hh + mm + ss + mms;
            var nowTime = year + '-' + MM + "-" + DD + " " + hh + ':' + mm + ":" + ss + ":" + mms;
            var timeData = {
                planTag: planTag,
                nowTime: nowTime,
            };
            return timeData;

        },
        prefixInteger: function(num, length) {
            return ("0000000000000000" + num).substr(-length);
        },

    };
    module.exports = Init;
});