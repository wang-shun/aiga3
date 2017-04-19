package com.ai.aiga.domain;
// Generated 2017-4-19 18:18:32 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaOnlineGeneralSteps generated by hbm2java
 */
@Entity
@Table(name="NA_ONLINE_GENERAL_STEPS"
    ,schema="AIGA"
)
public class NaOnlineGeneralSteps  implements java.io.Serializable {


     private Long stepId;
     private Long isInvolve;
     private String onlineSystem;
     private String prerequisite;
     private String generalSteps;
     private String stepDescription;
     private Date preStartTime;
     private Date preDuration;
     private String operator;
     private Date realStartTime;
     private Date realDoneTime;
     private Long planId;
     private String remarks;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaOnlineGeneralSteps() {
    }

	
    public NaOnlineGeneralSteps(Long stepId) {
        this.stepId = stepId;
    }
    public NaOnlineGeneralSteps(Long stepId, Long isInvolve, String onlineSystem, String prerequisite, String generalSteps, String stepDescription, Date preStartTime, Date preDuration, String operator, Date realStartTime, Date realDoneTime, Long planId, String remarks, String ext1, String ext2, String ext3) {
       this.stepId = stepId;
       this.isInvolve = isInvolve;
       this.onlineSystem = onlineSystem;
       this.prerequisite = prerequisite;
       this.generalSteps = generalSteps;
       this.stepDescription = stepDescription;
       this.preStartTime = preStartTime;
       this.preDuration = preDuration;
       this.operator = operator;
       this.realStartTime = realStartTime;
       this.realDoneTime = realDoneTime;
       this.planId = planId;
       this.remarks = remarks;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
    
    @Column(name="STEP_ID", unique=true, nullable=false, precision=14, scale=0)
    public Long getStepId() {
        return this.stepId;
    }
    
    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }
    
    @Column(name="IS_INVOLVE", precision=2, scale=0)
    public Long getIsInvolve() {
        return this.isInvolve;
    }
    
    public void setIsInvolve(Long isInvolve) {
        this.isInvolve = isInvolve;
    }
    
    @Column(name="ONLINE_SYSTEM", length=200)
    public String getOnlineSystem() {
        return this.onlineSystem;
    }
    
    public void setOnlineSystem(String onlineSystem) {
        this.onlineSystem = onlineSystem;
    }
    
    @Column(name="PREREQUISITE", length=400)
    public String getPrerequisite() {
        return this.prerequisite;
    }
    
    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }
    
    @Column(name="GENERAL_STEPS", length=200)
    public String getGeneralSteps() {
        return this.generalSteps;
    }
    
    public void setGeneralSteps(String generalSteps) {
        this.generalSteps = generalSteps;
    }
    
    @Column(name="STEP_DESCRIPTION", length=400)
    public String getStepDescription() {
        return this.stepDescription;
    }
    
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PRE_START_TIME", length=7)
    public Date getPreStartTime() {
        return this.preStartTime;
    }
    
    public void setPreStartTime(Date preStartTime) {
        this.preStartTime = preStartTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PRE_DURATION", length=7)
    public Date getPreDuration() {
        return this.preDuration;
    }
    
    public void setPreDuration(Date preDuration) {
        this.preDuration = preDuration;
    }
    
    @Column(name="OPERATOR", length=200)
    public String getOperator() {
        return this.operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REAL_START_TIME", length=7)
    public Date getRealStartTime() {
        return this.realStartTime;
    }
    
    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REAL_DONE_TIME", length=7)
    public Date getRealDoneTime() {
        return this.realDoneTime;
    }
    
    public void setRealDoneTime(Date realDoneTime) {
        this.realDoneTime = realDoneTime;
    }
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="REMARKS", length=400)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name="EXT_1", length=20)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=20)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=20)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


