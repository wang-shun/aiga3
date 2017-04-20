package com.ai.aiga.view.controller.staff.dto;

public class StaffOrgRelatRequest {
	private Long organizeId;
    private Long staffId;
    private Character isAdminStaff;
    private Character isBaseOrg;
	public Long getOrganizeId() {
		return organizeId;
	}
	public void setOrganizeId(Long organizeId) {
		this.organizeId = organizeId;
	}
	public Long getStaffId() {
		return staffId;
	} 
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffOrgRelatRequest [organizeId=").append(organizeId).append(", staffId=").append(staffId)
				.append(", isAdminStaff=").append(isAdminStaff).append(", isBaseOrg=").append(isBaseOrg).append("]");
		return builder.toString();
	}


	
     
}
