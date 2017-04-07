package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.SysRoleDao;
import com.ai.aiga.dao.SysRoleStationtypeDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.domain.SysRoleStationtype;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.role.dto.RoleRequest;

@Service
@Transactional
public class RoleSv extends BaseService{
	
	@Autowired
	private SysRoleDao sysRoleDao;
	

	public List<SysRole> findRoles() {
		return sysRoleDao.findAll();
	}
   
public Object list(int pageNumber, int pageSize ,SysRole condition ) throws ParseException {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return sysRoleDao.search(cons, pageable);
	}
   
	
	
	public void saveRole(RoleRequest roleRequest) {
		if(roleRequest == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		/*if(StringUtils.isBlank(roleRequest.getRoleId().toString())){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"roleId");
		}*/
		if(StringUtils.isBlank(roleRequest.getCode())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		
		if(StringUtils.isBlank(roleRequest.getName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "name");
		}
		
		
		SysRole sysRole = new SysRole();
		//sysRole.setRoleId(roleRequest.getRoleId());
		sysRole.setCode(roleRequest.getCode());
		sysRole.setName(roleRequest.getName());
		sysRole.setNotes(roleRequest.getNotes());
		
		/*--------*/
		sysRole.setCreateDate(new Date(System.currentTimeMillis()));
		
		sysRoleDao.save(sysRole);
	}
	
	public void updateRole(RoleRequest roleRequest) {
		
		if(roleRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		
		if(roleRequest.getRoleId() == null || roleRequest.getRoleId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
		}
		
		SysRole sysRole = sysRoleDao.findOne(roleRequest.getRoleId());
		
		if(sysRole == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "roleId");
		}
		
		if(sysRole != null){
			
			if(StringUtils.isNotBlank(roleRequest.getCode())){
				sysRole.setCode(roleRequest.getCode());
			}
			
			if(StringUtils.isNotBlank(roleRequest.getName())){
				sysRole.setName(roleRequest.getName());
			}
			
			if(StringUtils.isNotBlank(roleRequest.getNotes())){
				sysRole.setNotes(roleRequest.getNotes());
			}
			
			/*--------*/
			sysRole.setDoneDate(new Date(System.currentTimeMillis()));
			
			sysRoleDao.save(sysRole);
		}
	}

	public void deleteRole(Long roleId) {
		
		if(roleId == null || roleId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
		}
		
		sysRoleDao.delete(roleId);
	}
	public SysRole findOne(Long roleId) {
		if(roleId == null || roleId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
		}
		
		return sysRoleDao.findOne(roleId);
	}

	public List<SysRole> findRolesByUserId(Long staffId) {
		return sysRoleDao.findRolesByUserId(staffId);
	}
	

}
