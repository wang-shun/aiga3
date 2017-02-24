package com.ai.aiga.view.json;

public class StaffOrgRelatRequest {
	private Long organizeId;
    private Long staffId;
    private Character isAdminStaff;
    private Character isBaseOrg;
    private String organizeName; 
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
	
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffOrgRelatRequest [organizeId=").append(organizeId).append(", staffId=").append(staffId)
				.append(", isAdminStaff=").append(isAdminStaff).append(", isBaseOrg=").append(isBaseOrg)
				.append(", organizeName=").append(organizeName).append("]");
		return builder.toString();
	}
	
	
	
     
}
