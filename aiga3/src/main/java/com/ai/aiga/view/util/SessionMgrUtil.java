package com.ai.aiga.view.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.security.shiro.UserInfo;

public class SessionMgrUtil {
	
	public static void addToSession(String key, Object obj){
		getSubject().getSession().setTimeout(72000000);
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
	
	/**
	 * 
	 * @ClassName: SessionMgrUtil :: getUserInfo
	 * @author: taoyf
	 * @date: 2017年4月1日 上午9:51:54
	 *
	 * @Description: 获得session中的用户UserInfo,包括用户基本信息, 角色, 菜单
	 * @return
	 */
	public static UserInfo getUserInfo(){
		return (UserInfo) getFromSession(MANAGER_LOGIN_USERINFO);
	}
	
	/**
	 * 
	 * @ClassName: SessionMgrUtil :: getStaff
	 * @author: taoyf
	 * @date: 2017年4月1日 上午9:52:18
	 *
	 * @Description:获得session中的人员信息
	 * @return
	 */
	public static AigaStaff getStaff(){
		UserInfo userInfo = getUserInfo();
		return userInfo.getStaff();
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
