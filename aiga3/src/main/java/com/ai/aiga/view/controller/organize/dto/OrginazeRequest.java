package com.ai.aiga.view.controller.organize.dto;

import lombok.Data;

/**
 * @ClassName: OrginazeRequest
 * @author: taoyf
 * @date: 2017年4月20日 下午4:46:59
 * @Description:
 * 
 */
@Data
public class OrginazeRequest {
	
	private Long organizeId;
	private Long parentOrganizeId;
	private String organizeName;
	private String code;
	private Long orgRoleTypeId;
	private String districtId;
	private String shortName;
	private String englishName;
	private Integer memberNum;
	private String managerName;
	private String email;
	private String phoneId;
	private String faxId;
	private String connectName;
	private Integer connectCardType;
	private String connectCardId;
	private String connectBillId;
	private String isLeaf;

}

