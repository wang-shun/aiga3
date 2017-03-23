package com.ai.aiga.view.json;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaAutoEnvironmentRequest {
	 private BigDecimal envId;
     private BigDecimal sysId;
     private String envName;
     private String envUrl;
     private String sysAccount;
     private String sysPassword;
     private String database;
     private String dbAccount;
     private String dbPassword;
     private BigDecimal regionId;
     private String soId;
     private String svnUrl;
     private String svnAccount;
     private String svnPassword;
     private BigDecimal envType;
     private BigDecimal runEnv;
     private BigDecimal creatorId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;
	public BigDecimal getEnvId() {
		return envId;
	}
	public void setEnvId(BigDecimal envId) {
		this.envId = envId;
	}
	public BigDecimal getSysId() {
		return sysId;
	}
	public void setSysId(BigDecimal sysId) {
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
	public BigDecimal getRegionId() {
		return regionId;
	}
	public void setRegionId(BigDecimal regionId) {
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
	public BigDecimal getEnvType() {
		return envType;
	}
	public void setEnvType(BigDecimal envType) {
		this.envType = envType;
	}
	public BigDecimal getRunEnv() {
		return runEnv;
	}
	public void setRunEnv(BigDecimal runEnv) {
		this.runEnv = runEnv;
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
		return "NaAutoEnvironmentRequest [envId=" + envId + ", sysId=" + sysId + ", envName=" + envName + ", envUrl="
				+ envUrl + ", sysAccount=" + sysAccount + ", sysPassword=" + sysPassword + ", database=" + database
				+ ", dbAccount=" + dbAccount + ", dbPassword=" + dbPassword + ", regionId=" + regionId + ", soId="
				+ soId + ", svnUrl=" + svnUrl + ", svnAccount=" + svnAccount + ", svnPassword=" + svnPassword
				+ ", envType=" + envType + ", runEnv=" + runEnv + ", creatorId=" + creatorId + ", updateTime="
				+ updateTime + "]";
	}
     
}
