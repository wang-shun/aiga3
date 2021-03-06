package com.ai.am.view.controller.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.security.shiro.UserInfo;
import com.ai.am.view.controller.common.dto.CurrentUser;
import com.ai.am.view.json.base.JsonBean;
import com.ai.am.view.util.SessionMgrUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Login")
@Controller
public class LoginController {
	
//	@RequestMapping(path = "/login", method=RequestMethod.GET)
//	public String goToLogin(){
//		return "login";
//	}
	
	@RequestMapping(path = "/login", method=RequestMethod.POST)
	@ApiOperation(value = "登陆", notes = "暂无")
	public @ResponseBody JsonBean login(String username, String password, String code){
		JsonBean bean = new JsonBean();
		
		Subject subject = SecurityUtils.getSubject();
		
		Integer count = SessionMgrUtil.addLoginCounts();
		
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			bean.fail("用户名或者密码错误!");
			return bean;
		}
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try { 
			//登录，即身份验证 
			subject.login(token);
			bean.success("success");
		} catch (AuthenticationException e) { 
			//5、身份验证失败
			if(count > 1){
				
			}
			bean.fail("用户名或者密码错误");
		}
		return bean;
	}
	
	@RequestMapping(path = "/currentUser")
	@ApiOperation(value = "获得当前用户信息", notes = "暂无")
	public @ResponseBody JsonBean currentUser(){
		JsonBean bean = new JsonBean();
		
		CurrentUser user = new CurrentUser();
		
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		if(userInfo != null){
			user.setStaff(userInfo.getStaff());
			user.setRoles(userInfo.getRoles());
		}
		
		bean.setData(user);
		return bean;
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.POST)
	public String logout(){
		
		Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
        }
		return "login";
	}
	
	
	
}
