define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('loginSignIn');

	// 添加员工
	srvMap.add("addUserinfoSignIn", pathAlias + "retMessage.json", "aiga/staff/saveSignIn");
 	//get org
    srvMap.add("findAllOrg", pathAlias+"getSysMessageList.json", "sys/organize/findAllOrg");
 	//get rol
    srvMap.add("getSysRole", pathAlias+"getSysMessageList.json", "sys/role/list");  
 	//change pwd
    srvMap.add("changeMyPass", pathAlias+"getSysMessageList.json", "aiga/staff/changeMyPass");
	//查询账户信息
	srvMap.add("getAccountInfo", pathAlias+"getSysMessageList.json", "aiga/staff/list");
	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			this.login();
			this.changepwd();
			this.signin();
			this.accountSearch();
		},
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
	//账号查询
	accountSearch: function() {
		var self = this;
		$("#JS_accountSearch").off('click').on('click',function() {
            var _modal = $("#JS_accountSearchModal");
            var _form = $("#JS_accountSearchForm");
            var _dom = $("#JS_accountList");
            _modal.modal('show');
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function() {
				var _cmd = _form.serialize();
				if(_cmd=='name=&billId='){					
					return;
				}
				var _domPagination = _form.find("[name='pagination']");
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Utils.getServerPage(srvMap.get('getAccountInfo'),_cmd,function(json){
					window.XMS.msgbox.hide();
					var template = Handlebars.compile($('#TPL_accountList').html());				
	        		var tablebtn = _form.find("[name='content']");
	        		tablebtn.html(template(json.data.content));
	        		Utils.eventTrClickCallback(_dom);
				},_domPagination);
			});
		});
	},
    //注册function
	signin:function(){
		var self = this;
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
                if($.trim(_form.find("input[name='billId']").val())==''){
                    XMS.msgbox.show('亲，手机号码不能为空！', 'error', 2000);
                    return false;
                }
                if($.trim(_form.find("input[name='email']").val())==''){
                    XMS.msgbox.show('亲，邮箱不能为空！', 'error', 2000);
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
				if($.trim(_form.find("select[name='organizeId']").val())=='' || $.trim(_form.find("input[name='roleId']").val())==''){
                    XMS.msgbox.show('亲，用户所属组织和用户角色不能为空！', 'error', 2000);
                    return false;
                }
                var strs= new Array(); //定义一数组
				strs=cmd.split("&roleId="); //字符分割
				var _cmd = strs[0]+"&roleId=";
				for(var i = 1;i < strs.length; i++) {
					_cmd += strs[i] + ",";
                }
                _cmd = _cmd.substring(0, _cmd.length - 1);
                Rose.ajax.postJson(srvMap.get('addUserinfoSignIn'), _cmd, function(json, status) {
					if(status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('注册成功！', 'success', 2000);
						_modal.modal('hide');
						//成功后表单清除
						self._apply_data_clear();
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
                
            })
        })
	  },
	  //申请页表单清除
        _apply_data_clear: function() {
        	var domInput = $("#JS_signInForm").find('input,textarea');
        	domInput.each(function() {
        		var domNow = $(this);
        		if(domNow.attr("type") == 'text') {
        			domNow.val('');
        		} else if(domNow.attr("type") == 'password') {
        			domNow.val('');
        		} else if(domNow.attr("type") == 'checkbox') {
        			domNow.removeAttr("checked");
        		} else if(domNow.attr("type") == 'textarea') {
        			domNow.val('');
        		} else if(domNow.attr("type") == 'file') {
        			domNow.after(domNow.clone().val(""));      
        			domNow.remove();  
        		}
        	});
        },
	}
    module.exports = Query;
});

