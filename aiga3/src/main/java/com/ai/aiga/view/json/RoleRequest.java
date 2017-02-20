package com.ai.aiga.view.json;

public class RoleRequest {
	
	private Long roleId;
	private String code;
	private String name;
	private String notes;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return "RoleRequest [roleId=" + roleId + ", code=" + code + ", name=" + name + ", notes=" + notes + "]";
	}
	

}
