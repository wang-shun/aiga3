define(function(require, exports, module) {
    // 路径重命名
    var pathAlias = "caseTempMng/";
    var Utils = require("global/utils.js");

    // 用例模板列表显示 ok
    srvMap.add("caseTempList", pathAlias + "caseTempList.json", "case/template/list");
    //系统大类下拉框显示 OK
    srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
    //系统子类下拉框 OK
    srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
    //功能点下拉框 OK
    srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");
    //删除模板 ok
    srvMap.add("delCaseTemp", pathAlias + "getFunList.json", "case/template/del");
    //获取模板信息 ok
    srvMap.add("getCaseTempInfo", pathAlias + "getCaseTempInfo.json", "case/template/get");
    //新增用例模板 Ok
    srvMap.add("addCaseTemp", pathAlias + "caseTempList.json", "case/template/save");
    //修改用例模板 
    srvMap.add("updateCaseTemp", pathAlias + "caseTempList.json", "case/template/update");
    //获取组件树 
    srvMap.add("getCompTree", pathAlias + "getCompTree.json", "sys/cache/commenCompTree");
    //获取组件信息 OK
    srvMap.add("getCompinfo", "componentManage/getCompinfo.json", "sys/component/findone");
    //保存自动化模板
    srvMap.add("addAutoTestTemp", "componentManage/getCompinfo.json", "auto/template/saveList");
    //保存测试用例
    srvMap.add("addTestCase", "componentManage/getCompinfo.json", "case/instance/save");
    //业务接口 
    srvMap.add("getBusiList", pathAlias + "getBusiList.json", "sys/cache/busi");

    //接口列表
    srvMap.add("getESBInterfaceList", pathAlias + "esbInterfaceList.json", "case/template/EsbList");
    //根据接口获取因子
    srvMap.add("getFactorsList", pathAlias + "esbInterfaceList.json", "case/template/getFactor");


    // 模板对象
    var Tpl = {
        getCaseTempList: $("#TPL_caseTempList").html(),
        getSysList: $("#TPL_getSysList").html(),
        getSubSysList: $("#TPL_getSubSysList").html(),
        getBusiList: $("#TPL_getBusiList").html(),
        getFunList: $("#TPL_getFunList").html(),
        getCaseTempForm: $("#TPL_getCaseTempForm").html(),
        getFactorList: $("#TPL_getFactorList").html(),
        compList: $("#TPL_compList").html(),
        // getFactorForm: $("#TPL_getFactorForm").html(),
        getTestFactorList: $("#TPL_getTestFactorList").html(),
        getCaseTempInfo: $("#TPL_getCaseTempInfo").html(),
        getESBInterfaceList: $("#TPL_getESBInterfaceList").html(),
        getCBOSSInterfaceList: $("#TPL_getCBOSSInterfaceList").html(),

    };

    // 容器对象
    var Dom = {
        getCaseTempList: '#JS_caseTempList', //table对象
        getSysList: '#query_sysId',
        getSubsysList: '#query_subSysId',
        getFunList: '#query_funId',

        //功能按钮
        addCaseTemp: '#JS_addCaseTemp', //新增模板
        deleCaseTemp: '#JS_deleCaseTemp', //删除
        viewCaseTemp: '#JS_viewCaseTemp', //查看与编辑
        createAutoTestTemp: '#JS_createAutoTestTemp', //自动化模板生成
        createTest: '#JS_createTest', //用例生成

        //modal
        modalCaseTempForm: '#modal_CaseTempForm',
        modalAutoTempForm: '#modal_AutoCaseTempForm',
        modalTestCaseForm: '#modal_testCaseForm',
        caseTempForm: '#JS_CaseTempForm',
        testForm: '#JS_TestForm',

        queryCaseTempForm: '#JS_queryCaseTempForm',
        //组件树
        compTree: '#compTree',

        //
        factorForm: '#JS_factorForm',
        factorList: '#JS_factorList',
        testFactorList: '#JS_testCaseFactorList',

        //
        interFaceInfoForm: "#JS_interfaceInfoForm",
    };

    //下拉框容器
    var dropChoice1 = {

        getSysList: '#query_sysId',
        getSubsysList: '#query_subSysId',
        getFunList: '#query_funId',
        getBusiList: '#query_busi',
    };
    var dropChoice2 = {

        getSysList: '#add_sysId',
        getSubsysList: '#add_subSysId',
        getFunList: '#add_funId',
        getBusiList: '#add_busiId',

    }


    var busiData = null;
    var caseType = null;

    var Init = {

        init: function() {
            this._render();
        },
        _render: function() {

            this.getBusiList(dropChoice1);
            this.getCaseTempList();
            this.getSysList(dropChoice1);
            this.addCaseTemp();
            this.queryCaseTemp();
            this.editCaseTemp();
            this.newAutoCaseTemp();
            this.newTestCase();

        },

        getBusiList: function(obj, data) {
            Rose.ajax.getJson(srvMap.get('getBusiList'), '', function(json, status) {
                if (status) {
                    busiData = json.data;
                    var template = Handlebars.compile(Tpl.getBusiList);
                    $(obj.getBusiList).find("select").html(template(json.data));
                    if (data) {
                        $(obj.getBusiList).find("select").val(data.busiId);
                    }
                    console.log(json.data)
                }

            });
        },
        //系统大类下拉框
        getSysList: function(obj, data) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getSysList'), '', function(json, status) {
                if (status) {
                    if (data) {
                        var template = Handlebars.compile(Tpl.getSysList);
                        $(obj.getSysList).find("select").html(template(json.data));
                        self.sysSelected(obj, data);
                        self.getBusiList(obj, data);
                        $(obj.getSysList).find("select").val(data.sysId);
                        $(obj.getSysList).find("select").change();
                    } else {
                        var template = Handlebars.compile(Tpl.getSysList);
                        $(obj.getSysList).find("select").html(template(json.data));
                        self.sysSelected(obj);
                        self.getBusiList(obj);
                        console.log(json.data)

                    }
                }

            });
        },

        //系统大类下拉框选择事件
        sysSelected: function(obj, data) {
            var self = this;
            $(obj.getSysList).find("select").change(function() {
                var id = $(obj.getSysList).find("select").val();
                console.log(id);
                if (data) {
                    self.getSubSysList(id, obj, data);
                } else {
                    self.getSubSysList(id, obj);
                }
            });
        },

        //系统子类下拉框选择事件
        subsysSelected: function(obj, data) {
            var self = this;
            $(obj.getSubsysList).find("select").change(function() {
                var id = $(obj.getSubsysList).find("select").val();
                if (data) {
                    self.getFunList(id, obj, data);
                } else {
                    self.getFunList(id, obj);
                }
            });
        },
        //系统子类下拉框
        getSubSysList: function(id, obj, data) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getSubsysList'), 'sysId=' + id, function(json, status) {
                if (status) {
                    if (data) {
                        var template = Handlebars.compile(Tpl.getSubSysList);
                        $(obj.getSubsysList).find("select").html(template(json.data));
                        console.log(json.data)
                        self.subsysSelected(obj, data);
                        $(obj.getSubsysList).find("select").val(data.sysSubId);
                        $(obj.getSubsysList).find("select").change();
                    } else {
                        var template = Handlebars.compile(Tpl.getSubSysList);
                        $(obj.getSubsysList).find("select").html(template(json.data));
                        console.log(json.data)
                        self.subsysSelected(obj);
                    }
                }
            });
        },
        //功能点下拉框
        getFunList: function(id, obj, data) {
            Rose.ajax.getJson(srvMap.get('getFunList'), 'sysSubId=' + id, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getFunList);
                    $(obj.getFunList).find("select").html(template(json.data));
                    console.log(json.data)
                    if (data) {
                        $(obj.getFunList).find("select").val(data.funId);
                    }
                }
            });
        },

        // 用例模板列表
        getCaseTempList: function(cmd) {
            var self = this;
            // cmd = cmd + '&pageNum=' + currentPage;
            var cur_sysId;
            var cur_subsysId;
            var _dom = $(Dom.getCaseTempList).find("[name='pagination']");
            Handlebars.registerHelper("transformatImp", function(value) {
                if (value == 1) {
                    return "一级用例";
                } else if (value == 2) {
                    return "二级用例";
                } else if (value == 3) {
                    return "三级用例";
                } else if (value == 4) {
                    return "四级用例";
                }
            });
            Handlebars.registerHelper("transformatType", function(value) {
                if (value == 1) {
                    return "UI类";
                } else if (value == 2) {
                    return "接口类";
                } else if (value == 3) {
                    return "后台进程类";
                }
            });
            Handlebars.registerHelper("transformatBusi", function(value) {
                var _val = value;
                var name;
                $.each(busiData, function(n, value) {
                    if (_val == value.busiId) {
                        name = value.busiName;
                    }
                });
                return name;

            });

            Utils.getServerPage(srvMap.get('caseTempList'), cmd, function(json) {
                var _tbody = $(Dom.getCaseTempList).find("tbody");
                var template = Handlebars.compile(Tpl.getCaseTempList);
                console.log(json.data)
                _tbody.html(template(json.data.content));
                self.deleCaseTemp();
                Utils.eventTrClickCallback($(Dom.getCaseTempList), function() {
                    $(Dom.viewCaseTemp).click();
                })

            }, _dom);
        },
        // 按条件查询模板
        queryCaseTemp: function() {
            var self = this;
            var _form = $(Dom.queryCaseTempForm);
            // 表单校验初始化
            //_form.bootstrapValidator('validate');
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                var cmd = $(Dom.queryCaseTempForm).serialize();
                self.getCaseTempList(cmd);
                //});
            });

        },

        interfaceSelected: function() {
            var self = this;
            var _modal = $(Dom.modalCaseTempForm);
            var _selectType = _modal.find("select[name='caseType']");
            var _interfaceType = _modal.find("select[name='interfaceType']");
            var _tab2 = $("#JS_interfaceTab");
            console.log(_modal.find("button[name='queryInterface']"));
            _modal.find("button[name='queryInterface']").unbind();
            _modal.find("button[name='queryInterface']").bind('click',function(){
                //*[@id="JS_interface"]/div[1]/div/div[1]/input
                var _name = _modal.find("[name='QUERY_messageName']").val();
                self.getInterfaceList(_interfaceType.val(),_name);
            });
            _selectType.unbind();
            _selectType.change(function(event) {
                /* Act on the event */
                var type = _selectType.val();
                var _interface = _interfaceType.val();
                if (type == 2) {
                    $("#JS_interfaceType").removeClass('hide');
                    if (_interface) {
                        _tab2.removeClass('hide');
                        self.getInterfaceList(_interface,'');
                        if (_interface == 1) {
                            $("#JS_msgType").addClass('hide');
                        } else {
                            $("#JS_msgType").removeClass('hide');
                        }
                    }

                } else {
                    _tab2.addClass('hide')
                    $("#JS_interfaceType").addClass('hide')

                }
            });
            _selectType.change();
            _interfaceType.change(function(event) {
                /* Act on the event */
                _selectType.change();
            });
        },
        //获取接口列表
        getInterfaceList: function(interfaceType,messageName) {
            var self = this;
            var _tpl;
            var _srv;
            var _name;
            if (interfaceType == 1) {
                tpl = Tpl.getESBInterfaceList;
                _srv = "getESBInterfaceList";
                _name = "esbName=";
            } else if (interfaceType == 1) {
                tpl = Tpl.getCBOSSInterfaceList;
                _srv = "getCBOSSInterfaceList";
                _name = "cbossName=";
            }

            var _dom = $("#JS_interface");
            var pagination = _dom.find("[name='pagination']")
            var _table = $("#JS_interface").find("table");
            Utils.getServerPage(srvMap.get(_srv), _name+messageName, function(json) {
                //加载模板
                var template = Handlebars.compile(Tpl.getESBInterfaceList);
                $("#JS_interface").find("tbody").html(template(json.data.content));
                //单击选中接口事件
                self.eventClickChecked(_table, function() {
                    var _data = self.getCheckedRow(_table, true);
                    //赋值
                    $(Dom.interFaceInfoForm).find("[name='messageId']").val(_data.Id);
                    $(Dom.interFaceInfoForm).find("[name='messageName']").val(_data.Name);
                    //加载因子列表
                    var cmd = "messageId=" + _data.Id + "&interfaceType=" + interfaceType;
                    Rose.ajax.postJson(srvMap.get('getFactorsList'), cmd, function(json, status) {
                        var factor_template = Handlebars.compile(Tpl.getFactorList);

                        $(Dom.factorList).html(factor_template(json.data));
                        Utils.eventClickChecked($(Dom.factorList), function() {})
                    })

                });
            }, pagination);
        },

        // 新增模板
        addCaseTemp: function() {
            var self = this;
            $(Dom.addCaseTemp).bind('click', function() {

                $("#JS_factoryBody").slimScroll({
                    "height": '280px'
                });
                // 弹出层
                var _modal = $(Dom.modalCaseTempForm);
                var _form = $(Dom.caseTempForm);
                _modal.modal('show');
                _modal.find(".modal-title").html("新增模板");
                self.resetTab(_modal);
                //加载form表单
                var template = Handlebars.compile(Tpl.getCaseTempForm);
                _form.html(template());
                $(Dom.factorList).empty();
                //加载下拉框
                self.getSysList(dropChoice2)

                self.addFactor();
                self.deleFactor();
                self.interfaceSelected();
                //加载三个因子选项供填写
                var factor_template = Handlebars.compile(Tpl.getFactorList);
                var empty = {
                    'data': ''
                };
                $("#JS_messageAddFactor").hide();
                _modal.find("table").show();
                $(Dom.factorList).append(factor_template(empty));
                $(Dom.factorList).append(factor_template(empty));
                $(Dom.factorList).append(factor_template(empty));
                Utils.eventClickChecked($(Dom.factorList));

                var _formInterface = $(Dom.interFaceInfoForm);
                self.resetForm(_formInterface);
                $("#JS_SaveCaseTemp").unbind('click');
                $("#JS_SaveCaseTemp").bind('click', function() {
                    Utils.checkForm(_form, function() {
                        var cmd = _form.serialize();
                        if (_form.find("[name='caseType']").val() == 2) {
                            cmd += "&" + _formInterface.serialize();
                            if (_formInterface.find("[name='address']").val()) {

                            } else {
                                XMS.msgbox.show('发布地址未填写！', 'error', 2000)
                                return false;
                            }
                            if (_formInterface.find("[name='validParam']").val()) {

                            } else {
                                XMS.msgbox.show('比对参数未填写！', 'error', 2000)
                                return false;
                            }
                        }

                        var factors = [];
                        $(Dom.factorList).find("tr").each(function() {
                            var tdArr = $(this).children();
                            var order = $(this).find("[name='factorOrder']").val();
                            console.log(order);
                            factors.push({
                                "factorName": tdArr.eq(2).find("input").val(),
                                "remark": tdArr.eq(3).find("input").val(),
                                "factorOrder": order
                            });
                        });
                        cmd += "&factors=" + JSON.stringify(factors);
                        console.log(cmd);
                        Rose.ajax.postJson(srvMap.get('addCaseTemp'), cmd, function(json, status) {
                            if (status) {
                                // 添加用户成功后，刷新用户列表页
                                XMS.msgbox.show('添加模板成功！', 'success', 2000)
                                    // 关闭弹出层
                                $(Dom.modalCaseTempForm).modal('hide')
                                setTimeout(function() {
                                    self.getCaseTempList();
                                }, 1000)
                            }
                        });
                    })

                    // });
                })
            });
            $(Dom.modalCaseTempForm).find("button[name='cancel']").unbind('click');
            $(Dom.modalCaseTempForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalCaseTempForm).modal('hide');
            });
        },
        //查看与编辑
        editCaseTemp: function() {
            var self = this;
            $(Dom.viewCaseTemp).unbind();
            $(Dom.viewCaseTemp).bind('click', function() {

                var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
                if (_data) {
                    var _form = $(Dom.caseTempForm);
                    var _formInterface = $(Dom.interFaceInfoForm);
                    var _modal = $(Dom.modalCaseTempForm);
                    _modal.modal('show');
                    _modal.find(".modal-title").html("查看编辑模板");
                    self.resetTab(_modal);
                    //加载form表单
                    self.getCaseTempInfo("caseId=" + _data.caseId);
                    self.addFactor();
                    self.deleFactor();
                    self.interfaceSelected();

                    $("#JS_factoryBody").slimScroll({
                        "height": '300px'
                    });


                    // 表单提交
                    $("#JS_SaveCaseTemp").unbind('click')
                    $("#JS_SaveCaseTemp").bind('click', function() {
                        Utils.checkForm(_form, function() {
                            // 表单校验：成功后调取接口
                            //_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
                            var cmd = _form.serialize() + "&caseId=" + _data.caseId;
                            var id;
                            var name;
                            var remark;
                            var order;
                            if (_form.find("[name='caseType']").val() == 2) {
                                cmd += "&" + _formInterface.serialize();
                                if (_formInterface.find("[name='address']").val()) {

                                } else {
                                    XMS.msgbox.show('发布地址未填写！', 'error', 2000)
                                    return false;
                                }
                                if (_formInterface.find("[name='validParam']").val()) {

                                } else {
                                    XMS.msgbox.show('比对参数未填写！', 'error', 2000)
                                    return false;
                                }
                            }

                            var factors = [];
                            $(Dom.factorList).find("tr").each(function() {
                                var tdArr = $(this).children();
                                // cmd = cmd+"&factorId="+tdArr.eq(0).find("input").val();
                                // cmd = cmd+"&factorName="+tdArr.eq(1).find("input").val();
                                // cmd = cmd+"&remark="+tdArr.eq(2).find("input").val();
                                id = tdArr.eq(0).find("input").val();
                                name = tdArr.eq(2).find("input").val();
                                remark = tdArr.eq(3).find("input").val();
                                order = $(this).find("[name='factorOrder']").val();
                                factors.push({
                                    "factorId": id,
                                    "factorName": name,
                                    "factorOrder": order,
                                    "remark": remark
                                });
                            });
                            cmd += "&factors=" + JSON.stringify(factors);
                            console.log(cmd);
                            Rose.ajax.postJson(srvMap.get('updateCaseTemp'), cmd, function(json, status) {
                                if (status) {
                                    XMS.msgbox.show('修改模板成功！', 'success', 2000)
                                        // 关闭弹出层
                                    $(Dom.modalCaseTempForm).modal('hide')
                                    setTimeout(function() {
                                        self.getCaseTempList();
                                    }, 1000)
                                }
                            });
                        });
                        // });
                    });
                }


            });
            $(Dom.modalCaseTempForm).find("button[name='cancel']").unbind('click');
            $(Dom.modalCaseTempForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalCaseTempForm).modal('hide');
            });
        },
        //生成自动化模板
        newAutoCaseTemp: function() {
            var self = this;
            var caseId;
            $(Dom.createAutoTestTemp).bind('click', function() {

                $("#JS_compTree").slimScroll({
                    "height": '320px'
                });
                $("#JS_compBody").slimScroll({
                    "height": '320px'
                });
                //获取当前选中模板
                var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
                if (_data) {
                    caseId = _data.caseId;
                    $('#tempName1').val(_data.caseName + '_');
                    $(Dom.modalAutoTempForm).modal('show');
                    self.getCompTree();
                    $('#messageAddComp').show();
                    $('#compThead').hide();
                    $('#compBody').empty();

                    //删除组件comp
                    self.deleComp();
                    self.getInfoForAuto("caseId=" + caseId);
                    //保存自动化模板
                    $(Dom.modalAutoTempForm).find("button[name='save']").unbind();
                    $(Dom.modalAutoTempForm).find("button[name='save']").bind('click', function() {
                        var name = $('#tempName1').val() + $('#tempName2').val();

                        var cmd = {
                            'caseId': caseId,
                            'tempName': name,
                        };
                        var compRequestList = [];
                        $("#compBody").find("tr").each(function() {
                            var tdArr = $(this).children();
                            var compId = tdArr.eq(0).find("input").val();
                            var compOrder = tdArr.eq(3).find("input").val();
                            compRequestList.push({
                                'compId': compId,
                                'compOrder': compOrder
                            });
                        });
                        cmd["compRequestList"] = compRequestList;
                        console.log(cmd);
                        Rose.ajax.postJson(srvMap.get('addAutoTestTemp'), cmd, function(json, status) {
                            if (status) {
                                // 添加用户成功后，刷新用户列表页
                                XMS.msgbox.show('自动化模板生成成功！', 'success', 2000)
                                    // 关闭弹出层
                                $(Dom.modalAutoTempForm).modal('hide')
                            }
                        });
                    });
                };

            });


            $(Dom.modalAutoTempForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalAutoTempForm).modal('hide');
            });
        },


        //生成测试用例
        newTestCase: function() {
            var self = this;
            var cmd;
            $(Dom.createTest).bind('click', function() {
                var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
                if (_data) {
                    cmd = 'caseId=' + _data.caseId;
                    $(Dom.modalTestCaseForm).modal('show');
                    $("#JS_bodyFactor").slimScroll({
                        "height": '300px'
                    });
                    $('#testName1').val(_data.caseName + '_');
                    Rose.ajax.postJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
                        if (status) {
                            var factor_template = Handlebars.compile(Tpl.getTestFactorList);
                            $(Dom.testFactorList).html(factor_template(json.data.factors));
                            Utils.eventClickChecked($(Dom.testFactorList), function() {

                            })
                        }
                    });


                };
            });
            //保存测试用例
            $(Dom.testForm).find("button[name='save']").bind('click', function() {
                cmd = cmd + "&testName=" + $('#testName1').val() + $('#testName2').val();

                var factors = [];

                $(Dom.testFactorList).find("tr").each(function() {
                    var tdArr = $(this).children();
                    if (tdArr.eq(0).find("input").is(':checked')) {
                        factors.push({
                            "factorId": tdArr.eq(0).find("input").val(),
                            "factorName": tdArr.eq(1).find("input").val(),
                            //"remark":tdArr.eq(2).find("input").val(),
                            "factorValue": tdArr.eq(3).find("input").val(),
                            "factorOrder": tdArr.eq(4).find("input").val()
                        });
                    }
                });

                cmd += "&factors=" + JSON.stringify(factors);

                console.log(cmd);
                Rose.ajax.postJson(srvMap.get('addTestCase'), cmd, function(json, status) {
                    if (status) {
                        // 添加用户成功后，刷新用户列表页
                        XMS.msgbox.show('测试用例生成成功！', 'success', 2000)
                            // 关闭弹出层
                        $(Dom.modalTestCaseForm).modal('hide')

                    }
                });
            });
            $(Dom.testForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalTestCaseForm).modal('hide');
            });
        },



        // 删除模板
        deleCaseTemp: function() {
            var self = this;
            $(Dom.deleCaseTemp).unbind('click');
            $(Dom.deleCaseTemp).bind('click', function() {
                var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
                if (_data) {
                    var _caseId = "caseId=" + _data.caseId;
                    console.log(_caseId);
                    Rose.ajax.getJson(srvMap.get('delCaseTemp'), _caseId, function(json, status) {
                        if (status) {
                            // dele成功后，重新加载模板列表
                            window.XMS.msgbox.show('模板删除成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getCaseTempList();
                            }, 1000)
                        }
                    });

                }
            });
        },

        //获取模板信息
        getCaseTempInfo: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
                if (status) {
                    var factor_template = Handlebars.compile(Tpl.getFactorList);
                    var caseTemp_template = Handlebars.compile(Tpl.getCaseTempForm);
                    $("#JS_messageAddFactor").hide();
                    $('#factorThead').show();
                    $(Dom.caseTempForm).html(caseTemp_template(json.data));
                    //加载下拉框
                    //self.getSysList("#add_sysId");
                    self.getSysList(dropChoice2, json.data);
                    $(Dom.caseTempForm).find("[name='caseType']").val(json.data.caseType);
                    if (json.data.caseType == 2) {
                        $(Dom.caseTempForm).find("[name='interfaceType']").val(json.data.caseInterface.interfaceType);
                        self.interfaceSelected();
                        _interfaceForm.find("[name='address']").val(json.data.caseInterface.address);
                        _interfaceForm.find("[name='validParam']").html(json.data.caseInterface.validParam);
                        _interfaceForm.find("[name='messageId']").val(json.data.caseInterface.messageId);
                        _interfaceForm.find("[name='messageName']").val(json.data.caseInterface.messageName);

                        if (json.data.caseInterface.messageType) {
                            _interfaceForm.find("[name='messageType']").val(json.data.caseInterface.messageType);
                        }
                    }
                    $(Dom.caseTempForm).find("[name='important']").val(json.data.important);
                    $(Dom.caseTempForm).find("[name='operateDesc']").val(json.data.operateDesc);

                    $(Dom.factorList).html(factor_template(json.data.factors));
                    Utils.eventClickChecked($(Dom.factorList), function() {});
                    self.interfaceSelected();

                }
            });
        },

        getInfoForAuto: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
                if (status) {
                    caseType = json.data.caseType;
                    var template = Handlebars.compile(Tpl.getCaseTempInfo);
                    $("#JS_getCaseTempInfo").html(template(json.data));
                }
            });
        },

        //新增因子
        addFactor: function(cmd) {
            var self = this;
            $("#JS_addFactor").unbind('click');
            $("#JS_addFactor").bind('click', function() {

                var factor_template = Handlebars.compile(Tpl.getFactorList);
                var empty = {
                    'data': ''
                };
                $("#JS_messageAddFactor").hide();
                $('#factorThead').show();
                $('#JS_factorList').show();
                $(Dom.factorList).append(factor_template(empty));
                Utils.eventClickChecked($(Dom.factorList), function() {})
                    // $("#compTable").slimScroll({
                    //  "height": '300px',
                    //  alwaysVisible: true,
                    // });              
            });
        },
        //删除因子
        deleFactor: function(cmd) {
            var self = this;
            var _modal = $(Dom.modalCaseTempForm);
            $('#JS_delFactor').unbind('click');
            $('#JS_delFactor').bind('click', function() {
                var factor = self.getCheckedRow(Dom.factorList);
                if (factor.find("input[name='factorName']").length == 0) {
                    window.XMS.msgbox.show('请先选择一个因子！', 'error', 2000);
                    return;
                } else {
                    factor.remove();
                }
                if ($(Dom.factorList + " tr").length == 0) {
                    $("#JS_messageAddFactor").show();
                    modal.find("table").hide();
                };
            });

        },
        //删除组件
        deleComp: function() {

            var self = this;
            $('#deleComp').unbind('click');
            $('#deleComp').bind('click', function() {
                var comp = self.getCheckedRow("#compBody");
                if (comp.find("input[name='compId']").length == 0) {
                    window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
                    return;
                } else {
                    comp.remove();
                }
                if ($("#compBody" + " tr").length == 0) {
                    $("#messageAddComp").show();
                    $('#compThead').hide();
                };
            });

        },

        //获取组件信息
        getCompinfo: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getCompinfo'), cmd, function(json, status) {
                if (status) {
                    $('#messageAddComp').hide();
                    $('#compThead').show();
                    var _dom = $("#compTable");
                    var dataArray = Utils.getTableDataRows(_dom);
                    json.data["compOrder"] = dataArray.length + 1;
                    var template = Handlebars.compile(Tpl.compList);
                    $("#compBody").append(template(json.data));

                    Utils.eventClickChecked($("#compBody"), function() {

                    })
                }
            });
        },
        //组件树
        getCompTree: function() {
            var self = this;
            var setting = {
                check: {
                    enable: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pid"
                    }
                },

                callback: {
                    beforeClick: function(treeId, treeNode, clickFlag) {

                        return (!treeNode.isParent);
                    },
                    onClick: function(event, treeId, treeNode) {
                        console.log(treeNode);
                        var cmd = "compId=" + treeNode.id;
                        self.getCompinfo(cmd);
                        //self.addComp(cmd1);
                    }
                }
            };

            Rose.ajax.getJson(srvMap.get('getCompTree'), '', function(json, status) {
                if (status) {
                    $.fn.zTree.init($(Dom.compTree), setting, json.data);

                }
            });
        },
        //重置tab
        resetTab: function(obj) {
            $(obj).find(".nav-tabs").find(".active").removeClass('active');
            $(obj).find(".nav-tabs").find("li:first").addClass('active');
            $(obj).find(".tab-content").find(".active").removeClass('active');
            $(obj).find(".tab-content").children('#tab_info').addClass('active');
        },


        // 获取列表当前选中行
        getCaseTempCheckedRow: function(obj) {
            var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
            var _caseId = _obj.find("input[name='caseId']")

            var _name = _obj.find("input[name='caseName']")
            var data = {
                caseId: "",
                caseName: "",
            }
            if (_caseId.length == 0) {
                window.XMS.msgbox.show('请先选择一个模板！', 'error', 2000);
                return;
            } else {
                data.caseId = _caseId.val();
                data.caseName = _name.val();
            }
            return data;
        },

        // 获取列表当前选中行
        getCheckedRow: function(obj, required) {
            if (required) {
                var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
                var _data = {
                    Id: "",
                    Name: "",

                }
                _data.Id = _obj.find("input").eq(0).val();
                _data.Name = _obj.find("input").eq(1).val();
                return _data;
            } else {
                var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
                return _obj;
            }

        },

        //获取选中组件
        getCompCheckedRow: function() {
            var _obj = $("#compBody").find("input[type='radio']:checked").parents("tr");
            var _Id = _obj.find("input[name='compId']")

            var _name = _obj.find("input[name='compName']")
            var data = {
                obj: "",
                compId: "",
                compName: "",
            }
            if (_Id.length == 0) {
                window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
                return;
            } else {
                data.compId = _Id.val();
                data.compName = _name.val();
                data.obj = _obj;
            }
            return data;
        },


        eventClickChecked: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            obj.find("tr").bind('click', function(event) {
                $(this).find('.minimal').iCheck('check');
                if (callback) {
                    callback();
                }
            });
        },
        // 事件：双击选中当前行
        eventDClickCallback: function(obj, callback) {
            obj.find("tr").bind('dblclick ', function(event) {
                if (callback) {
                    callback();
                }
            });
        },
        resetForm: function(objForm, callback) {
            $(objForm).find(':input')
                .not(':button, :submit, :reset')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');

        },

        // checkForm: function(objForm, callback) {
        //     var state = true;
        //     var text = '';
        //     $(objForm).find(':input[required]')
        //         .not(':button, :submit, :reset, :hidden').each(function() {
        //             var _val = $.trim($(this).val());
        //             var _text = $.trim($(this).parent().prev().text());
        //             if (_val == null || _val == undefined || _val == '') {
        //                 state = false;
        //                 text = _text.replace(/\：/, '');
        //                 return false;
        //             }
        //         })
        //     if (state) {
        //         callback(state);
        //     } else {
        //         XMS.msgbox.show(text.trimStar() + '不能为空！', 'error', 2000);
        //     }
        // },
    };
    module.exports = Init;
});