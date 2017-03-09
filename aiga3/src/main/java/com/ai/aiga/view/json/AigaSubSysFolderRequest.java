package com.ai.aiga.view.json;

import java.math.BigDecimal;
import java.util.Date;

public class AigaSubSysFolderRequest {
	private BigDecimal subsysId;
	private String sysName;
	private Date createTime;
	private Date updateTime;
	private BigDecimal sysId;
	public BigDecimal getSubsysId() {
		return subsysId;
	}
	public void setSubsysId(BigDecimal subsysId) {
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
	public BigDecimal getSysId() {
		return sysId;
	}
	public void setSysId(BigDecimal sysId) {
		this.sysId = sysId;
	}
	@Override
	public String toString() {
		return "AigaSubSysFolderRequest [subsysId=" + subsysId + ", sysName=" + sysName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", sysId=" + sysId + "]";
	}
	
}
