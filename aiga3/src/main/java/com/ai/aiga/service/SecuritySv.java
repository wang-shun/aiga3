package com.ai.aiga.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class SecuritySv extends BaseService{
	
	@Autowired
	private AigaStaffSv aigaStaffSv;

	public AigaStaff getUserByName(String username) {
		AigaStaff user = aigaStaffSv.getStaffByCode(username);
		return user;
	}
	

	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	public void findMenus(UserInfo userInfo) {
		
		
	}

}
