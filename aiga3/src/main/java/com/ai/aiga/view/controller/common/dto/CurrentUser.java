package com.ai.aiga.view.controller.common.dto;

import java.util.List;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.SysRole;

import lombok.Data;

/**
 * @ClassName: CurrentUser
 * @author: taoyf
 * @date: 2017年5月10日 下午4:44:05
 * @Description:
 * 
 */
@Data
public class CurrentUser {
	
	private AigaStaff staff;
	
	private List<SysRole> roles;

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

}

