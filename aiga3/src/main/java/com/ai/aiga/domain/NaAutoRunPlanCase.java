package com.ai.aiga.domain;
// Generated 2017-3-17 18:53:45 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="NA_AUTO_RUN_PLAN_CASE"
)
public class NaAutoRunPlanCase  implements java.io.Serializable {


     private Long relaId;
     private Long planId;
     private Long autoId;
     private Long groupId;
     private Long collectId;
     private Long sortNumber;
     private Long sortGroup;
     private Long environmentType;


    public NaAutoRunPlanCase() {
    }

    
    
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_RUN_PLAN_CASE$SEQ")
    @SequenceGenerator(name="NA_AUTO_RUN_PLAN_CASE$SEQ",sequenceName="NA_AUTO_RUN_PLAN_CASE$SEQ",allocationSize=1)
   @Column(name="relaId" , unique=true, nullable=false, precision=22, scale=0)
	public Long getRelaId() {
		return relaId;
	}


	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	  @Column(name="Plan_id", length=40)
	public Long getPlanId() {
		return planId;
	}


	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	  @Column(name="Auto_id", length=40)
	public Long getAutoId() {
		return autoId;
	}


	public void setAutoId(Long autoId) {
		this.autoId = autoId;
	}

	  @Column(name="Group_id" ,length=40)
	public Long getGroupId() {
		return groupId;
	}


	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	  @Column(name="Collect_id", length=40)
	public Long getCollectId() {
		return collectId;
	}


	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}


	  @Column(name="Sort_number", length=40)
	public Long getSortNumber() {
		return sortNumber;
	}


	public void setSortNumber(Long sortNumber) {
		this.sortNumber = sortNumber;
	}


	  @Column(name="Sort_group", length=40)
	public Long getSortGroup() {
		return sortGroup;
	}


	public void setSortGroup(Long sortGroup) {
		this.sortGroup = sortGroup;
	}


	  @Column(name="Environment_type", length=40)
	public Long getEnvironmentType() {
		return environmentType;
	}


	public void setEnvironmentType(Long environmentType) {
		this.environmentType = environmentType;
	}

}


