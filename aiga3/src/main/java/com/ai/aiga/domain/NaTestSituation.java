package com.ai.aiga.domain;
// Generated 2017-4-13 14:18:43 by Hibernate Tools 3.2.2.GA


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


     private Long testId;
     private String sysName;
     private String subSysName;
     private String testSituation;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaTestSituation() {
    }

	
    public NaTestSituation(Long testId) {
        this.testId = testId;
    }
    public NaTestSituation(Long testId, String sysName, String subSysName, String testSituation, String ext1, String ext2, String ext3) {
       this.testId = testId;
       this.sysName = sysName;
       this.subSysName = subSysName;
       this.testSituation = testSituation;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
    
    @Column(name="TEST_ID", unique=true, nullable=false, precision=12, scale=0)
    public Long getTestId() {
        return this.testId;
    }
    
    public void setTestId(Long testId) {
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




}


