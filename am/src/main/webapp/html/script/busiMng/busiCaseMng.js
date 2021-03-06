define(function(require, exports, module) {

    // 路径重命名
    var pathAlias = "busiMng/";
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化菜单列表
    srvMap.add("getModulList", pathAlias + "getModulList.json", "");

    //获取保存结果
    srvMap.add("getCaseList", pathAlias + "busiCaseList.json", "");
    //修改
    srvMap.add("updateCase", pathAlias + "getBusiInfo.json", "");

    // 模板对象
    var Tpl = {
        getCaseList: $("#TPL_busiCaseList").html(),
    };

    // 容器对象
    var Dom = {
        busiCaseList: '#JS_busiCaseList',
        treeModuls: "#JS_busiModulTree",
        modalUpdateCase: "#modal_busiCaseInfo",
        formCaseInfo: "#Js_busiCaseInfo",
    };

    //当前菜单id
    var currentModul = 0;

    var setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "funcId",
                pIdKey: "parentId"
            }
        },

        callback: {
            onClick: function(event, treeId, treeNode) {

                currentModul = treeNode.funcId;
                currentModulName = treeNode.name;
                var cmd = "funcId=" + currentModul;
                initFunction.getCaseList(cmd);

            }
        }
    };

    var initFunction = {


        init: function() {
            this._render();
        },
        _render: function() {

            this.initTree();
            this.getCaseList();

        },

        initTree: function() {
            Rose.ajax.getJson(srvMap.get('getModulList'), '', function(json, status) {
                if (status) {
                    console.log(json.data);
                    $.fn.zTree.init($(Dom.treeModuls), setting, json.data);
                    var treeObj = $.fn.zTree.getZTreeObj(Dom.treeModuls);
                    console.log(treeObj);
                }
            });
        },

        getCaseList: function(cmd) {
        	var self = this;
            Rose.ajax.getJson(srvMap.get('getCaseList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getCaseList);
                    console.log(json.data)
                    $(Dom.busiCaseList).html(template(json.data.content));
                    Utils.eventTrClickCallback($(Dom.busiCaseList),function(){
                    	self.updateCase();
                    });
                }
            });
        },

        updateCase: function() {
            var self = this;
            var _form = $(Dom.formCaseInfo);
            $(Dom.modalUpdateCase).modal('show');

            _form.find('button[name="submit"]').unbind();
            _form.find('button[name="submit"]').bind('click', function() {
                var cmd = _form.serialize();
                Rose.ajax.postJson(srvMap.get('updateCase'), cmd, function(json, status) {
                    if (status) {
                        XMS.msgbox.show('修改用例成功！', 'success', 3000)
                        $(Dom.modalUpdateCase).modal('hide');
                    }
                });

            });

        },

        modulDel: function() {
            var _form = $(Dom.getModulInfo);
            var self = this;
            _form.find('button[name="del"]').bind('click', function() {
                var cmd = "funcId=" + currentModul;
                console.log(cmd);
                Rose.ajax.postJson(srvMap.get('deleModul'), cmd, function(json, status) {
                    if (status) {
                        self.iniModulList();
                        OperateState = null;
                        currentModul = 0;
                        XMS.msgbox.show('删除菜单成功！', 'success', 3000)
                        _form.find('input').val("");
                        console.log(json.data);
                        _form.find('button[name="del"]').attr({
                            "disabled": "disabled"
                        });
                        _form.find('button[name="save"]').attr({
                            "disabled": "disabled"
                        });
                        // _form.find('button[name="save"]').removeAttr("disabled");//将按钮可用
                        // _form.find('button[name="del"]').removeAttr("disabled");//将按钮可用
                    } else {
                        XMS.msgbox.show(json.retMessage, 'error', 3000)
                    }
                });

            })
        },

    };


    module.exports = initFunction;
});
