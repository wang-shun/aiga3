define(function(require, exports, module) {
    // 路径重命名
    var pathAlias = "autoManage/autoCaseMng/";
    var pathAlias2 = "autoManage/autoCaseTempMng/";
    var Utils = require("global/utils.js");
    var planTag; //计划编号
    var autoId;

    //问题分类下拉框
    srvMap.add("getRootList", "", "sys/cache/listRootid");
    //一级分类下拉框
    srvMap.add("getFirstList", "", "sys/cache/listFirstid");
    //二级分类下拉框
    srvMap.add("getSecondList", "", "sys/cache/listSecondid");
    //三级分类下拉框
    srvMap.add("getThirdList", "", "sys/cache/listThirdid");
    //系统大类下拉框显示
    srvMap.add("getSysList", "autoManage/autoCaseTempMng/getSysList.json", "sys/cache/listSysid");
    //系统子类下拉框
    srvMap.add("getSubsysList", "autoManage/autoCaseTempMng/getSubsysList.json", "sys/cache/listSubsysid");
    //功能点下拉框
    srvMap.add("getFunList", "autoManage/autoCaseTempMng/getFunList.json", "sys/cache/listFun");
    //业务
    srvMap.add("getBusiList", "caseTempMng/getBusiList.json", "sys/cache/busi");
    // 用例列表显示
    srvMap.add("getAutoCaseList", pathAlias + "autoCaseList.json", "auto/case/listInfo");

    // 根据用例ID获取组件列表
    srvMap.add("getAutoCompList", pathAlias2 + "getTempCompList.json", "auto/comp/findByAutoId");
    // 保存与组件关系(批量)
    srvMap.add("updateCaseCompList", pathAlias2 + "getTempCompList.json", "auto/case/saveAutoCompParam");
    //参数列表
    srvMap.add("parameterList", pathAlias2 + "getParameterList.json", "auto/param/findByAutoComp");
    //删除用例
    srvMap.add("deleAutoCase", pathAlias2 + "getParameterList.json", "auto/case/delete");
	//问题展示
	srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
	//刪除問題
	srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "/archi/question/delete");
	
    // 模板对象
    var Tpl = {
        getAutoCaseList: $("#TPL_autoCaseList").html(), //计划列表
        getSideAutoCompList: $("#TPL_sideTempCompList").html(),
        getParameterList: $("#TPL_parameterList").html(),
		getQuestionInfoList: require('tpl/archiQuesManage/quesRequest.tpl')

		
    };

    // 容器对象
    var Dom = {
        getAutoCaseList: '#JS_getAutoCaseList', //table对象
        queryAutoCaseList: '#JS_queryAutoCaseForm', //查询表单
        submitAutoCaseList: '#JS_submitAutoCaseForm', //查询表单
        modalEditAutoCase: '#JS_editAutoCaseModal', //modal
        getParameterList: '#JS_parameterList', //参数列表
        getSideAutoCompList: '#JS_sideAutoCompList', //侧边组件栏
		

        //编辑用例按钮
        btnEditAuoCase: '#Js_btnEdit',


    };
    var busiData;

    var Init = {
        init: function() {
            this._render();
        },
        _render: function() {
            $("#Js_contentWrapper").find('h1').html("自动化用例管理");
            $("#Js_contentWrapper").find('li.active').html("自动化用例管理");
            this.getBusiList();
//            this.getAutoCaseList();
            this.queryAutoCase();
            this.updateCaseInfo();
            this.deleAutoCase();
			this.getQuestionInfoList();
            Utils.setSelectData($(Dom.queryAutoCaseList));
        },

        getAutoCaseList: function(cmd) {
            var self = this;
            var _dom = $(Dom.getAutoCaseList);
            var pagination = _dom.find(".dataTables_paginate");

            Utils.getServerPage(srvMap.get('getAutoCaseList'), cmd, function(json) {
                var template = Handlebars.compile(Tpl.getAutoCaseList);
                console.log(json.data)
                
                _dom.find("tbody").html(template(json.data.content));
                Utils.eventClickChecked($(Dom.getAutoCaseList));

            }, pagination);
        },
        queryAutoCase: function() {
            var self = this;
            var _form = $(Dom.queryAutoCaseList);
            // 表单提交
            _form.find('button[name="query"]').bind('click', function() {

                    var cmd = _form.serialize();
                    self.getQuestionInfoList(cmd);///
                })
                // 表单重置
            _form.find('button[name="reset"]').bind('click', function() {
                alert();
            });
        },

        getBusiList: function(obj, data) {
            Rose.ajax.postJson(srvMap.get('getBusiList'), '', function(json, status) {
                if (status) {
                    busiData = json.data;
                }
            });
        },

        // 修改用例信息
        updateCaseInfo: function(cmd) {
            var self = this;
            var btn = $(Dom.btnEditAuoCase);
            btn.unbind('click');
            btn.bind('click', function() {
                var data = self.getAutoInfo();
                if (data) {
                    autoId = data.autoId;
                    var ids = 'autoId=' + data.autoId;
                    var _modal = $(Dom.modalEditAutoCase);
                    _modal.modal('show');
                    _modal.find("[name='autoName']").val(data.autoName);
                    _modal.find("[name='environmentType']").val(data.environmentType);

                    // 获取组件列表
                    self.getSideTempCompList(ids);
                    //保存组件
                    self.saveAutoCompParam(data.autoId);
                    // 先清空
                    $(Dom.getParameterList).find("tbody").remove();
                }
            });
        },
        // 获取侧边组件列表
        getSideTempCompList: function(cmd) {
            // alert('获取侧边组件列表'+cmd);
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.getJson(srvMap.get('getAutoCompList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var _dom = $(Dom.getSideAutoCompList);
                    var template = Handlebars.compile(Tpl.getSideAutoCompList);
                    _dom.html(template(json.data));


                    $.each(json.data, function(n, value) {
                        value.paramList["compId"] = value.compId;
                        value.paramList["compOrder"] = value.compOrder;
                        value.paramList["compName"] = value.compName;
                        console.log(value.paramList);
                        var template_table = Handlebars.compile(Tpl.getParameterList);
                        var _table = $(Dom.getParameterList);
                        _table.append(template_table(value.paramList))
                    });

                    Utils.eventClickChecked(_dom, function(isChecked, thisDom) {
                        var _name = thisDom.attr("name");
                        var dd = thisDom.closest("tr");
                        var _compOrder = dd.find("input[name='compOrder']").val();
                        var _compName = dd.find("input[name='compName']").val();
                        var _val = thisDom.val();
                        if (isChecked == "true") {
                            var cmd = "autoId=" + autoId + "&" + _name + '=' + _val + "&compOrder=" + _compOrder;
                            // 获取参数列表
                            self.getParameterList(cmd, _val, _compOrder, _compName);
                        } else {
                            var _table = $(Dom.getParameterList);
                            _table.find("tbody[name=" + _val + "_" + _compOrder + "]").remove();
                        }

                    })
                    console.log(_dom.find("[name='compId']"));
                    _dom.find("[name='compId']").iCheck('check');
                }
            });
        },
        // 获取参数列表
        getParameterList: function(cmd, compId, compOrder, compName) {
            // alert('参数列表'+cmd);
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.getJson(srvMap.get('parameterList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    json.data["compId"] = compId;
                    json.data["compOrder"] = compOrder;
                    json.data["compName"] = compName;
                    var template = Handlebars.compile(Tpl.getParameterList);
                    var _table = $(Dom.getParameterList);
                    _table.append(template(json.data))
                }
            });
        },
        // 保存用例
        saveAutoCompParam: function(autoId) {
            var self = this;
            var _dom = $(Dom.modalEditAutoCase);
            var _table = $(Dom.getParameterList);
            var _save = _dom.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var _autoName = _dom.find("[name='autoName']").val();
                var _environmentType = _dom.find("[name='environmentType']").val();
                if (_autoName == '') {
                    XMS.msgbox.show('用例模板名称不能为空！', 'error', 2000);
                    return;
                }
                if (_environmentType == '') {
                    XMS.msgbox.show('请选择环境类型！', 'error', 2000);
                    return;
                }
                var hasData = Utils.getCheckboxCheckedRow($(Dom.getSideAutoCompList));
                if (hasData) {
                    var cmd = {
                        "autoId": autoId,
                        "autoName": _autoName,
                        "environmentType": _environmentType,
                        "compList": []
                    };
                    // 抓取参数列表
                    _table.find("tbody").each(function() {
                        var data = {};
                        data["compId"] = $(this).find("[name='compId']").val();
                        data["compOrder"] = $(this).find("[name='compOrder']").val();
                        data["paramList"] = []
                        $(this).find("tr").each(function() {
                            var paramData = {}
                            if ($(this).find("input").length=0){

                            } else {
                                $(this).find("input").each(function() {
                                    var key = $(this).attr("name");
                                    var value = $(this).val();
                                    paramData[key] = value;
                                });
                                data["paramList"].push(paramData);
                            }

                        })
                        cmd.compList.push(data);
                    });
                    console.log("参数测试")
                    console.log(cmd)
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('updateCaseCompList'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存成功！', 'success', 2000)
                            _dom.modal('hide');
                            setTimeout(function() {
                                self.getAutoCaseList();
                            }, 1000)
                        }
                    });
                }
            });
        },

        //删除
        deleAutoCase: function() {
            var self = this;
            $("#JS_deleAutoCase").bind('click', function() {
                var data = self.getAutoInfo();
                if (data) {
                    var cmd = 'quesId=' + data.quesId;
                    alert(cmd);
                    Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('用例删除成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getAutoCaseList();
                            }, 1000)
                        }
                    });
                }
            });

        },
