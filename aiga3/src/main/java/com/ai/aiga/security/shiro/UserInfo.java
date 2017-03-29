package com.ai.aiga.security.shiro;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.ai.aiga.domain.AigaStaff;

public class UserInfo extends SimpleAuthenticationInfo{
	
	private AigaStaff staff;
	
	
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
	

}
