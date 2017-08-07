package com.ai.am.view.controller.auto.dto;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaAutoMachineEnvRequest {
	 private Long relaId;
     private Long machineId;
     private Long envId;
     private Long creatorId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;
	public Long getRelaId() {
		return relaId;
	}
	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public Long getEnvId() {
		return envId;
	}
	public void setEnvId(Long envId) {
		this.envId = envId;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
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
