package com.ai.aiga.view.controller.role.dto;

import java.util.Date;
import lombok.Data;
@Data
public class FouraRoleRequest {
	
    private Long roleId;
    private String code;
    private String name;
    private String notes;
    private Byte state;
    private Long doneCode;
    private Date createDate;
    private Date doneDate;
    private Date validDate;
    private Date expireDate;
    private Long opId;
    private Long orgId;
    
}
