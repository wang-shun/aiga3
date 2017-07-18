package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchitectureThirdRequest implements Serializable {
	private long onlysysId;
    private long idThird;
    private String name;
    private String systemCode;
    private String systemFunction;
    private String description;
    private String code;
    private long idSecond;
    private String belongLevel;
    private String department;
    private String projectInfo;
    private String designInfo;
    private String state;
    private long applyId;
    private String applyUser;
    private Date createDate;
    private Date modifyDate;
    private String identifiedInfo;
    private String fileInfo;
    private String ext1;
    private String ext2;
    private String ext3;
    private String sysState;
    private String rankInfo;
	public long getOnlysysId() {
		return onlysysId;
	}
	public void setOnlysysId(long onlysysId) {
		this.onlysysId = onlysysId;
	}
	public String getSysState() {
		return sysState;
	}
	public void setSysState(String sysState) {
		this.sysState = sysState;
	}
	public String getRankInfo() {
		return rankInfo;
	}
	public void setRankInfo(String rankInfo) {
		this.rankInfo = rankInfo;
	}
	public long getIdThird() {
		return idThird;
	}
	public void setIdThird(long idThird) {
		this.idThird = idThird;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getSystemFunction() {
		return systemFunction;
	}
	public void setSystemFunction(String systemFunction) {
		this.systemFunction = systemFunction;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getIdSecond() {
		return idSecond;
	}
	public void setIdSecond(long idSecond) {
		this.idSecond = idSecond;
	}
	public String getBelongLevel() {
		return belongLevel;
	}
	public void setBelongLevel(String belongLevel) {
		this.belongLevel = belongLevel;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getApplyId() {
		return applyId;
	}
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getIdentifiedInfo() {
		return identifiedInfo;
	}
	public void setIdentifiedInfo(String identifiedInfo) {
		this.identifiedInfo = identifiedInfo;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	@Override
	public String toString() {
		return "ArchitectureThirdRequest [idThird=" + idThird + ", name="
				+ name + ", systemCode=" + systemCode + ", systemFunction="
				+ systemFunction + ", description=" + description + ", code="
				+ code + ", idSecond=" + idSecond + ", belongLevel="
				+ belongLevel + ", department=" + department + ", projectInfo="
				+ projectInfo + ", designInfo=" + designInfo + ", state="
				+ state + ", applyId=" + applyId + ", applyUser=" + applyUser
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", identifiedInfo=" + identifiedInfo + ", fileInfo="
				+ fileInfo + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3="
				+ ext3 + "]";
	}
    
    
}
