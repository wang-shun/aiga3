package com.ai.aiga.domain;
// Generated 2017-4-24 15:00:18 by Hibernate Tools 3.2.2.GA



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaChangeOperationManager generated by hbm2java
 */
@Entity
@Table(name="NA_CHANGE_OPERATION_MANAGER"
)
public class NaChangeOperationManager  implements java.io.Serializable {


     private Long id;
     private String isAddMonitor;
     private String runPerson;
     private String startRunTime;
     private String stopRunTime;
     private String operationScript;
     private Long planId;
     private String fileName;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaChangeOperationManager() {
    }

	
    public NaChangeOperationManager(Long id) {
        this.id = id;
    }
    public NaChangeOperationManager(Long id, String isAddMonitor, String runPerson, String startRunTime, String stopRunTime, String operationScript, Long planId, String fileName, String ext1, String ext2, String ext3) {
       this.id = id;
       this.isAddMonitor = isAddMonitor;
       this.runPerson = runPerson;
       this.startRunTime = startRunTime;
       this.stopRunTime = stopRunTime;
       this.operationScript = operationScript;
       this.planId = planId;
       this.fileName = fileName;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_CHANGE_OPERATION$SEQ")
     @SequenceGenerator(name="NA_CHANGE_OPERATION$SEQ",sequenceName="NA_CHANGE_OPERATION$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="IS_ADD_MONITOR", length=200)
    public String getIsAddMonitor() {
        return this.isAddMonitor;
    }
    
    public void setIsAddMonitor(String isAddMonitor) {
        this.isAddMonitor = isAddMonitor;
    }
    
    @Column(name="RUN_PERSON", length=200)
    public String getRunPerson() {
        return this.runPerson;
    }
    
    public void setRunPerson(String runPerson) {
        this.runPerson = runPerson;
    }
    
    @Column(name="START_RUN_TIME", length=200)
    public String getstartRunTime() {
        return this.startRunTime;
    }
    
    public void setstartRunTime(String startRunTime) {
        this.startRunTime = startRunTime;
    }
    
    @Column(name="STOP_RUN_TIME", length=200)
    public String getStopRunTime() {
        return this.stopRunTime;
    }
    
    public void setStopRunTime(String stopRunTime) {
        this.stopRunTime = stopRunTime;
    }
    
    @Column(name="OPERATION_SCRIPT", length=200)
    public String getoperationScript() {
        return this.operationScript;
    }
    
    public void setoperationScript(String operationScript) {
        this.operationScript = operationScript;
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


