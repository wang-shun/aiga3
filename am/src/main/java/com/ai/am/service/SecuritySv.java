package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.domain.AigaFunction;
import com.ai.am.domain.AigaStaff;
import com.ai.am.domain.SysRole;
import com.ai.am.security.shiro.UserInfo;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.staff.StaffSv;
import com.ai.am.view.controller.common.dto.Menu;

@Service
@Transactional
public class SecuritySv extends BaseService{
	
	@Autowired
	private StaffSv aigaStaffSv;
	
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
