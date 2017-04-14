define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('caseGroup');
    
    // 路径重命名
    var pathAlias = "caseGroup/";

    //显示用例组列表
    srvMap.add("getCaseGroupList", pathAlias + "getCaseGroupList.json", "sys/autoGroup/list");
    //新增用例组
    srvMap.add("addCaseGroup", pathAlias + "retMessage.json", "sys/autoGroup/save");
    //删除用例组
    srvMap.add("delCaseGroup", pathAlias + "retMessage.json", "sys/autoGroup/delete");
    //修改用例组(名称)
    srvMap.add('updateCaseGroup', pathAlias + "retMessage.json", "sys/autoGroup/update");
    //系统大类下拉框显示
    srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
    //系统子类下拉框
    srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
    //功能点下拉框
    srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");
    //获取要修改的用例组的信息
    srvMap.add("getCaseGroupInfo", pathAlias + "getCaseGroupInfo.json", "sys/autoGroup/findOne");
    //获取用例列表
    srvMap.add("getCaseList", pathAlias + "getCaseList.json", "sys/autoGroup/caseList");
    //获取已关联的用例列表
    srvMap.add('getRelaCaseList', pathAlias + "getRelaCaseList.json", "sys/autoGroup/caseRelatGroupList");
    //新增用例组用例关联
    srvMap.add('addRelaCase', pathAlias + "retMessage.json", "sys/autoGroup/caseRelatGroupSave");
    //删除用例组用例关联
    srvMap.add('delRelaCase', pathAlias + "retMessage.json", "sys/autoGroup/caseRelatGroupDel");
    //保存执行顺序
    srvMap.add('saveGroupOrder', pathAlias + "retMessage.json", "sys/autoGroup/groupOrderUpdate");

    // 模板对象
    var Tpl = {
        getCaseGroupList: $("#TPL_getCaseGroupList").html(), //获取用例组列表
        addCaseGroupInfo: $("#TPL_addCaseGroupInfo").html(), //获取添加用例组表单 
        getCaseList: $("#TPL_getCaseList").html(), //获取用例列表
        getRelaCaseList: $("#TPL_getRelaCaseList").html()
    };

    // 容器对象
    var Dom = {
        getCaseGroupList: '#JS_getCaseGroupList', //获取用例组列表    
        addCaseGroupInfo: '#JS_addCaseGroupInfo', //获取添加用例组的表单数据
        updateCaseGroupInfo: '#JS_updateCaseGroupInfo', // 编辑用例组获取用例组的名称 
        updateCaseGroupModal: '#JS_updateCaseGroupModal', //弹出编辑用例组界面
        getCaseList: '#JS_getCaseList', //获取用例列表
        getRelaCaseList: '#JS_getRelaCaseList', //获取已关联的用例列表
        //功能按钮
        addCaseGroup: '#JS_addCaseGroup', //新增用例组
        delCaseGroup: '#JS_delCaseGroup', //删除用例组
        updateCaseGroup: '#JS_updateCaseGroup', //编辑用例组
        updateCaseGroupSubmit: '#JS_updateCaseGroupSubmit', //修改用例组名称
        relaCase: '#JS_relaCase', //关联用例
        getRelaCaseWrap: '#JS_getRelaCaseWrap', //用于抓取tab2上两个按钮
        //modal
        addCaseGroupModal: '#JS_addCaseGroupModal',
        //查询用例
        queryCaseList: '#JS_queryCaseList', //获取用例列表(搜索)
        //查询用例组
        queryCaseGroup: '#JS_queryCaseGroup' //抓取表单搜索用例组
    };

    var Data = {
        groupId: null
    }

    var Init = {
        init: function() {
            this._render();
        },
        _render: function() {
            // 默认加载接口
            this.getCaseGroupList();
            this.queryCaseGroup();
            this.addCaseGroup();
            this.delCaseGroup();
            this.updateCaseGroup();


        },
        // 用例组列表
        getCaseGroupList: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getCaseGroupList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getCaseGroupList);
                    console.log(json.data)
                    $(Dom.getCaseGroupList).html(template(json.data));
                    self.eventClickChecked($(Dom.getCaseGroupList));
                    self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                            var _data = self.getCheckedCaseGroup();
                            Data.groupId = _data.groupId;
                            console.log(Data.groupId);
                            var cmd = "groupId=" + _data.groupId;
                            console.log(cmd);
                            self.getCaseGroupInfo(cmd);
                        })
                        //设置分页
                    self.initPaging($(Dom.getCaseGroupList), 8)
                }
            });
        },
        // 按条件查询用例组
        queryCaseGroup: function() {
            var self = this;
            var _form = $(Dom.queryCaseGroup);
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                var cmd = $(Dom.queryCaseGroup).serialize();
                self.getCaseGroupList(cmd);
            })
        },
        // 新增用例组
        addCaseGroup: function() {
            var self = this;
            $(Dom.addCaseGroup).unbind('click');
            $(Dom.addCaseGroup).bind('click', function() {
                // 弹出层
                $(Dom.addCaseGroupModal).modal('show');
                //组件表单校验初始化
                var _form = $(Dom.addCaseGroupInfo);
                var template = Handlebars.compile(Tpl.addCaseGroupInfo);
                _form.html(template({}));
                // 表单提交
                $("#JS_addCaseGroupSubmit").unbind('click');
                $("#JS_addCaseGroupSubmit").bind('click', function() {
                    var cmd = _form.serialize();
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('addCaseGroup'), cmd, function(json, status) {
                        if (status) {
                            // 添加用户成功后，刷新用户列表页
                            XMS.msgbox.show('添加用例组成功！', 'success', 2000)
                                // 关闭弹出层
                            $(Dom.addCaseGroupModal).modal('hide')
                            setTimeout(function() {
                                self.getCaseGroupList();
                            }, 1000)
                        }
                    });
                })
            })
        },
        //删除用例组
        delCaseGroup: function() {
            var self = this;
            $(Dom.delCaseGroup).unbind('click');
            $(Dom.delCaseGroup).bind('click', function() {
                var _data = self.getCheckedCaseGroup();
                if (_data) {
                    var cmd = "groupId=" + _data.groupId;
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delCaseGroup'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getCaseGroupList();
                            }, 1000)
                        }
                    });
                }
            });
        },
        //修改用例组
        updateCaseGroup: function() {
            var self = this;
            $(Dom.updateCaseGroup).unbind('click');
            $(Dom.updateCaseGroup).bind('click', function() {
                var _data = self.getCheckedCaseGroup();
                //存储在全局变量
                Data.groupId = _data.groupId;
                console.log(Data.groupId);
                var cmd = "groupId=" + _data.groupId;
                if (cmd) {
                    self.getCaseGroupInfo(cmd);
                }
            });
        },
        //获取用例组信息 编辑用例组（弹窗）
        getCaseGroupInfo: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getCaseGroupInfo'), cmd, function(json, status) {
                if (status) {
                    // 表单校验初始化
                    var _form = $(Dom.updateCaseGroupInfo);
                    var _formSelect = $(Dom.queryCaseList);
                    Utils.setSelectData(_formSelect);
                    var template = Handlebars.compile(Tpl.addCaseGroupInfo);
                    console.log(json.data);
                    _form.html(template(json.data));
                    self.getCaseList();
                    self.queryCaseList();
                    self.getRelaCaseList();
                    self.relaCase();
                    self.delRelaCase();
                    self.saveGroupOrder();
                    // 弹出层
                    $(Dom.updateCaseGroupModal).modal('show');

                    // 提交
                    $(Dom.updateCaseGroupSubmit).unbind('click');
                    $(Dom.updateCaseGroupSubmit).bind('click', function() {

                        // var _cmd = "&groupId=" + Data.groupId;
                        var cmd = _form.serialize() + "&updateId=1";
                        console.log(cmd);
                        XMS.msgbox.show('数据加载中，请稍候...', 'loading')
                        Rose.ajax.postJson(srvMap.get('updateCaseGroup'), cmd, function(json, status) {
                            if (status) {
                                // 添加用户成功后，刷新用户列表页
                                XMS.msgbox.show('修改用例组成功！', 'success', 2000)
                                    // 关闭弹出层
                                $(Dom.updateCaseGroupModal).modal('hide')
                                setTimeout(function() {
                                    self.getCaseGroupList();
                                }, 1000)
                            }
                        });
                        //});
                    })
                }
            });
        },
        // 用例列表
        getCaseList: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getCaseList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getCaseList);
                    console.log(json.data)
                    $(Dom.getCaseList).html(template(json.data));
                    //单击选中
                    self.eventClickChecked($(Dom.getCaseList));
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
                    //设置分页
                    self.initPaging($(Dom.getCaseList), 4)
                }
            });
        },
        // 已关联用例列表
        getRelaCaseList: function(cmd) {
            var self = this;
            var cmd = "groupId=" + Data.groupId;
            console.log(cmd);
            Rose.ajax.postJson(srvMap.get('getRelaCaseList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getRelaCaseList);
                    console.log(json.data)
                    $(Dom.getRelaCaseList).html(template(json.data));
                    //单击选中
                    self.eventClickChecked($(Dom.getRelaCaseList));
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
                    //设置分页
                    self.initPaging($(Dom.getRelaCaseList), 4)
                }
            });
        },
        // 按条件查询用例组
        queryCaseList: function() {
            var self = this;
            var _form = $(Dom.queryCaseList);
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                var cmd = $(Dom.queryCaseList).serialize();
                self.getCaseList(cmd);
            })
        },
        //关联用例新增
        relaCase: function() {
            var self = this;
            $(Dom.relaCase).unbind('click');
            $(Dom.relaCase).bind('click', function() {
                var _data = self.getCheckedCase();
                if (_data) {
                    var _groupId = Data.groupId;
                    console.log(_groupId);
                    var _autoIdsArray = [];
                    _data.each(function() {
                        _autoIdsArray.push($(this).val());
                    })
                    var _cmd = "groupId=" + _groupId;
                    var _cmd1 = "&autoIds=" + _autoIdsArray.join(",");
                    var cmd = _cmd + _cmd1 + "&creatorId=1";
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('addRelaCase'), cmd, function(json, status) {
                        if (status) {
                            if (json.data.flag == 'false') {
                                window.XMS.msgbox.show('不能重复关联已关联用例！', 'error', 2000);
                                return;
                            } else {
                                window.XMS.msgbox.show('关联用例成功！', 'success', 2000)
                                setTimeout(function() {
                                    self.getRelaCaseList("groupId=" + _groupId);
                                    self.getCaseList("groupId=" + _groupId);
                                }, 1000)
                            }

                        }
                    });
                }
            });
        },
        // 删除已关联用例
        delRelaCase: function() {
            var self = this;
            var _domDel = $(Dom.getRelaCaseWrap).find("[name='del']");
            _domDel.unbind('click');
            _domDel.bind('click', function() {
                var _data = self.getCheckedRelaCase();
                if (_data) {
                    var _groupId = Data.groupId;
                    console.log(_groupId);
                    var _autoIdsArray = [];
                    _data.each(function() {
                        _autoIdsArray.push($(this).val());
                    })
                    var _cmd = "groupId=" + _groupId;
                    var _cmd1 = "&autoIds=" + _autoIdsArray.join(",");
                    var cmd = _cmd + _cmd1;
                    console.log(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delRelaCase'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除已关联用例成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaCaseList("groupId=" + _groupId);
                            }, 1000)
                        }
                    });
                }
            });
        },
        // 保存执行顺序
        saveGroupOrder: function() {
            var self = this;
            var _domSave = $(Dom.getRelaCaseWrap).find("[name='save']");
            _domSave.unbind('click');
            _domSave.bind('click', function() {
                var _data = self.getCheckedRelaCase();
                var _data1 = self.getCheckedGroupOrder();
                if (_data) {
                    var _groupId = Data.groupId;
                    console.log(_groupId);
                    var _autoIdsArray = [];
                    _data.each(function() {
                        _autoIdsArray.push($(this).val());
                    })
                    var _groupOrdersArray = [];
                    _data1.each(function() {
                        _groupOrdersArray.push($(this).val());
                    })
                    var _cmd = "groupId=" + _groupId;
                    var _cmd1 = "&autoIds=" + _autoIdsArray.join(",");
                    var _cmd2 = "&groupOrders=" + _groupOrdersArray.join(",");
                    var cmd = _cmd + _cmd1 + _cmd2;
                    console.log(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('saveGroupOrder'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存执行顺序成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaCaseList("groupId=" + _groupId);
                            }, 1000)
                        }
                    });
                }
            });
        },

        //获取选中用例组
        getCheckedCaseGroup: function() {
            var _obj = $(Dom.getCaseGroupList).find("input[type='radio']:checked").parents("tr");
            var _groupId = _obj.find("input[name='groupId']")
            if (_groupId.length == 0) {
                window.XMS.msgbox.show('请先选择一个用例组！', 'error', 2000);
                return;
            } else {
                var data = {
                    groupId: ""
                }
                data.groupId = _groupId.val();
            }
            console.log(data);
            return data;
        },
        //获取选中用例
        getCheckedCase: function() {
            var _obj = $(Dom.getCaseList).find("input[type='checkbox']:checked").parents("tr");
            var _autoId = _obj.find("input[name='autoId']");
            console.log(_autoId);
            if (_autoId.length == 0) {
                window.XMS.msgbox.show('请先选择一个用例！', 'error', 2000);
                return;
            } else {
                var _data = $(_autoId);
                data = _data;
                console.log(data);
            }
            return data;
        },
        //获取选中已关联用例
        getCheckedRelaCase: function() {
            var _obj = $(Dom.getRelaCaseList).find("input[type='checkbox']:checked").parents("tr");
            var _autoId = _obj.find("input[name='autoId']");
            console.log(_autoId);
            if (_autoId.length == 0) {
                window.XMS.msgbox.show('请先选择一个已关联用例！', 'error', 2000);
                return;
            } else {
                var _data = $(_autoId);
                data = _data;
                console.log(data);
            }
            return data;
        },
        //获取选中已关联用例的执行顺序
        getCheckedGroupOrder: function() {
            var _obj = $(Dom.getRelaCaseList).find("input[type='checkbox']:checked").parents("tr");
            var _groupOrder = _obj.find("input[name='groupOrder']");
            console.log(_groupOrder);
            if (_groupOrder.length == 0) {
                window.XMS.msgbox.show('请先选择一个已关联用例！', 'error', 2000);
                return;
            } else {
                var _data = $(_groupOrder);
                data = _data;
                console.log(data);
            }
            return data;
        },
        //事件：单机选中当前行
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
        // 事件：双击选中当前行
        eventDClickCallback: function(obj, callback) {
            obj.find("tr").bind('dblclick ', function(event) {
                if (callback) {
                    callback();
                }
            });
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
                "autoWidth": false
            });
        }
    };
    module.exports = Init;
});
