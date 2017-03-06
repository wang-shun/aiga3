package com.ai.aiga.view.json;

public class StaffOrgRelatResponse {
	private Long organizeId;
	private String organizeName;
	private Character isAdminStaff;
    private Character isBaseOrg;
	public Long getOrganizeId() {
		return organizeId;
	}
	public void setOrganizeId(Long organizeId) {
		this.organizeId = organizeId;
	}
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	public Character getIsAdminStaff() {
		return isAdminStaff;
	}
	public void setIsAdminStaff(Character isAdminStaff) {
		this.isAdminStaff = isAdminStaff;
	}
	public Character getIsBaseOrg() {
		return isBaseOrg;
	}
	public void setIsBaseOrg(Character isBaseOrg) {
		this.isBaseOrg = isBaseOrg;
	}
	
}
