/*
 * 头部公用模块
 * author:qijc
 */
define(function(require, exports, module) {

    // 用户信息接口
    srvMap.add("getUserinfo", "global/getUserInfo.json", "html/mock/global/getUserInfo.json");

    // 模板对象
    var Tpl = {
        header: require('tpl/global/header.tpl')
    };

    // 容器对象
    var Mod = {
        header: $('#Mod_header')
    };

    // 渲染对象
    var Query = {
        init: function(){
            this._render();
        },
        _render: function() {

            // 请求：用户信息接口
            Rose.ajax.getJson(srvMap.get('getUserinfo'), '', function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.header);
                    Mod.header.html(template(json.data));

                    // 事件：退出系统
                    $("#JS_logout").click(function() {
                        if (confirm('尊敬的用户' + json.data.userName + '，您确认退出吗？')) {
                            window.parent.location.reload();
                        }
                    })

                }
            });
        },
    };
    Query.init();
    // 暴露渲染对象
    module.exports = Query;
});
