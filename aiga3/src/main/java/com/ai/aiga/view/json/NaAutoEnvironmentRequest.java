package com.ai.aiga.view.json;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaAutoEnvironmentRequest {
	 private Long envId;
     private Long sysId;
     private String envName;
     private String envUrl;
     private String sysAccount;
     private String sysPassword;
     private String database;
     private String dbAccount;
     private String dbPassword;
     private Long regionId;
     private String soId;
     private String svnUrl;
     private String svnAccount;
     private String svnPassword;
     private Long envType;
     private Long runEnv;
     private Long creatorId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;
	public Long getEnvId() {
		return envId;
	}
	public void setEnvId(Long envId) {
		this.envId = envId;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getEnvUrl() {
		return envUrl;
	}
	public void setEnvUrl(String envUrl) {
		this.envUrl = envUrl;
	}
	public String getSysAccount() {
		return sysAccount;
	}
	public void setSysAccount(String sysAccount) {
		this.sysAccount = sysAccount;
	}
	public String getSysPassword() {
		return sysPassword;
	}
	public void setSysPassword(String sysPassword) {
		this.sysPassword = sysPassword;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getDbAccount() {
		return dbAccount;
	}
	public void setDbAccount(String dbAccount) {
		this.dbAccount = dbAccount;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSvnUrl() {
		return svnUrl;
	}
	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}
	public String getSvnAccount() {
		return svnAccount;
	}
	public void setSvnAccount(String svnAccount) {
		this.svnAccount = svnAccount;
	}
	public String getSvnPassword() {
		return svnPassword;
	}
	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}
	public Long getEnvType() {
		return envType;
	}
	public void setEnvType(Long envType) {
		this.envType = envType;
	}
	public Long getRunEnv() {
		return runEnv;
	}
	public void setRunEnv(Long runEnv) {
		this.runEnv = runEnv;
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
		return "NaAutoEnvironmentRequest [envId=" + envId + ", sysId=" + sysId + ", envName=" + envName + ", envUrl="
				+ envUrl + ", sysAccount=" + sysAccount + ", sysPassword=" + sysPassword + ", database=" + database
				+ ", dbAccount=" + dbAccount + ", dbPassword=" + dbPassword + ", regionId=" + regionId + ", soId="
				+ soId + ", svnUrl=" + svnUrl + ", svnAccount=" + svnAccount + ", svnPassword=" + svnPassword
				+ ", envType=" + envType + ", runEnv=" + runEnv + ", creatorId=" + creatorId + ", updateTime="
				+ updateTime + "]";
	}
     
}
