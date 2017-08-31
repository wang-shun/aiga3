define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('loginSignIn');

 	//get id
    srvMap.add("getEventFindAll", pathAlias+"getSysMessageList.json", "archi/event/findAll");
	// 添加员工
	srvMap.add("addUserinfoSignIn", pathAlias + "retMessage.json", "aiga/staff/saveSignIn");
 	//get id
    srvMap.add("findAllOrg", pathAlias+"getSysMessageList.json", "sys/organize/findAllOrg");
 	//get id
    srvMap.add("getSysRole", pathAlias+"getSysMessageList.json", "sys/role/list");

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			this.login();
			this.changepwd();
			this.signin();
		},
//		    $(function() {
		login:function(){
            $('#login #password').focus(function() {
                $('#owl-login').addClass('password');
            }).blur(function() {
                $('#owl-login').removeClass('password');
            });
            $("body").keydown(function() {
                if (event.keyCode == "13") {//keyCode=13是回车键
                    $("#mysubmit").click();
                }
            });
            $("#mysubmit").click(function() {
                XMS.msgbox.show('正在登录中，请稍候...', 'loading');

                var data = {
                    username: $("#myform").find("input[name='username']").val(),
                    password: $("#myform").find("input[name='password']").val()
                }

                if($.trim(data.username)==''){
                    window.XMS.msgbox.show('亲，用户名不能为空！', 'error', 2000);
                    return false;
                }
                if($.trim(data.password)==''){
                    window.XMS.msgbox.show('亲，密码不能为空！', 'error', 2000);
                    return false;
                }

                // 待删除，用于测试前台跳转效果
                if(window.location.href.indexOf("8888")>=0){
                   setTimeout(function(){
                        window.location = "index.html";
                    },1500)
                }
                $.post("../login", data, function(d) {
                    if (d) {
                        var status = d["retCode"];
                        if (status == "200") {
                            window.location = "index.html";
                        } else {
                            window.XMS.msgbox.show(d["retMessage"], 'error', 2000);
                            //$("#tips-error").html(d["retMessage"]);
                        }
                    }
                });
                return false;
            });
		},
        //改密function
		changepwd:function(){
            $("#JS_changePassword").bind("click",function(){
            var _modal = $("#JS_changePasswordModal");
            var _form = $("#JS_changePasswordForm");
			Utils.setSelectData(_form);
            $("#JS_changePasswordModal").modal('show');
			
            _modal.find("[name='submit']").click(function(){
                if($.trim(_form.find("input[name='code']").val())=='' || $.trim(_form.find("input[name='password']").val())==''){
                    XMS.msgbox.show('亲，用户名和原密码不能为空！', 'error', 2000);
                    return false;
                }
                if($.trim(_form.find("input[name='newPassword']").val())=='' || $.trim(_form.find("input[name='recentnewPassword']").val())==''){
                    XMS.msgbox.show('亲，新密码和重复密码不能为空！', 'error', 2000);
                    return false;
                }
                if(!Rose.validate.Check('passwrd',_form.find("input[name='newPassword']").val())){
                    XMS.msgbox.show('亲，密码长度在6-10位之间，包含大写字母、小写字母及数字！', 'error', 2000);
                    return false;
                }
                if(_form.find("input[name='newPassword']").val()!=_form.find("input[name='recentnewPassword']").val()){
                    XMS.msgbox.show('亲，请确认两次密码输入一致！', 'error', 2000);
                    return false;
                }
                XMS.msgbox.show('密码修改中，请稍候...', 'loading');
                var data = {
                    code: $.trim(_form.find("input[name='code']").val()),
                    password: $.trim(_form.find("input[name='password']").val()),
                    newPassword: $.trim(_form.find("input[name='newPassword']").val())
                }
                $.post("/aiga3/aiga/staff/changeMyPass", data, function(d) {
	                if (d) {
	                    var status = d["retCode"];
	                    if (status == "200") {
	                        XMS.msgbox.show('密码修改成功', 'success', 2000);
	                        setTimeout(function(){
	                            _modal.modal('hide');
	                        },1500)
	                    } else {
	                        window.XMS.msgbox.show(d["retMessage"], 'error', 2000);
	                        //$("#tips-error").html(d["retMessage"]);
	                    }
	                }
            	});
            })
        })
	},
    //注册function
	signin:function(){
        $("#JS_signIn").bind("click",function(){
            var _modal = $("#JS_signInModal");
            var _form = $("#JS_signInForm");
			Utils.setSelectData(_form);
            _modal.modal('show');
            _modal.find("[name='signin']").click(function(){
            	var cmd = _form.serialize();
                if($.trim(_form.find("input[name='code']").val())=='' || $.trim(_form.find("input[name='password']").val())==''){
                    XMS.msgbox.show('亲，用户名和密码不能为空！', 'error', 2000);
                    return false;
                }
                if($.trim(_form.find("input[name='password']").val())=='' || $.trim(_form.find("input[name='recentPassword']").val())==''){
                    XMS.msgbox.show('亲，密码和重复密码不能为空！', 'error', 2000);
                    return false;
                }
                if(_form.find("input[name='password']").val()!=_form.find("input[name='recentPassword']").val()){
                    XMS.msgbox.show('亲，请确认两次密码输入一致！', 'error', 2000);
                    return false;
                }
				if($.trim(_form.find("select[name='organizeId']").val())=='' || $.trim(_form.find("select[name='roleId']").val())==''){
                    XMS.msgbox.show('亲，用户所属组织和用户角色不能为空！', 'error', 2000);
                    return false;
                }
                Rose.ajax.postJson(srvMap.get('addUserinfoSignIn'), cmd, function(json, status) {
					if(status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('注册成功！', 'success', 2000);
						_modal.modal('hide');
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
                
            })
        })
	  },
	}
    module.exports = Query;
});

