define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 路径重命名
    var pathAlias = "email/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getAddresseeList", pathAlias + "getAddresseeList.json", "aiga/employee/email");
    srvMap.add("send", pathAlias + "retMessage.json", "sys/email/send");


    // 模板对象
    var Tpl = {

    };

    // 容器对象
    var Dom = {
        emailForm: '#JS_emailForm'

    };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new"
    }

    var Query = {
        init: function() {
            // 邮件发送
            this.sendEmail();
        },

        // 发送邮件
        sendEmail: function() {
            var self = this;
            var _form = $(Dom.emailForm);
            Utils.setSelectData(_form);
            var _send = _form.find("[name='send']");
            _send.unbind('click');
            _send.bind('click', function() {
                self.checkForm($(Dom.emailForm), function() {
                    var _addressee = _form.find("[name='addressee']").val();
                    var _ccList = _form.find("[name='ccList']").val();
                    var _subject = _form.find("[name='subject']").val();
                    var markupStr = $('#summernote').summernote('code');
                    var _content = encodeURI(markupStr);
                    console.log(_content)
                    var files = _form.find("[name='attachment']")[0].files;
                    console.log(files);
                    var fileList = [];
                    for (i = 0; i < files.length; i++) {
                        fileList.push(files[i]);    
                    }
                    console.log(fileList);
                    //var cmd = "addressee=" + _addressee + "&ccList=" + _ccList + "&subject=" + _subject + "&content=" + markupStr + "&files=" + files;
                    var cmd = {
                        addressee: _addressee,
                        ccList: _ccList,
                        subject: _subject,
                        content: _content,
                        files: fileList
                    }

                    // XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    // Rose.ajax.postJsonUpload(srvMap.get('send'), cmd, function(json, status) {
                    //     if (status) {
                    //         window.XMS.msgbox.show('发送成功！', 'success', 2000);
                    //     }
                    // });
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    $.ajaxUpload({
                        url: srvMap.get('send'),
                        data: cmd,
                        success: function(data, status, xhr) {
                            console.log(data);
                            if (status) {
                                window.XMS.msgbox.show('发送成功！', 'success', 2000);
                            }
                        }
                    });
                })
            });
        },
        checkForm: function(objForm, callback) {
            var state = true;
            var text = '';
            $(objForm).find(':input[required]')
                .not(':button, :submit, :reset, :hidden').each(function() {
                    var _val = $.trim($(this).val());
                    var _text = $.trim($(this).prev().text());
                    if (_val == null || _val == undefined || _val == '') {
                        state = false;
                        text = _text;
                        return false;
                    }
                })
            if (state) {
                callback(state);
            } else {
                XMS.msgbox.show('收件人不能为空！', 'error', 2000);
            }
        },
    };
    module.exports = Query;
});
