package com.ai.aiga.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.SysRoleDao;
import com.ai.aiga.dao.SysRoleStationtypeDao;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.domain.SysRoleStationtype;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.RoleRequest;

@Service
@Transactional
public class RoleSv extends BaseService{
	
	@Autowired
	private SysRoleDao sysRoleDao;
	

	public List<SysRole> findRoles() {
		return sysRoleDao.findAll();
	}

	public void saveRole(RoleRequest roleRequest) {
		SysRole sysRole = new SysRole();
		sysRole.setCode(roleRequest.getCode());
		sysRole.setName(roleRequest.getName());
		sysRole.setNotes(roleRequest.getNotes());
		
		/*--------*/
		sysRole.setCreateDate(new Date(System.currentTimeMillis()));
		
		sysRoleDao.save(sysRole);
	}
	
	public void updateRole(RoleRequest roleRequest) {
		
		SysRole sysRole = sysRoleDao.findOne(roleRequest.getRoleId());
		
		if(sysRole != null){
			sysRole.setCode(roleRequest.getCode());
			sysRole.setName(roleRequest.getName());
			sysRole.setNotes(roleRequest.getNotes());
			
			/*--------*/
			sysRole.setDoneDate(new Date(System.currentTimeMillis()));
		}
		
		sysRoleDao.save(sysRole);
	}

	public void deleteRole(Long roleId) {
		sysRoleDao.delete(roleId);
	}

	

}
