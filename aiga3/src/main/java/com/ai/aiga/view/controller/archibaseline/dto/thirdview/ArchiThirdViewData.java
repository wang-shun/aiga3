package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiThirdViewData implements Serializable {
	private String name;
	private String idThird;
	private String secName;
	private String firName;
	private String belongLevel;
	private String systemFunction;
	private String department;
	private String projectInfo;
	private String designInfo;
	private String codeName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdThird() {
		return idThird;
	}
	public void setIdThird(String idThird) {
		this.idThird = idThird;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getFirName() {
		return firName;
	}
	public void setFirName(String firName) {
		this.firName = firName;
	}
	public String getBelongLevel() {
		return belongLevel;
	}
	public void setBelongLevel(String belongLevel) {
		this.belongLevel = belongLevel;
	}
	public String getSystemFunction() {
		return systemFunction;
	}
	public void setSystemFunction(String systemFunction) {
		this.systemFunction = systemFunction;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(String projectInfo) {
		this.projectInfo = projectInfo;
	}
	public String getDesignInfo() {
		return designInfo;
	}
	public void setDesignInfo(String designInfo) {
		this.designInfo = designInfo;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
