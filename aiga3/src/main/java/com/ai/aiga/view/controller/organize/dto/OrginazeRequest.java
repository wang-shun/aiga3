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
	private String contactName;
	private Integer contactCardType;
	private String contactCardId;
	private String contactBillId;
	private String sLeaf;
	public Long getOrganizeId() {
		return organizeId;
	}
	public void setOrganizeId(Long organizeId) {
		this.organizeId = organizeId;
	}
	public Long getParentOrganizeId() {
		return parentOrganizeId;
	}
	public void setParentOrganizeId(Long parentOrganizeId) {
		this.parentOrganizeId = parentOrganizeId;
	}
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getOrgRoleTypeId() {
		return orgRoleTypeId;
	}
	public void setOrgRoleTypeId(Long orgRoleTypeId) {
		this.orgRoleTypeId = orgRoleTypeId;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public Integer getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getFaxId() {
		return faxId;
	}
	public void setFaxId(String faxId) {
		this.faxId = faxId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Integer getContactCardType() {
		return contactCardType;
	}
	public void setContactCardType(Integer contactCardType) {
		this.contactCardType = contactCardType;
	}
	public String getContactCardId() {
		return contactCardId;
	}
	public void setContactCardId(String contactCardId) {
		this.contactCardId = contactCardId;
	}
	public String getContactBillId() {
		return contactBillId;
	}
	public void setContactBillId(String contactBillId) {
		this.contactBillId = contactBillId;
	}
	public String getsLeaf() {
		return sLeaf;
	}
	public void setsLeaf(String sLeaf) {
		this.sLeaf = sLeaf;
	}

}

