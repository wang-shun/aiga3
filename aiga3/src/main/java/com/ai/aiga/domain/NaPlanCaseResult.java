package com.ai.aiga.domain;
// Generated 2017-4-6 9:59:23 by Hibernate Tools 3.2.2.GA


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
 * NaPlanCaseResult generated by hbm2java
 */
@Entity
@Table(name="NA_PLAN_CASE_RESULT"
    ,schema="AIGA"
)
public class NaPlanCaseResult  implements java.io.Serializable {


     private Long resultId;
     private Long subTaskId;
     private Long caseId;
     private Byte caseType;
     private Byte caseState;
     private String result;
     private String bug;
     private Long operatId;
     private Date doneDate;
     private Byte autoResult;
     private String userAutoResult;
     private String ext1;
     private String ext2;
     private String ext3;
     private String autoCode;
     private String remarks;
    public NaPlanCaseResult() {
    }

	
    public NaPlanCaseResult(long resultId) {
        this.resultId = resultId;
    }
   
   
    public NaPlanCaseResult(Long resultId, Long subTaskId, Long caseId, Byte caseType, Byte caseState, String result,
			String bug, Long operatId, Date doneDate, Byte autoResult, String userAutoResult, String ext1, String ext2,
			String ext3, String autoCode, String remarks) {
		this.resultId = resultId;
		this.subTaskId = subTaskId;
		this.caseId = caseId;
		this.caseType = caseType;
		this.caseState = caseState;
		this.result = result;
		this.bug = bug;
		this.operatId = operatId;
		this.doneDate = doneDate;
		this.autoResult = autoResult;
		this.userAutoResult = userAutoResult;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
		this.autoCode = autoCode;
		this.remarks = remarks;
	}


	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_PLAN_CASE_RESULT$SEQ")
    @SequenceGenerator(name="NA_PLAN_CASE_RESULT$SEQ",sequenceName="NA_PLAN_CASE_RESULT$SEQ",allocationSize=1)
    @Column(name="RESULT_ID", unique=true, nullable=false, precision=14, scale=0)
    public Long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(Long resultId) {
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
    
    @Column(name="OPERAT_ID", precision=14, scale=0)
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
    
    @Column(name="AUTO_RESULT", precision=2, scale=0)
    public Byte getAutoResult() {
        return this.autoResult;
    }
    
    public void setAutoResult(Byte autoResult) {
        this.autoResult = autoResult;
    }
    
    @Column(name="USER_AUTO_RESULT", length=200)
    public String getUserAutoResult() {
        return this.userAutoResult;
    }
    
    public void setUserAutoResult(String userAutoResult) {
        this.userAutoResult = userAutoResult;
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
    
    @Column(name="EXT3", length=200)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    @Column(name="AUTOCODE", length=200)
	public String getAutoCode() {
		return autoCode;
	}


	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}

	@Column(name="REMARKS", length=200)
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    


}


