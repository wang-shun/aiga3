package com.ai.aiga.domain;
// Generated 2017-4-10 14:38:12 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaPlanCaseResultExpSum generated by hbm2java
 */
@Entity
@Table(name="NA_PLAN_CASE_RESULT_EXP_SUM"
)
public class NaPlanCaseResultExpSum  implements java.io.Serializable {


     private Long resultId;
     private Long subTaskId;
     private Long interId;
     private String interCode;
     private Long totalTime;
     private String runResult;
     private Long caseType;
     private Long caseState;
     private Long operatId;
     private Date doneDate;
     private String ext1;
     private String ext2;
     private String ext3;
     private String afprodData;
     private String isMatching;
     private String bfprodData;

    public NaPlanCaseResultExpSum() {
    }

	
    public NaPlanCaseResultExpSum(Long resultId) {
        this.resultId = resultId;
    }
    public NaPlanCaseResultExpSum(Long resultId, Long subTaskId, Long interId, String interCode, Long totalTime, String runResult, Long caseType, Long caseState, Long operatId, Date doneDate, String ext1, String ext2, String ext3, String afprodData, String isMatching, String bfprodData) {
       this.resultId = resultId;
       this.subTaskId = subTaskId;
       this.interId = interId;
       this.interCode = interCode;
       this.totalTime = totalTime;
       this.runResult = runResult;
       this.caseType = caseType;
       this.caseState = caseState;
       this.operatId = operatId;
       this.doneDate = doneDate;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.afprodData = afprodData;
       this.isMatching = isMatching;
       this.bfprodData = bfprodData;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_PLAN_CASE_RESULT_EXP_S$SEQ")
     @SequenceGenerator(name="NA_PLAN_CASE_RESULT_EXP_S$SEQ",sequenceName="NA_PLAN_CASE_RESULT_EXP_S$SEQ",allocationSize=1)
    @Column(name="RESULT_ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="SUB_TASK_ID", precision=22, scale=0)
    public Long getSubTaskId() {
        return this.subTaskId;
    }
    
    public void setSubTaskId(Long subTaskId) {
        this.subTaskId = subTaskId;
    }
    
    @Column(name="INTER_ID", precision=22, scale=0)
    public Long getInterId() {
        return this.interId;
    }
    
    public void setInterId(Long interId) {
        this.interId = interId;
    }
    
    @Column(name="INTER_CODE", length=100)
    public String getInterCode() {
        return this.interCode;
    }
    
    public void setInterCode(String interCode) {
        this.interCode = interCode;
    }
    
    @Column(name="TOTAL_TIME", precision=22, scale=0)
    public Long getTotalTime() {
        return this.totalTime;
    }
    
    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
    
    @Column(name="RUN_RESULT", length=50)
    public String getRunResult() {
        return this.runResult;
    }
    
    public void setRunResult(String runResult) {
        this.runResult = runResult;
    }
    
    @Column(name="CASE_TYPE", precision=1, scale=0)
    public Long getCaseType() {
        return this.caseType;
    }
    
    public void setCaseType(Long caseType) {
        this.caseType = caseType;
    }
    
    @Column(name="CASE_STATE", precision=1, scale=0)
    public Long getCaseState() {
        return this.caseState;
    }
    
    public void setCaseState(Long caseState) {
        this.caseState = caseState;
    }
    
    @Column(name="OPERAT_ID", precision=12, scale=0)
    public Long getOperatId() {
        return this.operatId;
    }
    
    public void setOperatId(Long operatId) {
        this.operatId = operatId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=7)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    
    @Column(name="EXT1", length=500)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT2", length=500)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT3", length=500)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
    
    @Column(name="AFPROD_DATA", length=100)
    public String getAfprodData() {
        return this.afprodData;
    }
    
    public void setAfprodData(String afprodData) {
        this.afprodData = afprodData;
    }
    
    @Column(name="IS_MATCHING", length=100)
    public String getIsMatching() {
        return this.isMatching;
    }
    
    public void setIsMatching(String isMatching) {
        this.isMatching = isMatching;
    }
    
    @Column(name="BFPROD_DATA", length=100)
    public String getBfprodData() {
        return this.bfprodData;
    }
    
    public void setBfprodData(String bfprodData) {
        this.bfprodData = bfprodData;
    }




}


