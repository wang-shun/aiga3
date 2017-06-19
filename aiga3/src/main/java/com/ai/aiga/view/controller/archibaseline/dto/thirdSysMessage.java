package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class thirdSysMessage implements Serializable {
	private int tempId;
	private String primaryDomainId;		//所属一级域id
	private String primaryDomainName;	//所属一级域名
	private String secondDomainId;		//所属二级域id
	private String secondDomainName;	//所属二级域名
	private String domainId;			//系统编号
	private String logogram;			//简称
	private String domainName;			//名称
	private String functionDescription;	//系统功能描述
	private String rankInfo;			//等级信息
	private String establishmentInfo;	//项目立项信息
	private String responDepartment;	//责任部门
	private String planDesignInfo;		//规划设计信息
	private String sysState;			//系统建设状态
	private String hierarchy;			//分层层级	
	public String getPrimaryDomainId() {
		return primaryDomainId;
	}
	public void setPrimaryDomainId(String primaryDomainId) {
		this.primaryDomainId = primaryDomainId;
	}
	public String getPrimaryDomainName() {
		return primaryDomainName;
	}
	public void setPrimaryDomainName(String primaryDomainName) {
		this.primaryDomainName = primaryDomainName;
	}
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public String getSecondDomainId() {
		return secondDomainId;
	}
	public void setSecondDomainId(String secondDomainId) {
		this.secondDomainId = secondDomainId;
	}
	public String getSecondDomainName() {
		return secondDomainName;
	}
	public void setSecondDomainName(String secondDomainName) {
		this.secondDomainName = secondDomainName;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getLogogram() {
		return logogram;
	}
	public void setLogogram(String logogram) {
		this.logogram = logogram;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getFunctionDescription() {
		return functionDescription;
	}
	public void setFunctionDescription(String functionDescription) {
		this.functionDescription = functionDescription;
	}
	public String getRankInfo() {
		return rankInfo;
	}
	public void setRankInfo(String rankInfo) {
		this.rankInfo = rankInfo;
	}
	public String getEstablishmentInfo() {
		return establishmentInfo;
	}
	public void setEstablishmentInfo(String establishmentInfo) {
		this.establishmentInfo = establishmentInfo;
	}
	public String getResponDepartment() {
		return responDepartment;
	}
	public void setResponDepartment(String responDepartment) {
		this.responDepartment = responDepartment;
	}
	public String getPlanDesignInfo() {
		return planDesignInfo;
	}
	public void setPlanDesignInfo(String planDesignInfo) {
		this.planDesignInfo = planDesignInfo;
	}
	public String getSysState() {
		return sysState;
	}
	public void setSysState(String sysState) {
		this.sysState = sysState;
	}
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}
}
