package com.ai.am.security.shiro;

import java.util.List;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.ai.am.domain.AigaFunction;
import com.ai.am.domain.AigaStaff;
import com.ai.am.domain.SysRole;

public class UserInfo extends SimpleAuthenticationInfo{
	
	private AigaStaff staff;
	
	private List<SysRole> roles;
	
	private List<AigaFunction> funs;
	
	
    public UserInfo(Object principal, Object credentials, String realmName) {
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = credentials;
    }

	public AigaStaff getStaff() {
		return staff;
	}

	public void setStaff(AigaStaff staff) {
		this.staff = staff;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<AigaFunction> getFuns() {
		return funs;
	}

	public void setFuns(List<AigaFunction> funs) {
		this.funs = funs;
	}
	

}
