package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class secSysMessage implements Serializable {
	private int tempId;
	private String domainId;			//系统编号
	private String logogram;			//简称
	private String domainName;			//名称
	private String primaryDomainId;		//所属一级域id
	private String primaryDomainName;	//所属一级域名
	private String hierarchy;			//分层层级
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
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
}
