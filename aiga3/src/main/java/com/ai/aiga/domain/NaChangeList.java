package com.ai.aiga.domain;
// Generated 2017-3-30 20:46:11 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaChangeList generated by hbm2java
 */
@Entity
@Table(name="NA_CHANGE_LIST"
    ,schema="AIGA"
)
public class NaChangeList  implements java.io.Serializable {


     private BigDecimal changeId;
     private String changeName;
     private String changeManager;
     private String changeMan;
     private String changeTitle;
     private BigDecimal planId;
     private String reviewState;
     private String resultState;
     private String ext1;
     private String ext2;

    public NaChangeList() {
    }

	
    public NaChangeList(BigDecimal changeId) {
        this.changeId = changeId;
    }
    public NaChangeList(BigDecimal changeId, String changeName, String changeManager, String changeMan, String changeTitle, BigDecimal planId, String reviewState, String resultState, String ext1, String ext2) {
       this.changeId = changeId;
       this.changeName = changeName;
       this.changeManager = changeManager;
       this.changeMan = changeMan;
       this.changeTitle = changeTitle;
       this.planId = planId;
       this.reviewState = reviewState;
       this.resultState = resultState;
       this.ext1 = ext1;
       this.ext2 = ext2;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_CHANGE_LIST$SEQ")
     @SequenceGenerator(name="NA_CHANGE_LIST$SEQ",sequenceName="NA_CHANGE_LIST$SEQ",allocationSize=1)
    @Column(name="CHANGE_ID", unique=true, nullable=false, precision=22, scale=0)
    public BigDecimal getChangeId() {
        return this.changeId;
    }
    
    public void setChangeId(BigDecimal changeId) {
        this.changeId = changeId;
    }
    
    @Column(name="CHANGE_NAME", length=200)
    public String getChangeName() {
        return this.changeName;
    }
    
    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }
    
    @Column(name="CHANGE_MANAGER", length=2000)
    public String getChangeManager() {
        return this.changeManager;
    }
    
    public void setChangeManager(String changeManager) {
        this.changeManager = changeManager;
    }
    
    @Column(name="CHANGE_MAN", length=2000)
    public String getChangeMan() {
        return this.changeMan;
    }
    
    public void setChangeMan(String changeMan) {
        this.changeMan = changeMan;
    }
    
    @Column(name="CHANGE_TITLE", length=2000)
    public String getChangeTitle() {
        return this.changeTitle;
    }
    
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public BigDecimal getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(BigDecimal planId) {
        this.planId = planId;
    }
    
    @Column(name="REVIEW_STATE", length=4000)
    public String getReviewState() {
        return this.reviewState;
    }
    
    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }
    
    @Column(name="RESULT_STATE", length=4000)
    public String getResultState() {
        return this.resultState;
    }
    
    public void setResultState(String resultState) {
        this.resultState = resultState;
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




}


