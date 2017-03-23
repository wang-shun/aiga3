package com.ai.aiga.view.json;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaAutoMachineRequest {
	 private BigDecimal machineId;
     private String machineIp;
     private String machineName;
     private BigDecimal status;
     private String machineAccount;
     private String machinePassword;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date requestTime;
     private BigDecimal taskId;
	public BigDecimal getMachineId() {
		return machineId;
	}
	public void setMachineId(BigDecimal machineId) {
		this.machineId = machineId;
	}
	public String getMachineIp() {
		return machineIp;
	}
	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	public String getMachineAccount() {
		return machineAccount;
	}
	public void setMachineAccount(String machineAccount) {
		this.machineAccount = machineAccount;
	}
	public String getMachinePassword() {
		return machinePassword;
	}
	public void setMachinePassword(String machinePassword) {
		this.machinePassword = machinePassword;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public BigDecimal getTaskId() {
		return taskId;
	}
	public void setTaskId(BigDecimal taskId) {
		this.taskId = taskId;
	}
	@Override
	public String toString() {
		return "NaAutoMachineRequest [machineId=" + machineId + ", machineIp=" + machineIp + ", machineName="
				+ machineName + ", status=" + status + ", machineAccount=" + machineAccount + ", machinePassword="
				+ machinePassword + ", requestTime=" + requestTime + ", taskId=" + taskId + "]";
	}
     
}
