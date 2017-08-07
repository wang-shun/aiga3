package com.ai.am.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchitectureFirstRequest implements Serializable {

    private long idFirst;
    private String name;
    private String shortName;
    private String description;
    private String code;
    private String belongLevel;
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
	public long getIdFirst() {
		return idFirst;
	}
	public void setIdFirst(long idFirst) {
		this.idFirst = idFirst;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	public String getBelongLevel() {
		return belongLevel;
	}
	public void setBelongLevel(String belongLevel) {
		this.belongLevel = belongLevel;
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
		return "ArchitectureFirstRequest [idFirst=" + idFirst + ", name="
				+ name + ", shortName=" + shortName + ", description="
				+ description + ", code=" + code + ", belongLevel="
				+ belongLevel + ", state=" + state + ", applyId=" + applyId
				+ ", applyUser=" + applyUser + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", identifiedInfo="
				+ identifiedInfo + ", fileInfo=" + fileInfo + ", ext1=" + ext1
				+ ", ext2=" + ext2 + ", ext3=" + ext3 + "]";
	}
    
    
}
