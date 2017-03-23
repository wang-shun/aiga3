package com.ai.aiga.view.json;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaAutoMachineEnvRequest {
	 private BigDecimal relaId;
     private BigDecimal machineId;
     private BigDecimal envId;
     private BigDecimal creatorId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;
	public BigDecimal getRelaId() {
		return relaId;
	}
	public void setRelaId(BigDecimal relaId) {
		this.relaId = relaId;
	}
	public BigDecimal getMachineId() {
		return machineId;
	}
	public void setMachineId(BigDecimal machineId) {
		this.machineId = machineId;
	}
	public BigDecimal getEnvId() {
		return envId;
	}
	public void setEnvId(BigDecimal envId) {
		this.envId = envId;
	}
	public BigDecimal getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(BigDecimal creatorId) {
		this.creatorId = creatorId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "NaAutoMachineEnvRequest [relaId=" + relaId + ", machineId=" + machineId + ", envId=" + envId
				+ ", creatorId=" + creatorId + ", updateTime=" + updateTime + "]";
	}
     
}
