package com.ai.aiga.view.controller.organize.dto;

import java.util.Date;
import lombok.Data;
/**
 * 
 * @author Danny
 *
 */
@Data
public class FouraStaffOrgRelatRequest {
	
    private long staffOrgRelatId;
    private Long organizeId;
    private Long staffId;
    private Character isAdminStaff;
    private Character isBaseOrg;
    private String notes;
    private Long doneCode;
    private Date createDate;
    private Date doneDate;
    private Date validDate;
    private Date expireDate;
    private Long opId;
    private Long orgId;

}

