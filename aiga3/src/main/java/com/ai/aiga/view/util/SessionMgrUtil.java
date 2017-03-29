package com.ai.aiga.view.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ai.aiga.security.shiro.UserInfo;

public class SessionMgrUtil {
	

	
	
	public static void addToSession(String key, Object obj){
		getSubject().getSession().setAttribute(key, obj);
	}
	
	public static Object getFromSession(String key){
		return getSubject().getSession().getAttribute(key);
	}
	
	/**
	 * 
	 */
	public static final String MANAGER_LOGIN_USERINFO = "ai_manager_login_userinfo";
	
	public static void cacheUserInfo(UserInfo userInfo){
		addToSession(MANAGER_LOGIN_USERINFO, userInfo);
	}
	
	public static UserInfo getUserInfo(){
		return (UserInfo) getFromSession(MANAGER_LOGIN_USERINFO);
	}
	
	
	/**
	 * 
	 */
	public static final String MANAGER_LOGIN_COUNT = "ai_manager_login_count";
	//待优化
	public static Integer addLoginCounts(){
		Integer count = (Integer) getSubject().getSession().getAttribute(MANAGER_LOGIN_COUNT);
		if(count != null){
			count = count + 1;
		}else{
			count = 1;
		}
		getSubject().getSession().setAttribute(MANAGER_LOGIN_COUNT, count);
		return count;
	}
	
	private static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	
	/**********************登陆验证码 start***************************/
	/**
	 * 验证码保存到session中的key
	 */
	public static final String MANAGER_LOGIN_VALIDATE_CODE = "ai_manager_validate_code";
	
	public static final void putValidateCode(String code){
		getSubject().getSession().setAttribute(MANAGER_LOGIN_VALIDATE_CODE, code);
	}
	
	public static final String getValidateCode(){
		 return (String) getSubject().getSession().getAttribute(MANAGER_LOGIN_VALIDATE_CODE);
	}
	
	/**********************登陆验证码 end***************************/

}
