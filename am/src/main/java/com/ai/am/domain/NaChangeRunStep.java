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
 * NaChangeRunStep generated by hbm2java
 */
@Entity
@Table(name="NA_CHANGE_RUN_STEP"
)
public class NaChangeRunStep  implements java.io.Serializable {


     private Long id;
     private String isConfigArg;
     private String isRestartHost;
     private String isRestartCollection;
     private String isChangeHardware;
     private String isRestartDb;
     private String isRestartApplication;
     private String runStep;
     private String runPerson;
     private String startRunTime;
     private String stopRunTime;
     private String operationScript;
     private Long planId;
     private String fileName;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaChangeRunStep() {
    }

	
    public NaChangeRunStep(Long id) {
        this.id = id;
    }
    public NaChangeRunStep(Long id, String isConfigArg, String isRestartHost, String isRestartCollection, String isChangeHardware, String isRestartDb, String isRestartApplication, String runStep, String runPerson, String startRunTime, String stopRunTime, String operationScript, Long planId, String fileName, String ext1, String ext2, String ext3) {
       this.id = id;
       this.isConfigArg = isConfigArg;
       this.isRestartHost = isRestartHost;
       this.isRestartCollection = isRestartCollection;
       this.isChangeHardware = isChangeHardware;
       this.isRestartDb = isRestartDb;
       this.isRestartApplication = isRestartApplication;
       this.runStep = runStep;
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
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_CHANGE_RUN_STEP$SEQ")
     @SequenceGenerator(name="NA_CHANGE_RUN_STEP$SEQ",sequenceName="NA_CHANGE_RUN_STEP$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="IS_CONFIG_ARG", length=400)
    public String getIsConfigArg() {
        return this.isConfigArg;
    }
    
    public void setIsConfigArg(String isConfigArg) {
        this.isConfigArg = isConfigArg;
    }
    
    @Column(name="IS_RESTART_HOST", length=400)
    public String getIsRestartHost() {
        return this.isRestartHost;
    }
    
    public void setIsRestartHost(String isRestartHost) {
        this.isRestartHost = isRestartHost;
    }
    
    @Column(name="IS_RESTART_COLLECTION", length=400)
    public String getIsRestartCollection() {
        return this.isRestartCollection;
    }
    
    public void setIsRestartCollection(String isRestartCollection) {
        this.isRestartCollection = isRestartCollection;
    }
    
    @Column(name="IS_CHANGE_HARDWARE", length=400)
    public String getIsChangeHardware() {
        return this.isChangeHardware;
    }
    
    public void setIsChangeHardware(String isChangeHardware) {
        this.isChangeHardware = isChangeHardware;
    }
    
    @Column(name="IS_RESTART_DB", length=400)
    public String getIsRestartDb() {
        return this.isRestartDb;
    }
    
    public void setIsRestartDb(String isRestartDb) {
        this.isRestartDb = isRestartDb;
    }
    
    @Column(name="IS_RESTART_APPLICATION", length=400)
    public String getIsRestartApplication() {
        return this.isRestartApplication;
    }
    
    public void setIsRestartApplication(String isRestartApplication) {
        this.isRestartApplication = isRestartApplication;
    }
    
    @Column(name="RUN_STEP", length=400)
    public String getRunStep() {
        return this.runStep;
    }
    
    public void setRunStep(String runStep) {
        this.runStep = runStep;
    }
    
    @Column(name="RUN_PERSON", length=400)
    public String getRunPerson() {
        return this.runPerson;
    }
    
    public void setRunPerson(String runPerson) {
        this.runPerson = runPerson;
    }
    
    @Column(name="START_RUN_TIME", length=400)
    public String getStartRunTime() {
        return this.startRunTime;
    }
    
    public void setStartRunTime(String startRunTime) {
        this.startRunTime = startRunTime;
    }
    
    @Column(name="STOP_RUN_TIME", length=400)
    public String getStopRunTime() {
        return this.stopRunTime;
    }
    
    public void setStopRunTime(String stopRunTime) {
        this.stopRunTime = stopRunTime;
    }
    
    @Column(name="OPERATION_SCRIPT", length=400)
    public String getOperationScript() {
        return this.operationScript;
    }
    
    public void setOperationScript(String operationScript) {
        this.operationScript = operationScript;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="FILE_NAME", length=400)
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="EXT_1", length=400)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=400)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=400)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


