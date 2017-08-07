package com.ai.am.domain;
// Generated 2017-4-25 17:21:00 by Hibernate Tools 3.2.2.GA


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AigaReleaseReport generated by hbm2java
 */
@Entity
@Table(name="AIGA_RELEASE_REPORT"
    ,schema="AIGA"
)
public class AigaReleaseReport  implements java.io.Serializable {


     private long reportId;
     private BigDecimal onlinePlanId;
     private String onlinePlanName;
     private BigDecimal taskId;
     private String taskName;
     private Date releaseDate;
     private Serializable applicationName;
     private Date startTime;
     private Date finishTime;
     private Serializable duration;

    public AigaReleaseReport() {
    }

	
    public AigaReleaseReport(long reportId) {
        this.reportId = reportId;
    }
    public AigaReleaseReport(long reportId, BigDecimal onlinePlanId, String onlinePlanName, BigDecimal taskId, String taskName, Date releaseDate, Serializable applicationName, Date startTime, Date finishTime, Serializable duration) {
       this.reportId = reportId;
       this.onlinePlanId = onlinePlanId;
       this.onlinePlanName = onlinePlanName;
       this.taskId = taskId;
       this.taskName = taskName;
       this.releaseDate = releaseDate;
       this.applicationName = applicationName;
       this.startTime = startTime;
       this.finishTime = finishTime;
       this.duration = duration;
    }
   
     @Id 
    
    @Column(name="REPORT_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getReportId() {
        return this.reportId;
    }
    
    public void setReportId(long reportId) {
        this.reportId = reportId;
    }
    
    @Column(name="ONLINE_PLAN_ID", precision=22, scale=0)
    public BigDecimal getOnlinePlanId() {
        return this.onlinePlanId;
    }
    
    public void setOnlinePlanId(BigDecimal onlinePlanId) {
        this.onlinePlanId = onlinePlanId;
    }
    
    @Column(name="ONLINE_PLAN_NAME", length=200)
    public String getOnlinePlanName() {
        return this.onlinePlanName;
    }
    
    public void setOnlinePlanName(String onlinePlanName) {
        this.onlinePlanName = onlinePlanName;
    }
    
    @Column(name="TASK_ID", precision=22, scale=0)
    public BigDecimal getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(BigDecimal taskId) {
        this.taskId = taskId;
    }
    
    @Column(name="TASK_NAME", length=200)
    public String getTaskName() {
        return this.taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RELEASE_DATE", length=7)
    public Date getReleaseDate() {
        return this.releaseDate;
    }
    
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    @Column(name="APPLICATION_NAME")
    public Serializable getApplicationName() {
        return this.applicationName;
    }
    
    public void setApplicationName(Serializable applicationName) {
        this.applicationName = applicationName;
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
    
    @Column(name="DURATION")
    public Serializable getDuration() {
        return this.duration;
    }
    
    public void setDuration(Serializable duration) {
        this.duration = duration;
    }




}


