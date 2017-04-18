package com.ai.aiga.domain;
// Generated 2017-4-17 17:04:55 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaDbScriptExecutionProcess generated by hbm2java
 */
@Entity
@Table(name="NA_DB_SCRIPT_EXECUTION_PROCESS"
    ,schema="AIGA"
)
public class NaDbScriptExecutionProcess  implements java.io.Serializable {


     private Long id;
     private String batch;
     private Date startTime;
     private Date finishTime;
     private String historyTime;
     private String remark;
     private Long planId;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaDbScriptExecutionProcess() {
    }

	
    public NaDbScriptExecutionProcess(Long id) {
        this.id = id;
    }
    public NaDbScriptExecutionProcess(Long id, String batch, Date startTime, Date finishTime, String historyTime, String remark, Long planId, String ext1, String ext2, String ext3) {
       this.id = id;
       this.batch = batch;
       this.startTime = startTime;
       this.finishTime = finishTime;
       this.historyTime = historyTime;
       this.remark = remark;
       this.planId = planId;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_DB_SCRIPT_EXECUTION_PRO$SEQ")
     @SequenceGenerator(name="NA_DB_SCRIPT_EXECUTION_PRO$SEQ",sequenceName="NA_DB_SCRIPT_EXECUTION_PRO$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=14, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="BATCH", length=200)
    public String getBatch() {
        return this.batch;
    }
    
    public void setBatch(String batch) {
        this.batch = batch;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_TIME", length=7)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FINISH_TIME", length=7)
    public Date getFinishTime() {
        return this.finishTime;
    }
    
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    
    @Column(name="HISTORY_TIME", length=2000)
    public String getHistoryTime() {
        return this.historyTime;
    }
    
    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }
    
    @Column(name="REMARK", length=2000)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
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

