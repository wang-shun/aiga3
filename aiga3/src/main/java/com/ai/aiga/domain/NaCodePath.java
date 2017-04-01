package com.ai.aiga.domain;
// Generated 2017-3-30 20:51:25 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * NaCodePath generated by hbm2java
 */
@Entity
@Table(name="NA_CODE_PATH")
public class NaCodePath  implements java.io.Serializable {


     private Long id;
     private String listId;
     private String sysName;
     private String modelName;
     private String packageName;
     private Long state;
     private String remark;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date planDate;
     private Long isFinished;
     private Long updateCount;
     private Long result;

    public NaCodePath() {
    }

	
    public NaCodePath(Long id) {
        this.id = id;
    }
    public NaCodePath(Long id, String listId, String sysName, String modelName, String packageName, Long state, String remark, Date planDate, Long isFinished, Long updateCount, Long result) {
       this.id = id;
       this.listId = listId;
       this.sysName = sysName;
       this.modelName = modelName;
       this.packageName = packageName;
       this.state = state;
       this.remark = remark;
       this.planDate = planDate;
       this.isFinished = isFinished;
       this.updateCount = updateCount;
       this.result = result;
    }
   
     @Id 
    
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="LIST_ID", length=100)
    public String getListId() {
        return this.listId;
    }
    
    public void setListId(String listId) {
        this.listId = listId;
    }
    
    @Column(name="SYS_NAME", length=2000)
    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    @Column(name="MODEL_NAME", length=2000)
    public String getModelName() {
        return this.modelName;
    }
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    @Column(name="PACKAGE_NAME", length=2000)
    public String getPackageName() {
        return this.packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    @Column(name="STATE", precision=22, scale=0)
    public Long getState() {
        return this.state;
    }
    
    public void setState(Long state) {
        this.state = state;
    }
    
    @Column(name="REMARK", length=4000)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="PLAN_DATE")
    public Date getPlanDate() {
        return this.planDate;
    }
    
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
    
    @Column(name="IS_FINISHED", precision=22, scale=0)
    public Long getIsFinished() {
        return this.isFinished;
    }
    
    public void setIsFinished(Long isFinished) {
        this.isFinished = isFinished;
    }
    
    @Column(name="UPDATE_COUNT", precision=22, scale=0)
    public Long getUpdateCount() {
        return this.updateCount;
    }
    
    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }
    
    @Column(name="RESULT", precision=20, scale=0)
    public Long getResult() {
        return this.result;
    }
    
    public void setResult(Long result) {
        this.result = result;
    }




}


