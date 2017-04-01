package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.Menu;

@Service
@Transactional
public class SecuritySv extends BaseService{
	
	@Autowired
	private AigaStaffSv aigaStaffSv;
	
	@Autowired
	private RoleSv roleSv;
	
	@Autowired
	private FunctionSv functionSv;

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


	public void initUserSecurity(UserInfo userInfo) {
		List<SysRole> roles = roleSv.findRolesByUserId(userInfo.getStaff().getStaffId());
		userInfo.setRoles(roles);
		
		if(roles != null && roles.size() > 0){
			List<Long> roleIds = new ArrayList<Long>();
			
			for(SysRole role : roles){
				roleIds.add(role.getRoleId());
			}
			
			List<AigaFunction> funs = functionSv.findFunctionsByRoleids(roleIds);
			userInfo.setFuns(funs);
		}
	}
	
	public List<Menu> getMenus(UserInfo userInfo) {
		List<Menu> menus = functionSv.structureMenu(userInfo.getFuns());
		return menus;
	}

	




}
