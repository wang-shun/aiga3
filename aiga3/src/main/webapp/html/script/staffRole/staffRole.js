define(function(require, exports, module) {

    // 通用工具模块
    var Utils = require("global/utils.js");

    //路径重命名
    var pathAlias = "staffRole/";
    // 查询所有员工
    srvMap.add("getUserinfoList", pathAlias + "getUserinfoList.json", "aiga/staff/list");
    //查询所有岗位信息
    srvMap.add("getStaffRoleList", pathAlias + "getStaffRoleList.json", "sys/role/list");
    //根据当前员工ID调取已选择的岗位信息roleAuthorID,roleID
    srvMap.add("getStaffRoleCheckedList", pathAlias + "getStaffRoleCheckedList.json", "sys/staffrole/list");
    //修改员工角色
    srvMap.add("saveStaffRole", pathAlias + "retMessage.json", "sys/staffrole/update");

    // 模板对象
    var Tpl = {
        getUserinfoList: require('tpl/staffRole/getUserinfoList.tpl'),
        getStaffRoleList: require('tpl/staffRole/getStaffRoleList.tpl')
    };

    // 容器对象
    var Mod = {
        getUserinfoList: '#Page_getUserinfoList',
        getStaffRoleList: '#Page_getStaffRoleList'
    };
    var Dom = {
        saveStaffRole: "#JS_saveStaffRole",
        getUserinfoListTable: '#JS_getUserinfoListTable',
        getStaffRoleListTable: '#JS_getStaffRoleListTable',
        queryStaffForm: '#JS_queryStaffForm'
    };
    var Data = {
        staffId: null,
        queryListCmd: null
    }


    var indexInfoQuery = {
        init: function() {
            this._render();

        },
        _render: function() {
            // 查询所有员工
            this.getUserinfoList();
            // 查询所有角色
            this.getStaffRoleList();
            this.saveStaffRole();
            this.queryStaffForm();
        },
        queryStaffForm: function() {
            var self = this;
            var _form = $(Dom.queryStaffForm);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getUserinfoList(cmd);
            });
        },
        getUserinfoList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            Rose.ajax.postJson(srvMap.get('getUserinfoList'), _cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getUserinfoList);
                    console.log(json.data.content);
                    $(Mod.getUserinfoList).html(template(json.data.content));

                    Utils.eventTrClickCallback($(Dom.getUserinfoListTable))

                    $(Dom.getUserinfoListTable).find("tr").bind('click', function(event) {
                        $(this).find('.minimal').iCheck('check');
                        var _data = self.getCheckedRole();
                        var _stafId = _data.staffId;
                        var cmd = "staffId=" + _stafId;
                        Data.staffId = _stafId;
                        console.log(cmd);
                        self.getStaffRoleCheckedList(cmd)
                    });

                    // 滚动条
                    $(Dom.getUserinfoListTable).parent().slimScroll({
                        "height": '500px'
                    });

                }
            });
        },
        getStaffRoleCheckedList: function(cmd) {
            $("input[name='roleId']").iCheck('uncheck');
            Rose.ajax.postJson(srvMap.get('getStaffRoleCheckedList'), cmd, function(json, status) {
                if (status) {
                    var _array = json.data;
                    console.log(_array);
                    for (x in _array) {
                        $("#JS_role_" + _array[x].roleId).iCheck('check');
                    }
                }
            });

        },
        getStaffRoleList: function(cmd) {
            Rose.ajax.postJson(srvMap.get('getStaffRoleList'), cmd, function(json, status) {
                var self = this;
                if (status) {
                    var template = Handlebars.compile(Tpl.getStaffRoleList);
                    console.log(json.data)
                    $(Mod.getStaffRoleList).html(template(json.data));
                    //iCheck
                    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                        checkboxClass: 'icheckbox_minimal-blue',
                        radioClass: 'iradio_minimal-blue'
                    });

                    // 事件：双击选中当前行数据
                    $('#JS_getStaffRoleListTable').find("tr").bind('click', function(event) {
                        $(this).find('.minimal').iCheck('check');
                    });
                    // 滚动条
                    $('#JS_getStaffRoleListTable').parent().slimScroll({
                        "height": '500px'
                    });
                }
            });
        },
        saveStaffRole: function() {
            var self = this;
            $(Dom.saveStaffRole).bind('click', function() {
                var _dom = $(Dom.getStaffRoleListTable).find("input[name='roleId']:checked");
                var _roleIdsArray = [];
                _dom.each(function() {
                    _roleIdsArray.push($(this).val());
                })
                var cmd = {
                    "staffId": Data.staffId,
                    "roleIds": _roleIdsArray.join(",")
                }
                console.log(cmd);
                if (_roleIdsArray.length == 0) {
                    window.XMS.msgbox.show('请先选择一个授权权限点！', 'error', 2000);
                    return;
                } else {
                    Rose.ajax.postJson(srvMap.get('saveStaffRole'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存成功！', 'success', 2000)
                        }
                    });
                }
            });
        },
        getCheckedRole: function() {
            var _obj = $(Dom.getUserinfoListTable).find("input[type='radio']:checked").parents("tr");
            var _staffId = _obj.find("input[name='staffId']")
            if (_staffId.length == 0) {
                window.XMS.msgbox.show('请先选择一个员工！', 'error', 2000);
                return;
            } else {
                var data = {
                    roleAuthorId: "",
                    staffId: "",
                    roleId: ""
                }
                data.staffId = _staffId.val();
            }
            return data;
        }
    };
    module.exports = indexInfoQuery;
});
