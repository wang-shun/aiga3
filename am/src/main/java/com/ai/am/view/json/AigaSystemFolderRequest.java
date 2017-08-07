package com.ai.am.view.json;


import java.util.Date;

public class AigaSystemFolderRequest {
	private Long sysId;
	private String sysName;
	private Date createTime;
	private Date updateTime;
	private String remarks;
	private String firm;
	private Short importantClass;
	private Short sysOfDomain;
	private Short isInvalid;
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public Short getImportantClass() {
		return importantClass;
	}
	public void setImportantClass(Short importantClass) {
		this.importantClass = importantClass;
	}
	public Short getSysOfDomain() {
		return sysOfDomain;
	}
	public void setSysOfDomain(Short sysOfDomain) {
		this.sysOfDomain = sysOfDomain;
	}
	public Short getIsInvalid() {
		return isInvalid;
	}
	public void setIsInvalid(Short isInvalid) {
		this.isInvalid = isInvalid;
	}
	@Override
	public String toString() {
		return "AigaSystemFolderRequest [sysId=" + sysId + ", sysName=" + sysName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", remarks=" + remarks + ", firm=" + firm + ", importantClass="
				+ importantClass + ", sysOfDomain=" + sysOfDomain + ", isInvalid=" + isInvalid + "]";
	}
	
}