/*        deleAutoCase: function() {
            var self = this;
            $("#JS_deleAutoCase").bind('click', function() {
                var data = self.getAutoInfo();
                if (data) {
                    var cmd = 'autoId=' + data.autoId;
                    alert(cmd);
                    Rose.ajax.getJson(srvMap.get('deleAutoCase'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('用例删除成功！', 'success', 2000);
                            setTimeout(function() {
                                self.getAutoCaseList();
                            }, 1000)
                        }
                    });
                }



            });

        },*/

        //-----------问题展示---------
        getQuestionInfoList: function(cmd) {
        
        	var self = this;
            var _dom = $(Dom.getAutoCaseList);
            var pagination = _dom.find(".dataTables_paginate");
Rose.ajax.getJson(srvMap.get('getQuestionInfoList'), cmd, function(json, status) {
//            Utils.getServerPage(srvMap.get('getQuestionInfoList'), cmd, function(json) {
                var template = Handlebars.compile(Tpl.getQuestionInfoList);
                console.log(json.data)
                
                _dom.find("tbody").html(template(json.data));
                Utils.eventClickChecked($(Dom.getAutoCaseList));
});
//            }, pagination);
        },

        //获取选中当前用例数据
        getAutoInfo: function() {
            var obj = this.getCheckedRow(Dom.getAutoCaseList);
            var data = {
                autoId: "",
                autoName: "",
                environmentType: "",
            };
            if (obj.length == 0) {
                window.XMS.msgbox.show('请先选择一个用例！', 'error', 2000);
                return;
            } else {
                data.autoId = obj.find("[name='autoId']").val();

                data.autoName = obj.find("[name='autoName']").val();
                data.environmentType = obj.find("[name='environmentType']").val();
            }

            return data;
        },
        // 获取复选列表当前选中行
        getCheckedRow: function(obj) {
            var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
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

    };
    module.exports = Init;
});
