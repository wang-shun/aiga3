package com.ai.am.domain;
// Generated 2017-4-13 15:56:17 by Hibernate Tools 3.2.2.GA



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaGroupRequireList generated by hbm2java
 */
@Entity
@Table(name="NA_GROUP_REQUIRE_LIST"
)
public class NaGroupRequireList  implements java.io.Serializable {


     private Long id;
     private String devTaskId;
     private String requireName;
     private String testMan;
     private String requireMan;
     private String ext1;
     private String ext2;
     private String ext3;
     private Long planId;

    public NaGroupRequireList() {
    }

	
    public NaGroupRequireList(Long id) {
        this.id = id;
    }
    public NaGroupRequireList(Long id, String devTaskId, String requireName, String testMan, String requireMan, String ext1, String ext2, String ext3, Long planId) {
       this.id = id;
       this.devTaskId = devTaskId;
       this.requireName = requireName;
       this.testMan = testMan;
       this.requireMan = requireMan;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.planId = planId;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_GROUP_REQUIRE_LIST$SEQ")
     @SequenceGenerator(name="NA_GROUP_REQUIRE_LIST$SEQ",sequenceName="NA_GROUP_REQUIRE_LIST$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="DEV_TASK_ID", length=200)
    public String getDevTaskId() {
        return this.devTaskId;
    }
    
    public void setDevTaskId(String devTaskId) {
        this.devTaskId = devTaskId;
    }
    
    @Column(name="REQUIRE_NAME", length=2000)
    public String getRequireName() {
        return this.requireName;
    }
    
    public void setRequireName(String requireName) {
        this.requireName = requireName;
    }
    
    @Column(name="TEST_MAN", length=200)
    public String getTestMan() {
        return this.testMan;
    }
    
    public void setTestMan(String testMan) {
        this.testMan = testMan;
    }
    
    @Column(name="REQUIRE_MAN", length=200)
    public String getRequireMan() {
        return this.requireMan;
    }
    
    public void setRequireMan(String requireMan) {
        this.requireMan = requireMan;
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
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }




}

