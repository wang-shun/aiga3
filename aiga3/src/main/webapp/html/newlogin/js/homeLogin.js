function init() {
	//密码找回按钮
	$('#lose_account').off('click').on('click',function() {
		$("#userLoginModal").modal('hide');
		setTimeout(function() {
			$("#loseAccountModal").modal('show');
		},300)
	});
	//主页按钮
	$(".momi-load").off('click').on('click',function() {
        $("#userLoginModal").modal('show');
	});
	//导航登陆按钮
	$("#titleLogin").off('click').on('click',function() {
        $("#userLoginModal").modal('show');
	});
	//回车监控
    $("#login").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $("#login_button").click();
        }
    });
    //找回密码按钮1
	$("#Button1").off('click').on('click',function() {
		var data = $("#account").val();
	    loseAccount('account',data);
	});
    //找回密码按钮2
	$("#Button2").off('click').on('click',function() {
		var data = $("#pwd_eml").val();
	    loseAccount('pwdEml',data);
	});    
    //初始化下拉框  组织
    $.post("../../sys/organize/findAllOrg", '', function(d) {
        if (d) {
            var status = d["retCode"];
            var _data = d["data"];
            if (status == "200") {
            	//拼装下拉框数据
            	var _html = '<option value="">请选择</option>';
            	for (var i in _data) {
                    var _json = _data[i];
                    var _key, _value;
                    _key = _json['organizeId'];
                    _value = _json['organizeName'];
                    _html += '<option value="' + _key + '">' + _value + '</option>';
                }
                $('#groupsignup').html(_html);
            } else {
                window.XMS.msgbox.show(d["retMessage"], 'error', 2000);
            }
        }
    });
}
//密码找回
function loseAccount(type,value) {
	var cmd = {
		type : type,
		value : value
	}
	//找回代码
	XMS.msgbox.show('密码找回中，请稍候...', 'loading');
    $.post('../../pwdreset', cmd, function(d) {
    	window.XMS.msgbox.hide();
        if (d) {
            var status = d["retCode"];
            if (status == "200") {
                window.XMS.msgbox.show('找回成功，密码已发送至你的邮箱', 'success', 4000);
            } else {
                window.XMS.msgbox.show(d["retMessage"], 'error', 4000);
            }
        }
    });	
}
//登陆
function loginMethod() {
	 var dom = $("#login");
     var data = {
        username: dom.find("[name='username']").val(),
        password: dom.find("[name='password']").val()
     }
     XMS.msgbox.show('正在登录中，请稍候...', 'loading');
     $.post("../../login", data, function(d) {
     	window.XMS.msgbox.hide();
        if (d) {
            var status = d["retCode"];
            if (status == "200") {
                window.location = "../index.html";
            } else {
                window.XMS.msgbox.show(d["retMessage"], 'error', 2000);
            }
        }
    });
}
//新注册
//注册
function registerMethodNew() {
    var data = {
    	staffCode:          $('#usernamesignup').val(),
    	staffName:          $('#namesignup').val(),
    	staffPassword:      $('#passwordsignup').val(),
        recentPassword:		$('#passwordsignup_confirm').val(),
        email:         		$('#emailsignup').val(),
        billId:        		$('#phonesignup').val(),
		organizeId:    		$('#groupsignup').val(),
		organizeName:  		$('#groupsignup').text(),
		state:              '1'
    }
    if($.trim(data.staffCode)=='' || $.trim(data.staffPassword)==''){
        XMS.msgbox.show('亲，账号和密码不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.staffName)==''){
        XMS.msgbox.show('亲，用户姓名不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.billId)==''){
        XMS.msgbox.show('亲，手机号码不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.email)==''){
        XMS.msgbox.show('亲，邮箱不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.staffPassword)=='' || $.trim(data.staffPassword)==''){
        XMS.msgbox.show('亲，密码和重复密码不能为空！', 'error', 2000);
        return false;
    }
    if(data.staffPassword!=data.recentPassword){
        XMS.msgbox.show('亲，请确认两次密码输入一致！', 'error', 2000);
        return false;
    }
	if($.trim(data.organizeId)==''){
        XMS.msgbox.show('亲，用户所属组织不能为空！', 'error', 2000);
        return false;
    }
    XMS.msgbox.show('正在注册中，请稍候...', 'loading');
    $.post("../../staff/info/apply", data, function(d) {
    	window.XMS.msgbox.hide();
        if (d) {
            var status = d["retCode"];
            if (status == "200") {
                window.XMS.msgbox.show("申请单成功:请等待管理员审批,通知邮件将会下发至邮箱请注意查收。", 'success', 2000);
            } else {
                window.XMS.msgbox.show("注册失败："+d["retMessage"], 'error', 2000);
            }
        }
    });          
}
//注册
function registerMethod() {
     var data = {
        code:          $('#usernamesignup').val(),
        name:          $('#namesignup').val(),
        password:      $('#passwordsignup').val(),
        recentPassword:$('#passwordsignup_confirm').val(),
        email:         $('#emailsignup').val(),
        billId:        $('#phonesignup').val(),
		organizeId:    $('#groupsignup').val(),
		roleId:        '1,11,31,41' 
     }
    if($.trim(data.code)=='' || $.trim(data.password)==''){
        XMS.msgbox.show('亲，账号和密码不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.name)==''){
        XMS.msgbox.show('亲，用户姓名不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.billId)==''){
        XMS.msgbox.show('亲，手机号码不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.email)==''){
        XMS.msgbox.show('亲，邮箱不能为空！', 'error', 2000);
        return false;
    }
    if($.trim(data.password)=='' || $.trim(data.recentPassword)==''){
        XMS.msgbox.show('亲，密码和重复密码不能为空！', 'error', 2000);
        return false;
    }
    if(data.password!=data.recentPassword){
        XMS.msgbox.show('亲，请确认两次密码输入一致！', 'error', 2000);
        return false;
    }
	if($.trim(data.organizeId)=='' || $.trim(data.roleId)==''){
        XMS.msgbox.show('亲，用户所属组织和用户角色不能为空！', 'error', 2000);
        return false;
    }
    XMS.msgbox.show('正在注册中，请稍候...', 'loading');
    $.post("../../aiga/staff/saveSignIn", data, function(d) {
    	window.XMS.msgbox.hide();
        if (d) {
            var status = d["retCode"];
            if (status == "200") {
            	var loginData = {
            		username: data.code,
        			password: data.password 
            	}
            	XMS.msgbox.show('正在登录中，请稍候...', 'loading');
			    $.post("../../login", loginData, function(d) {
			    	window.XMS.msgbox.hide();
			        if (d) {
			            var status = d["retCode"];
			            if (status == "200") {
			                window.location = "../index.html";
			            } else {
			                window.XMS.msgbox.show("登陆失败："+d["retMessage"], 'error', 2000);
			            }
			        }
			    });
            } else {
                window.XMS.msgbox.show("注册失败："+d["retMessage"], 'error', 2000);
            }
        }
    });              
}
init();