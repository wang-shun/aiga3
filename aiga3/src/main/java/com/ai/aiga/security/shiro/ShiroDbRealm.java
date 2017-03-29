package com.ai.aiga.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.service.SecuritySv;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.view.util.SessionMgrUtil;


public class ShiroDbRealm extends AuthorizingRealm{
	
	private SecuritySv securitySv;
	
	public SecuritySv getSecuritySv() {
		return securitySv;
	}

	public void setSecuritySv(SecuritySv securitySv) {
		this.securitySv = securitySv;
	}

	/**
	 * 获取权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("taoyf -- protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc)");
		System.out.println("时间:" + DateUtil.getCurrTimeString());
		String username = (String)pc.getPrimaryPrincipal();
		
		UserPermissionInfo perInfo = new UserPermissionInfo();
		perInfo.setRoles(securitySv.findRoles(username));
		perInfo.setStringPermissions(securitySv.findPermissions(username));
		return perInfo;
	}

	/**
	 * 验证用户
	 * 
	 * 如果身份验证失败请捕获 AuthenticationException 或其子类，常见的如: 
	 * DisabledAccountException(禁用的帐号)、
	 * LockedAccountException(锁定的帐号)、 
	 * UnknownAccountException(错误的帐号)、
	 * ExcessiveAttemptsException(登录失败次数过 多)、
	 * IncorrectCredentialsException (错误的凭证)、
	 * ExpiredCredentialsException(过期的 凭证)等
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();  //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
		
        AigaStaff staff = securitySv.getUserByName(username);
		if(staff == null){
			throw new UnknownAccountException();
		}
		
		if(encryption(password).equals(staff.getPassword())){
			UserInfo userInfo = new UserInfo(username, password, getName());
			staff.setPassword("");
			userInfo.setStaff(staff);
			
			//没办法
			SessionMgrUtil.cacheUserInfo(userInfo);
			
			return userInfo;
		}else{
			throw new IncorrectCredentialsException();
		}
	}

	private String encryption(String password) {
		return password;
	}

}
