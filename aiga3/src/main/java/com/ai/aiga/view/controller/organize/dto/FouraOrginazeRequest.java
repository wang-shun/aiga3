package com.ai.aiga.view.controller.organize.dto;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @author Danny
 *
 */
@Data
public class FouraOrginazeRequest {
	
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
    private String orgAddress;
    private String contactName;
    private Integer contactCardType;
    private String contactCardId;
    private Integer postcode;
    private String contactBillId;
    private Long postProvince;
    private Long postCity;
    private String postAddress;
    private Integer postPostcod;
    private String notes;
    private Integer state;
    private Long doneCode;
    private Date createDate;
    private Date doneDate;
    private Date validDate;
    private Date expireDate;
    private Long opId;
    private Long orgId;
    private String oldCode;
    private String countyId;
    private String oldParentCode;
    private String ext1;
    private String ext2;
    private String ext3;
//    private String sLeaf;

}

