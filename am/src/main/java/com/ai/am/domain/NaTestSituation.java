package com.ai.am.domain;
// Generated 2017-4-17 19:14:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NaTestSituation generated by hbm2java
 */
@Entity
@Table(name="NA_TEST_SITUATION"
    ,schema="AIGA"
)
public class NaTestSituation  implements java.io.Serializable {


     private long testId;
     private String sysName;
     private String subSysName;
     private String testSituation;
     private String ext1;
     private String ext2;
     private String ext3;
     private Long planId;
     private String ext4;
     private String ext5;

    public NaTestSituation() {
    }

	
    public NaTestSituation(long testId) {
        this.testId = testId;
    }
    public NaTestSituation(long testId, String sysName, String subSysName, String testSituation, String ext1, String ext2, String ext3, Long planId, String ext4, String ext5) {
       this.testId = testId;
       this.sysName = sysName;
       this.subSysName = subSysName;
       this.testSituation = testSituation;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.planId = planId;
       this.ext4 = ext4;
       this.ext5 = ext5;
    }
   
     @Id 
    
    @Column(name="TEST_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getTestId() {
        return this.testId;
    }
    
    public void setTestId(long testId) {
        this.testId = testId;
    }
    
    @Column(name="SYS_NAME", length=200)
    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    @Column(name="SUB_SYS_NAME", length=200)
    public String getSubSysName() {
        return this.subSysName;
    }
    
    public void setSubSysName(String subSysName) {
        this.subSysName = subSysName;
    }
    
    @Column(name="TEST_SITUATION", length=2000)
    public String getTestSituation() {
        return this.testSituation;
    }
    
    public void setTestSituation(String testSituation) {
        this.testSituation = testSituation;
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
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="EXT_4", length=20)
    public String getExt4() {
        return this.ext4;
    }
    
    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }
    
    @Column(name="EXT_5", length=20)
    public String getExt5() {
        return this.ext5;
    }
    
    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }




}


