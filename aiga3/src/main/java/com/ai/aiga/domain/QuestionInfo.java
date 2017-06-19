package com.ai.aiga.domain;
// Generated 2017-6-9 10:57:21 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QuestionInfo generated by hbm2java
 */
@Entity
@Table(name="QUESTION_INFO"
    ,schema="AIGA"
)
public class QuestionInfo  implements java.io.Serializable {


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

    public QuestionInfo() {
    }

	
    public QuestionInfo(long quesId, String quesType, String firstCategory, String secondCategory, String thirdCategory, String diffProblem, String abstracts, String occurEnvironment, String belongProject, long idFirst, long idSecond, long idThird, String sysVersion, String priority, String defectLevel, String state, String requestInfo, String identifiedInfo, String solvedInfo, Date createDate, Date modifyDate, String reportor, String appointedPerson) {
        this.quesId = quesId;
        this.quesType = quesType;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.diffProblem = diffProblem;
        this.abstracts = abstracts;
        this.occurEnvironment = occurEnvironment;
        this.belongProject = belongProject;
        this.idFirst = idFirst;
        this.idSecond = idSecond;
        this.idThird = idThird;
        this.sysVersion = sysVersion;
        this.priority = priority;
        this.defectLevel = defectLevel;
        this.state = state;
        this.requestInfo = requestInfo;
        this.identifiedInfo = identifiedInfo;
        this.solvedInfo = solvedInfo;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.reportor = reportor;
        this.appointedPerson = appointedPerson;
    }
    public QuestionInfo(long quesId, String quesType, String firstCategory, String secondCategory, String thirdCategory, String diffProblem, String abstracts, String occurEnvironment, String belongProject, long idFirst, long idSecond, long idThird, String sysVersion, String priority, String defectLevel, String state, String requestInfo, String identifiedInfo, String solvedInfo, Date createDate, Date modifyDate, String reportor, String appointedPerson, String ext1, String ext2, String ext3) {
       this.quesId = quesId;
       this.quesType = quesType;
       this.firstCategory = firstCategory;
       this.secondCategory = secondCategory;
       this.thirdCategory = thirdCategory;
       this.diffProblem = diffProblem;
       this.abstracts = abstracts;
       this.occurEnvironment = occurEnvironment;
       this.belongProject = belongProject;
       this.idFirst = idFirst;
       this.idSecond = idSecond;
       this.idThird = idThird;
       this.sysVersion = sysVersion;
       this.priority = priority;
       this.defectLevel = defectLevel;
       this.state = state;
       this.requestInfo = requestInfo;
       this.identifiedInfo = identifiedInfo;
       this.solvedInfo = solvedInfo;
       this.createDate = createDate;
       this.modifyDate = modifyDate;
       this.reportor = reportor;
       this.appointedPerson = appointedPerson;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
    
    @Column(name="QUES_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getQuesId() {
        return this.quesId;
    }
    
    public void setQuesId(long quesId) {
        this.quesId = quesId;
    }
    
    @Column(name="QUES_TYPE", nullable=false, length=20)
    public String getQuesType() {
        return this.quesType;
    }
    
    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }
    
    @Column(name="FIRST_CATEGORY", nullable=false)
    public String getFirstCategory() {
        return this.firstCategory;
    }
    
    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }
    
    @Column(name="SECOND_CATEGORY", nullable=false)
    public String getSecondCategory() {
        return this.secondCategory;
    }
    
    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }
    
    @Column(name="THIRD_CATEGORY", nullable=false)
    public String getThirdCategory() {
        return this.thirdCategory;
    }
    
    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory;
    }
    
    @Column(name="DIFF_PROBLEM", nullable=false)
    public String getDiffProblem() {
        return this.diffProblem;
    }
    
    public void setDiffProblem(String diffProblem) {
        this.diffProblem = diffProblem;
    }
    
    @Column(name="ABSTRACTS", nullable=false, length=1000)
    public String getAbstracts() {
        return this.abstracts;
    }
    
    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }
    
    @Column(name="OCCUR_ENVIRONMENT", nullable=false, length=500)
    public String getOccurEnvironment() {
        return this.occurEnvironment;
    }
    
    public void setOccurEnvironment(String occurEnvironment) {
        this.occurEnvironment = occurEnvironment;
    }
    
    @Column(name="BELONG_PROJECT", nullable=false, length=100)
    public String getBelongProject() {
        return this.belongProject;
    }
    
    public void setBelongProject(String belongProject) {
        this.belongProject = belongProject;
    }
    
    @Column(name="ID_FIRST", unique=true, nullable=false, precision=10, scale=0)
    public long getIdFirst() {
        return this.idFirst;
    }
    
    public void setIdFirst(long idFirst) {
        this.idFirst = idFirst;
    }
    
    @Column(name="ID_SECOND", unique=true, nullable=false, precision=10, scale=0)
    public long getIdSecond() {
        return this.idSecond;
    }
    
    public void setIdSecond(long idSecond) {
        this.idSecond = idSecond;
    }
    
    @Column(name="ID_THIRD", unique=true, nullable=false, precision=10, scale=0)
    public long getIdThird() {
        return this.idThird;
    }
    
    public void setIdThird(long idThird) {
        this.idThird = idThird;
    }
    
    @Column(name="SYS_VERSION", nullable=false, length=20)
    public String getSysVersion() {
        return this.sysVersion;
    }
    
    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }
    
    @Column(name="PRIORITY", nullable=false, length=20)
    public String getPriority() {
        return this.priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    @Column(name="DEFECT_LEVEL", nullable=false, length=20)
    public String getDefectLevel() {
        return this.defectLevel;
    }
    
    public void setDefectLevel(String defectLevel) {
        this.defectLevel = defectLevel;
    }
    
    @Column(name="STATE", nullable=false, length=20)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="REQUEST_INFO", nullable=false, length=500)
    public String getRequestInfo() {
        return this.requestInfo;
    }
    
    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }
    
    @Column(name="IDENTIFIED_INFO", nullable=false, length=500)
    public String getIdentifiedInfo() {
        return this.identifiedInfo;
    }
    
    public void setIdentifiedInfo(String identifiedInfo) {
        this.identifiedInfo = identifiedInfo;
    }
    
    @Column(name="SOLVED_INFO", nullable=false, length=500)
    public String getSolvedInfo() {
        return this.solvedInfo;
    }
    
    public void setSolvedInfo(String solvedInfo) {
        this.solvedInfo = solvedInfo;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", nullable=false, length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFY_DATE", nullable=false, length=7)
    public Date getModifyDate() {
        return this.modifyDate;
    }
    
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    @Column(name="REPORTOR", nullable=false, length=20)
    public String getReportor() {
        return this.reportor;
    }
    
    public void setReportor(String reportor) {
        this.reportor = reportor;
    }
    
    @Column(name="APPOINTED_PERSON", nullable=false, length=20)
    public String getAppointedPerson() {
        return this.appointedPerson;
    }
    
    public void setAppointedPerson(String appointedPerson) {
        this.appointedPerson = appointedPerson;
    }
    
    @Column(name="EXT_1")
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2")
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3")
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }


}


