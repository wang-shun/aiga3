package com.ai.aiga.view.json;

public class StationRequest {
	
	private Long stationTypeId;
	private Long roleId;
	private String isAuthor;
	private String isRealOperation;
	private String stationTypeName;
	private String notes;
	
	
	public Long getStationTypeId() {
		return stationTypeId;
	}
	public void setStationTypeId(Long stationTypeId) {
		this.stationTypeId = stationTypeId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getIsAuthor() {
		return isAuthor;
	}
	public void setIsAuthor(String isAuthor) {
		this.isAuthor = isAuthor;
	}
	public String getIsRealOperation() {
		return isRealOperation;
	}
	public void setIsRealOperation(String isRealOperation) {
		this.isRealOperation = isRealOperation;
	}
	public String getStationTypeName() {
		return stationTypeName;
	}
	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	

}
