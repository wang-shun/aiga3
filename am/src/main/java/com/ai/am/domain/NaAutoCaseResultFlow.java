package com.ai.am.domain;
// Generated 2017-3-31 9:24:29 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaAutoCaseResultFlow generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_CASE_RESULT_FLOW"
    ,schema="AIGA"
)
public class NaAutoCaseResultFlow  implements java.io.Serializable {


     private long resultId;
     private Long subTaskId;
     private Long caseId;
     private Byte caseType;
     private Byte caseState;
     private String result;
     private String bug;
     private Long createId;
     private Date doneDate;
     private Byte resulr;
     private String userAutoResult;
     private Long planId;
     private Long collectId;
     private String ext1;
     private String ext2;

    public NaAutoCaseResultFlow() {
    }

	
    public NaAutoCaseResultFlow(long resultId) {
        this.resultId = resultId;
    }
    public NaAutoCaseResultFlow(long resultId, Long subTaskId, Long caseId, Byte caseType, Byte caseState, String result, String bug, Long createId, Date doneDate, Byte resulr, String userAutoResult, Long planId, Long collectId, String ext1, String ext2) {
       this.resultId = resultId;
       this.subTaskId = subTaskId;
       this.caseId = caseId;
       this.caseType = caseType;
       this.caseState = caseState;
       this.result = result;
       this.bug = bug;
       this.createId = createId;
       this.doneDate = doneDate;
       this.resulr = resulr;
       this.userAutoResult = userAutoResult;
       this.planId = planId;
       this.collectId = collectId;
       this.ext1 = ext1;
       this.ext2 = ext2;
    }
   
     @Id 
    
    @Column(name="RESULT_ID", unique=true, nullable=false, precision=14, scale=0)
    public long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="SUB_TASK_ID", precision=14, scale=0)
    public Long getSubTaskId() {
        return this.subTaskId;
    }
    
    public void setSubTaskId(Long subTaskId) {
        this.subTaskId = subTaskId;
    }
    
    @Column(name="CASE_ID", precision=14, scale=0)
    public Long getCaseId() {
        return this.caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    @Column(name="CASE_TYPE", precision=2, scale=0)
    public Byte getCaseType() {
        return this.caseType;
    }
    
    public void setCaseType(Byte caseType) {
        this.caseType = caseType;
    }
    
    @Column(name="CASE_STATE", precision=2, scale=0)
    public Byte getCaseState() {
        return this.caseState;
    }
    
    public void setCaseState(Byte caseState) {
        this.caseState = caseState;
    }
    
    @Column(name="RESULT", length=200)
    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    @Column(name="BUG", length=2000)
    public String getBug() {
        return this.bug;
    }
    
    public void setBug(String bug) {
        this.bug = bug;
    }
    
    @Column(name="CREATE_ID", precision=14, scale=0)
    public Long getCreateId() {
        return this.createId;
    }
    
    public void setCreateId(Long createId) {
        this.createId = createId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=7)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    
    @Column(name="RESULR", precision=2, scale=0)
    public Byte getResulr() {
        return this.resulr;
    }
    
    public void setResulr(Byte resulr) {
        this.resulr = resulr;
    }
    
    @Column(name="USER_AUTO_RESULT", length=200)
    public String getUserAutoResult() {
        return this.userAutoResult;
    }
    
    public void setUserAutoResult(String userAutoResult) {
        this.userAutoResult = userAutoResult;
    }
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="COLLECT_ID", precision=14, scale=0)
    public Long getCollectId() {
        return this.collectId;
    }
    
    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }
    
    @Column(name="EXT1", length=200)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT2", length=200)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }




}


