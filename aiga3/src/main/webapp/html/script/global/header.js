define(function(require, exports, module) {

    var Welcome =  require('script/welcome');
    // 用户信息接口
    srvMap.add("getUserinfo", "global/getUserInfo.json", "currentUser");
    // 获取总计结果 无入参
    srvMap.add("getHeaderKpiList", "welcome/getWelcomeKpiList.json", "sys/home/kpiList");
    // 获取总计结果 无入参
    srvMap.add("getHeaderKpiSave", "global/retMessage.json", "sys/home/kpiSave");
    // 模板对象
    var Tpl = {
        header: require('tpl/global/header.tpl'),
        getHeaderKpiListConfig: require('tpl/global/getHeaderKpiListConfig.tpl')
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
            var self = this;
            // 请求：用户信息接口
            Rose.ajax.getJson(srvMap.get('getUserinfo'), '', function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.header);
                    Mod.header.html(template(json.data));
                    LOGINUSER = json.data;
                    self.getHeaderKpiListConfig();
                    // 事件：退出系统
                    $("#JS_logout").click(function() {
                        if (confirm('尊敬的用户' + json.data.staff.name + '，您确认退出吗？')) {
                            window.parent.location.href='login.html';
                        }
                    })

                }
            });
        },
        getHeaderKpiListConfig: function() { //配置指标
            var self = this;
            var _configBtn = $("a[name='getHeaderKpiListConfig']");
            _configBtn.unbind('click');
            _configBtn.bind('click', function() {
                var _modal = $('#Modal_getHeaderKpiListConfig');
                // 显示弹框
                _modal.modal('show');
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.getJson(srvMap.get('getHeaderKpiList'), '', function(json, status) {
                    if (status) {
                        window.XMS.msgbox.hide();
                        var template = Handlebars.compile(Tpl.getHeaderKpiListConfig);
                        $('#JS_getHeaderKpiListConfig').html(template(json.data));
                        $('#JS_getHeaderKpiListConfig').find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                            checkboxClass: 'icheckbox_minimal-blue',
                            radioClass: 'iradio_minimal-blue'
                        });
                        self.getHeaderKpiSave();

                    }
                });
            });
        },
        getHeaderKpiSave:function(){
            var self = this;
            var _Modal = $('#Modal_getHeaderKpiListConfig');
            var _saveBtn = _Modal.find("[name='save']");
            _saveBtn.unbind('click');
            _saveBtn.bind('click', function() {
                var data = [];
                _Modal.find("input[type='checkbox']:checked").each(function(){
                    data.push($(this).val());
                })
                if(data.length >= 4){
                    var cmd = 'kpiIds='+data.join(",");
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.getJson(srvMap.get('getHeaderKpiSave'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存成功！', 'success', 2000)
                            _Modal.modal('hide');
                            setTimeout(function() {
                                Welcome.getWelcomeKpiList();
                            }, 1000)

                        }
                    });
                }else{
                    window.XMS.msgbox.show('亲，您至少要选择4项！', 'error', 2000);
                    return false;
                }
            });
        }
    };
    Query.init();
    // 暴露渲染对象
    module.exports = Query;
});
