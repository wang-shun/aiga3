package com.ai.am.domain;
// Generated 2017-3-20 16:19:14 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaAutoRunPlanCase generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_RUN_PLAN_CASE"
)
public class NaAutoRunPlanCase  implements java.io.Serializable {


     private Long relaId;
     private Long planId;
     private Long autoId;
     private Long groupId;
     private Long sortNumber;
     private Long sortGroup;
     private Long environmentType;
     private Long collectId;

    public NaAutoRunPlanCase() {
    }

	
    public NaAutoRunPlanCase(Long relaId) {
        this.relaId = relaId;
    }
    public NaAutoRunPlanCase(Long relaId, Long planId, Long autoId, Long groupId, Long sortNumber, Long sortGroup, Long environmentType, Long collectId) {
       this.relaId = relaId;
       this.planId = planId;
       this.autoId = autoId;
       this.groupId = groupId;
       this.sortNumber = sortNumber;
       this.sortGroup = sortGroup;
       this.environmentType = environmentType;
       this.collectId = collectId;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_RUN_PLAN_CASE$SEQ")
     @SequenceGenerator(name="NA_AUTO_RUN_PLAN_CASE$SEQ",sequenceName="NA_AUTO_RUN_PLAN_CASE$SEQ",allocationSize=1)
    @Column(name="RELA_ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getRelaId() {
        return this.relaId;
    }
    
    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="AUTO_ID", precision=22, scale=0)
    public Long getAutoId() {
        return this.autoId;
    }
    
    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }
    
    @Column(name="GROUP_ID", precision=22, scale=0)
    public Long getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    @Column(name="SORT_NUMBER", precision=22, scale=0)
    public Long getSortNumber() {
        return this.sortNumber;
    }
    
    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }
    
    @Column(name="SORT_GROUP", precision=22, scale=0)
    public Long getSortGroup() {
        return this.sortGroup;
    }
    
    public void setSortGroup(Long sortGroup) {
        this.sortGroup = sortGroup;
    }
    
    @Column(name="ENVIRONMENT_TYPE", precision=22, scale=0)
    public Long getEnvironmentType() {
        return this.environmentType;
    }
    
    public void setEnvironmentType(Long environmentType) {
        this.environmentType = environmentType;
    }
    
    @Column(name="COLLECT_ID", precision=22, scale=0)
    public Long getCollectId() {
        return this.collectId;
    }
    
    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }




}

