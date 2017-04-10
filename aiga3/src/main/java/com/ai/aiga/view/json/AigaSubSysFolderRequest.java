package com.ai.aiga.view.json;


import java.util.Date;

public class AigaSubSysFolderRequest {
	private Long subsysId;
	private String sysName;
	private Date createTime;
	private Date updateTime;
	private Long sysId;
	public Long getSubsysId() {
		return subsysId;
	}
	public void setSubsysId(Long subsysId) {
		this.subsysId = subsysId;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	@Override
	public String toString() {
		return "AigaSubSysFolderRequest [subsysId=" + subsysId + ", sysName=" + sysName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", sysId=" + sysId + "]";
	}
	
}
