package com.ai.aiga.service.staff.dto;

import lombok.Data;

/**
 * @ClassName: StaffOrgRelation
 * @author: taoyf
 * @date: 2017年4月20日 下午3:33:17
 * @Description:
 * 
 */
@Data
public class StaffOrgRelation {
	
	private Long organizeId;
	private String organizeName;
	private Character isAdminStaff;
    private Character isBaseOrg;

}

