package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class QuestionInfoRequest implements Serializable {

    private long quesId;
    private String quesType;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String diffProblem;
    private String abstracts;
    private String occurEnvironment;
    private String belongProject;
    private long idFirst;
    private long idSecond;
    private long idThird;
    private String sysVersion;
    private String priority;
    private String defectLevel;
    private String state;
    private String requestInfo;
    private String identifiedInfo;
    private String solvedInfo;
    private Date createDate;
    private Date modifyDate;
    private String reportor;
    private String appointedPerson;
    private String ext1;
    private String ext2;
    private String ext3;
	public long getQuesId() {
		return quesId;
	}
	public void setQuesId(long quesId) {
		this.quesId = quesId;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getFirstCategory() {
		return firstCategory;
	}
	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}
	public String getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}
	public String getThirdCategory() {
		return thirdCategory;
	}
	public void setThirdCategory(String thirdCategory) {
		this.thirdCategory = thirdCategory;
	}
	public String getDiffProblem() {
		return diffProblem;
	}
	public void setDiffProblem(String diffProblem) {
		this.diffProblem = diffProblem;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getOccurEnvironment() {
		return occurEnvironment;
	}
	public void setOccurEnvironment(String occurEnvironment) {
		this.occurEnvironment = occurEnvironment;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public long getIdFirst() {
		return idFirst;
	}
	public void setIdFirst(long idFirst) {
		this.idFirst = idFirst;
	}
	public long getIdSecond() {
		return idSecond;
	}
	public void setIdSecond(long idSecond) {
		this.idSecond = idSecond;
	}
	public long getIdThird() {
		return idThird;
	}
	public void setIdThird(long idThird) {
		this.idThird = idThird;
	}
	public String getSysVersion() {
		return sysVersion;
	}
	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDefectLevel() {
		return defectLevel;
	}
	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRequestInfo() {
		return requestInfo;
	}
	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}
	public String getIdentifiedInfo() {
		return identifiedInfo;
	}
	public void setIdentifiedInfo(String identifiedInfo) {
		this.identifiedInfo = identifiedInfo;
	}
	public String getSolvedInfo() {
		return solvedInfo;
	}
	public void setSolvedInfo(String solvedInfo) {
		this.solvedInfo = solvedInfo;
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
	public String getReportor() {
		return reportor;
	}
	public void setReportor(String reportor) {
		this.reportor = reportor;
	}
	public String getAppointedPerson() {
		return appointedPerson;
	}
	public void setAppointedPerson(String appointedPerson) {
		this.appointedPerson = appointedPerson;
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
		return "QuestionInfoRequest [quesId=" + quesId + ", quesType="
				+ quesType + ", firstCategory=" + firstCategory
				+ ", secondCategory=" + secondCategory + ", thirdCategory="
				+ thirdCategory + ", diffProblem=" + diffProblem
				+ ", abstracts=" + abstracts + ", occurEnvironment="
				+ occurEnvironment + ", belongProject=" + belongProject
				+ ", idFirst=" + idFirst + ", idSecond=" + idSecond
				+ ", idThird=" + idThird + ", sysVersion=" + sysVersion
				+ ", priority=" + priority + ", defectLevel=" + defectLevel
				+ ", state=" + state + ", requestInfo=" + requestInfo
				+ ", identifiedInfo=" + identifiedInfo + ", solvedInfo="
				+ solvedInfo + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", reportor=" + reportor + ", appointedPerson="
				+ appointedPerson + ", ext1=" + ext1 + ", ext2=" + ext2
				+ ", ext3=" + ext3 + "]";
	}
	
    
}
