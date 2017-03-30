package com.ai.aiga.view.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.SecuritySv;
import com.ai.aiga.view.json.Menu;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Global-全局")
@Controller
@RequestMapping(path = "/global")
public class GlobalController {
	
	@Autowired
	private SecuritySv securitySv;
	
	@RequestMapping(path = "/menu", method=RequestMethod.GET)
	@ApiOperation(value = "得到主菜单value", notes = "notes")
	public @ResponseBody JsonBean getMenus(){
		JsonBean bean = new JsonBean();
		
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		if(userInfo == null){
			bean.fail("用户未登陆!");
		}else{
			List<Menu> menus = securitySv.getMenus(userInfo);
			bean.setData(menus);
		}
		
		return bean;
	}
	
	

}
