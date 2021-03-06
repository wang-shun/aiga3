define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('componentManage');

    //路径重命名
    var pathAlias = "componentManage/";
    //获取所有功能菜单  
    srvMap.add("getFuncList", pathAlias + "getFunList.json", "sys/component/compTree");
    //根据当前功能点ID调取组件
    srvMap.add("getCompList", pathAlias + "getCompList.json", "sys/component/listByParam");
    // 查询组件信息
    srvMap.add("getCompinfo", pathAlias + "getCompinfo.json", "sys/component/findone");
    // 添加组件
    srvMap.add("addComp", pathAlias + "addComp.json", "sys/component/save");
    //修改组件
    srvMap.add("updateComp", pathAlias + "retMessage.json", "sys/component/update");
    //删除选中组件
    srvMap.add("delComp", pathAlias + "retMessage.json", "sys/component/delete");
    // 组件列表按条件查询
    srvMap.add("queryCompInfo", pathAlias + "queryCompInfo.json", "sys/component/listByParam");
    //请求控件树
    srvMap.add("getCompCtrTree", pathAlias + "getCompCtrTree.json", "sys/component/ctrlTree");
    //请求参数列表
    srvMap.add("getParameterList", pathAlias + "getParameterList.json", "sys/component/compParamList");
    //根据ID获取参数信息
    srvMap.add("getParamInfo", pathAlias + "getParamInfo.json", "sys/component/paramFindOne");
    //添加参数
    srvMap.add("addParamInfo", pathAlias + "retMessage.json", "sys/component/compParamSave");
    //修改参数
    srvMap.add("updateParamInfo", pathAlias + "retMessage.json", "sys/component/compParamUpdate");
    //删除参数
    srvMap.add("delParamInfo", pathAlias + "retMessage.json", "sys/component/compParamDel");
    var Tpl = {
        getQueryInfo: $("#TPL_getQueryInfo").html(),
        getCompInfoForm: $("#TPL_getCompInfoForm").html(),
        getParameterList: $("#TPL_getParameterList").html(),
        addParameterForm: $("#TPL_addParameterForm").html()
    };
    var Mod = {
        getQueryInfo: '#JS_getQueryInfo',
        getQueryInfoWrap: '#JS_getQueryInfoWrap'

    };
    var Dom = {
        getCompInfoForm: "#JS_getCompInfoForm",
        addComp: "#JS_addComp",
        delComp: "#JS_delComp",
        getQueryInfo: "#JS_getQueryInfo",
        addCompModal: "#JS_addCompModal",
        addCompCtrTree: "#JS_addCompCtrTree",
        addCompInfoForm: "#JS_addCompInfoForm",
        addCompSubmit: "#JS_addCompSubmit",
        updateComp: "#JS_updateComp",
        addParameterForm: "#JS_addParameterForm",
        getParameterList: "#JS_getParameterList",
        getParameterWrap: "#JS_getParameterWrap",
        compTreeScorll: "#JS_compTreeScorll",
        addCompCtrScroll: "#JS_addCompCtrScroll"
    }
    var Data = {
        funId: null,
        ctrlId: null,
        ctrlIds: '',
        compId: null,
        addCompId: null,
        isAdd: false,
        setPageType: function(type) {
            return {
                "data": {
                    "type": type
                }
            }
        }
    }
    var indexInfoQuery = {
        init: function() {
            this._render();
        },
        _render: function() {
            this.getLeftTree();
            this.queryCompInfo();
            //展示表单
        },
        //获取左侧组件树
        getLeftTree: function() {
            var self = this
            Rose.ajax.postJson(srvMap.get('getFuncList'), '', function(json, status) {
                if (status) {
                    console.log(json.data)
                    var setting = {
                        data: {
                            key: {
                                name: "name"
                            },
                            simpleData: {
                                enable: true,
                            }
                        },
                        callback: {
                            beforeClick: function(treeId, treeNode, clickFlag) {
                                return (treeNode.ifLeaf !== "N");
                            },
                            onClick: function(event, treeId, treeNode) {
                                var _funId = treeNode.id;
                                console.log(_funId);
                                //存储在全局变量中
                                Data.funId = _funId;
                                var cmd = "parentId=" + Data.funId;
                                self.getCompByFunId(cmd);
                            }
                        }
                    };
                    $.fn.zTree.init($("#Tree_getRightTree"), setting, json.data);
                }
                //滚动条
                Page.findId('compTreeScorll').slimScroll({
                    "height": '420px'
                });
            });

        },
        //通过功能点获取右侧组件表格数据
        getCompByFunId: function(cmd) {
            var self = this;
            var _dom = Page.findId('getQueryInfoWrap');
            var _domPagination = _dom.find("[name='pagination']");
            Utils.getServerPage(srvMap.get('getCompList'), cmd, function(json) {
                Page.findId('getQueryInfoWrap').removeClass("hide");
                var template = Handlebars.compile(Page.findTpl('getQueryInfo'));
                _dom.find("[name='content']").html(template(json.data.content));
                // Utils.eventTrClickCallback(_dom);
                // self.eventClickChecked($(Mod.getQueryInfo));
                Utils.eventTrClickCallback(_dom, function() {
                    var _data = self.getCheckedComp();
                    Data.compId = _data.compId;
                    var cmd = "compId=" + _data.compId;
                    self.getCompinfo(cmd);
                })

                self.addComp();
                self.delComp();
                self.updateComp();
            },_domPagination);
        },
        // 添加组件
        addComp: function() {
            var self = this;
            Page.findId('addComp').unbind('click');
            Page.findId('addComp').bind('click', function() {
                // 如果组织结构未选中或未显示都不行
                if (Data.funId == null) {
                    XMS.msgbox.show('请先选择一个功能点！', 'info', 2000);
                    return;
                }
                //输入模板
                Page.findModal('addCompModal').find(".modal-body").html(Handlebars.compile(Page.findId('addCompTemp').html()));
                // 设置添加标识
                Data.isAdd = true;
                // 弹出层
                Page.findModal('addCompModal').modal('show');

                // 控件树
                self.getCompCtrTree();

                //滚动条
                Page.findId('addCompCtrScroll').slimScroll({
                    "height": '420px'
                });
                //组件表单校验初始化
                var _form = Page.findId('addCompInfoForm');
                var template = Handlebars.compile(Page.findTpl('getCompInfoForm'));
                _form.html(template({}));
                //创建时间锁定
                _form.find("[name='createTime']").val("2017-03-03 15:29:30").attr("readonly", true);
                //最后修改人锁定
                _form.find("[name='updateId']").val("111").attr("readonly", true);
                //组件创建人锁定
                _form.find("[name='creatorId']").val("111").attr("readonly", true);

                self.addParamInfo();
                self.delParamInfo();

                // 表单提交
                self.addCompSubmit();
            })
        },
        getCompCtrTree: function() {
            var self = this;
            //加载控件结构树
            Rose.ajax.postJson(srvMap.get('getCompCtrTree'), '', function(json, status) {
                if (status) {
                    $.fn.zTree.init(Page.findId('addCompCtrTree'), {
                        data: {
                            key: {
                                name: "name"
                            },
                            simpleData: {
                                enable: true,
                            }
                        },
                        callback: {
                            beforeClick: function(treeId, treeNode, clickFlag) {
                                return (treeNode.ifLeaf !== "N");
                            },
                            onClick: function(event, treeId, treeNode) {
                                var _ctrlId = treeNode.id;
                                Data.ctrlId = _ctrlId;
                                Data.ctrlIds += _ctrlId + ',';
                                console.log(Data.ctrlIds);
                                var _compCtrId = treeNode.id;
                                var _script = self.getScript(json.data, _compCtrId);
                                Page.findId('addCompInfoForm').find("textarea[name='compScript']").append(_script + "\r\n");
                            }
                        }
                    }, json.data);
                }
            });
        },
        // 提交按钮
        addCompSubmit: function() {
            var self = this;
            Page.findId('addCompSubmit').unbind('click');
            Page.findId('addCompSubmit').bind('click', function() {
                var _form = Page.findId('addCompInfoForm');
                // 表单校验：成功后调取接口
                var cmd = _form.serialize();
                var addCmd = "&ctrlIds=" + Data.ctrlIds + "&parentId=" + Data.funId;
                cmd = cmd + addCmd;
                console.log(cmd);
                XMS.msgbox.show('数据加载中，请稍候...', 'loading')
                Rose.ajax.postJson(srvMap.get('addComp'), cmd, function(json, status) {
                    if (status) {
                        Data.addCompId = json.data.compId;
                        // 添加用户成功后，刷新用户列表页
                        XMS.msgbox.show('添加组件成功！', 'success', 2000)
                        Data.ctrlIds = '';
                        // // 关闭弹出层
                        // $(Dom.addCompModal).modal('hide')
                        setTimeout(function() {
                            var cmd = "parentId=" + Data.funId;
                            self.getCompByFunId(cmd);
                        }, 1000)
                    }
                });
            })
        },
        //获取参数列表(根据组件ID)
        getParameterListById: function(cmd) {
            var self = this;
            var _dom = Page.findId('getParameterList');
            Rose.ajax.postJson(srvMap.get('getParameterList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Page.findTpl('getParameterList'));
                    _dom.html(template(json.data));

                    // 添加、删除
                    self.addParamInfo();
                    self.delParamInfo();

                    // // 点击选中行
                    // self.eventClickChecked($(Dom.getParameterList));

                    // 绑定双击当前行事件
                    Utils.eventTrClickCallback(_dom, function() {
                        self.getParamInfoByID();
                    })

                    //设置分页
                    self.initPaging(_dom, 8)

                }
            });
        },
        // 获取参数信息
        getParamInfo: function() {
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getParamInfo'), '', function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    Page.findId('addParameterForm').removeClass('hide');
                    json.data["type"] = "新增参数";
                    var template = Handlebars.compile(Page.findTpl('addParameterForm'));
                    Page.findId('addParameterForm').html(template(json.data))
                        // 提交保存
                    self.updateParamInfo('update');
                }
            });

        },
        // 获取参数信息(根据参数ID)
        getParamInfoByID: function() {
            var self = this;
            var _data = self.getCheckedParamRow();
            var cmd = 'paramId=' + _data.paramId;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getParamInfo'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    Page.findId('addParameterForm').removeClass('hide');
                    json.data["type"] = "修改参数";
                    var template = Handlebars.compile(Page.findTpl('addParameterForm'));
                    Page.findId('addParameterForm').html(template(json.data))
                        // 提交保存
                    self.updateParamInfo('update');
                }
            });

        },
        // 添加参数
        addParamInfo: function() {
            var self = this;
            var _domAdd = Page.findId('getParameterWrap').find("[name='add']");
            _domAdd.unbind('click');
            _domAdd.bind('click', function() {
                Page.findId('addParameterForm').removeClass('hide');
                var json = Data.setPageType("添加参数")
                var template = Handlebars.compile(Page.findTpl('addParameterForm'));
                Page.findId('addParameterForm').html(template(json.data))
                    // 添加时移除roleId
                Page.findId('addParameterForm').find("[name='ParamId']").remove();
                // 提交保存
                self.updateParamInfo('save');
            });

        },
        //保存参数
        updateParamInfo: function(type) {
            var self = this;
            var _srvMap = type == "save" ? 'addParamInfo' : 'updateParamInfo';
            var _domSave = Page.findId('addParameterForm').find("[name='save']");
            _domSave.unbind('click');
            _domSave.bind('click', function() {
                var cmd = $(this).parents("form").serialize();
                var _compId = Data.isAdd == true ? Data.addCompId : Data.compId;
                cmd = cmd + "&compId=" + _compId;
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');

                Rose.ajax.postJson(srvMap.get(_srvMap), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('保存成功！', 'success', 2000)
                        setTimeout(function() {
                            self.getParameterListById("compId=" + _compId);
                        }, 1000)
                    }
                });
            });
        },
        // 删除参数
        delParamInfo: function() {
            var self = this;
            var _domDel = Page.findId('getParameterWrap').find("[name='del']");
            _domDel.bind('click', function() {
                var _data = self.getCheckedParamRow();
                if (_data) {
                    var cmd = 'paramId=' + _data.paramId;
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delParamInfo'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000)
                            setTimeout(function() {
                                var _compId = Data.isAdd == true ? Data.addCompId : Data.compId;
                                cmd = cmd + "&compId=" + _compId;
                                self.getParameterListById(cmd);
                                Page.findId('addParameterForm').addClass('hide');
                            }, 1000)
                        }
                    });
                }
            });
        },
        //获取组件信息(根据组件ID)
        getCompinfo: function(cmd) {
            var self = this;
            //设置修改标志
            Data.isAdd = false;
            //输入模板
            Page.findModal('addCompModal').find(".modal-body").html(Handlebars.compile(Page.findId('addCompTemp').html()));
            // 加载控件树
            self.getCompCtrTree();
            //滚动条
            Page.findId('addCompCtrScroll').slimScroll({
                "height": '420px'
            });
            Rose.ajax.postJson(srvMap.get('getCompinfo'), cmd, function(json, status) {
                if (status) {
                    // 表单校验初始化
                    var _form = Page.findId('addCompInfoForm');
                    var template = Handlebars.compile(Page.findTpl('getCompInfoForm'));
                    console.log(json.data);
                    _form.html(template(json.data));
                    $("#compScript").html(json.data.compScript)
                        //组件创建人锁定
                    _form.find("[name='creatorId']").val("111").attr("readonly", true);
                    self.getParameterListById(cmd);
                    // 弹出层
                    Page.findModal('addCompModal').modal('show');

                    // 提交
                    Page.findId('addCompSubmit').unbind('click');
                    Page.findId('addCompSubmit').bind('click', function() {

                        var _cmd1 = "&ctrlIds=" + Data.ctrlIds;
                        var _cmd = "&compId=" + Data.compId;
                        var cmd = _form.serialize() + _cmd + _cmd1;
                        console.log(cmd);
                        // self.getUserinfoList(cmd);
                        XMS.msgbox.show('数据加载中，请稍候...', 'loading')
                        Rose.ajax.postJson(srvMap.get('updateComp'), cmd, function(json, status) {
                            if (status) {
                                // 添加用户成功后，刷新用户列表页
                                XMS.msgbox.show('修改组件成功！', 'success', 2000)
                                Data.ctrlIds = '';
                                // 关闭弹出层
                                //$(Dom.addCompModal).modal('hide')
                                setTimeout(function() {
                                    self.getCompByFunId();
                                }, 1000)
                            }
                        });
                        //});
                    })
                }
            });
        },
        //修改组件
        updateComp: function() {
            var self = this;
            Page.findId('updateComp').unbind('click');
            Page.findId('updateComp').bind('click', function() {
                var _data = self.getCheckedComp();
                Data.compId = _data.compId;
                var cmd = "compId=" + _data.compId;
                if (cmd) {
                    self.getCompinfo(cmd);
                }
            });
        },
        //通过表单条件查询组件
        queryCompInfo: function() {
            var self = this;
            var _form = Page.findId('getCompInfoForm');
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                var cmd = _form.serialize();
                console.log(cmd);
                self.getCompByQuery(cmd);
            })
        },
        //搜索组件
        getCompByQuery: function(cmd) {
            var self = this;
            var _dom = Page.findId('getQueryInfoWrap');
            var _domPagination = _dom.find("[name='pagination']");
            Utils.getServerPage(srvMap.get('queryCompInfo'), cmd, function(json) {
                    _dom.removeClass("hide");
                    var template = Handlebars.compile(Page.findTpl('getQueryInfo'));
                    _dom.find("[name='content']").html(template(json.data.content));
                    // self.eventClickChecked($(Mod.getQueryInfo));
                    Utils.eventTrClickCallback(_dom, function() {
                        var _data = self.getCheckedComp();
                        Data.compId = _data.compId;
                        var cmd = "compId=" + _data.compId;
                        self.getCompinfo(cmd);
                    })
                    self.addComp();
                    self.delComp();
                    self.updateComp();
            },_domPagination);
        },
        //删除组件
        delComp: function() {
            var self = this;
            Page.findId('delComp').bind('click', function() {
                var _data = self.getCheckedComp();
                if (_data) {
                    var cmd = "compId=" + _data.compId;
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('delComp'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getCompByFunId();
                            }, 1000)
                        }
                    });
                }
            });
        },
        getScript: function(arrayData, _compCtrId) {
            var data = {};
            for (i in arrayData) {
                if (arrayData[i].id == _compCtrId) {
                    data = arrayData[i];
                }
            }
            var strObj = data.script;
            var waitTime = Page.findId('addCompInfoForm').find("input[name='waitTime']").val();
            strObj = strObj.replaceAll('element' + '\\(' + '\\)', 'element(' + data.name + ')');
            strObj = strObj.replaceAll('控件名', data.name);
            strObj = strObj.replaceAll('try' + '\\(' + '\\)', 'try(' + waitTime + ')');
            return Rose.string.js_beautify(strObj);


        },
        //获取选中组件
        getCheckedComp: function() {
            var _obj = Page.findId('getQueryInfo').find("input[type='radio']:checked").parents("tr");
            var _compId = _obj.find("input[name='compId']")
            if (_compId.length == 0) {
                window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
                return;
            } else {
                var data = {
                    compId: ""
                }
                data.compId = _compId.val();
            }
            return data;
        },
        // 获取参数列表当前选中参数ID
        getCheckedParamRow: function() {
            var _obj = Page.findId('getParameterList').find("input[type='radio']:checked").parents("tr");
            var _id = _obj.find("input[name='paramId']")
            var data = {
                paramId: _id.val()
            }
            if (_id.length == 0) {
                window.XMS.msgbox.show('请先选择一个参数！', 'info', 2000);
                return;
            }
            return data;
        },
        // 事件：单机选中当前行
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
        // 事件：双击绑定事件
        eventDClickCallback: function(obj, callback) {
            obj.find("tr").bind('dblclick ', function(event) {
                if (callback) {
                    callback();
                }
            });
        },
        // 事件：双击绑定事件
        initPaging: function(obj, length) {
            obj.find("table").DataTable({
                "iDisplayLength": length,
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": false,
                "info": true,
                "autoWidth": false
            });
        }

    };
    module.exports = indexInfoQuery;
});
