package com.ai.aiga.view.json;

import java.sql.Clob;
import java.util.Date;

public class NaUiComponentRequest {
	private Long compId;
	private Long parentId;
	private String compName;
	private Long creatorId;
	private Date createTime;
	private Long updateId;
	private String compDesc;
	//private Clob compScript;
	
	public Long getCompId() {
		return compId;
	}
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public String getCompDesc() {
		return compDesc;
	}
	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}
//	public Clob getCompScript() {
//		return compScript;
//	}
//	public void setCompScript(Clob compScript) {
//		this.compScript = compScript;
//	}
	
		
	
}
