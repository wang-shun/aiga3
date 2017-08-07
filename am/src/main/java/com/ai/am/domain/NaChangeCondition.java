package com.ai.am.domain;
// Generated 2017-4-24 15:00:18 by Hibernate Tools 3.2.2.GA



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaChangeCondition generated by hbm2java
 */
@Entity
@Table(name="NA_CHANGE_CONDITION"
)
public class NaChangeCondition  implements java.io.Serializable {


     private Long id;
     private String changeTitle;
     private String changeGroup;
     private String changeObjectType;
     private String changeMethodType;
     private String changeReason;
     private Long planId;
     private String fileName;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaChangeCondition() {
    }

	
    public NaChangeCondition(Long id) {
        this.id = id;
    }
    public NaChangeCondition(Long id, String changeTitle, String changeGroup, String changeObjectType, String changeMethodType, String changeReason, Long planId, String fileName, String ext1, String ext2, String ext3) {
       this.id = id;
       this.changeTitle = changeTitle;
       this.changeGroup = changeGroup;
       this.changeObjectType = changeObjectType;
       this.changeMethodType = changeMethodType;
       this.changeReason = changeReason;
       this.planId = planId;
       this.fileName = fileName;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_CHANGE_CONDITION$SEQ")
 	@SequenceGenerator(name = "NA_CHANGE_CONDITION$SEQ", sequenceName = "NA_CHANGE_CONDITION$SEQ", allocationSize = 1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="CHANGE_TITLE", length=200)
    public String getChangeTitle() {
        return this.changeTitle;
    }
    
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }
    
    @Column(name="CHANGE_GROUP", length=200)
    public String getChangeGroup() {
        return this.changeGroup;
    }
    
    public void setChangeGroup(String changeGroup) {
        this.changeGroup = changeGroup;
    }
    
    @Column(name="CHANGE_OBJECT_TYPE", length=200)
    public String getChangeObjectType() {
        return this.changeObjectType;
    }
    
    public void setChangeObjectType(String changeObjectType) {
        this.changeObjectType = changeObjectType;
    }
    
    @Column(name="CHANGE_METHOD_TYPE", length=200)
    public String getChangeMethodType() {
        return this.changeMethodType;
    }
    
    public void setChangeMethodType(String changeMethodType) {
        this.changeMethodType = changeMethodType;
    }
    
    @Column(name="CHANGE_REASON", length=200)
    public String getChangeReason() {
        return this.changeReason;
    }
    
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="FILE_NAME", length=200)
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="EXT_1", length=200)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=200)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=200)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


