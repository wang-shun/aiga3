package com.ai.am.domain;
// Generated 2017-3-21 10:20:29 by Hibernate Tools 3.2.2.GA


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
 * NaAutoTaskReportDetail generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_TASK_REPORT_DETAIL"
    ,schema="AIGA"
)
public class NaAutoTaskReportDetail  implements java.io.Serializable {


     private long detailId;
     private Long reportId;
     private Long taskId;
     private Long autoId;
     private Long resultId;
     private Character isSuccess;
     private Character  isBug;
     private String failReason;
     private String bugStaff;
     private Long creatorId;
     private Date updateTime;

    public NaAutoTaskReportDetail() {
    }

	
    public NaAutoTaskReportDetail(long detailId) {
        this.detailId = detailId;
    }
    public NaAutoTaskReportDetail(long detailId, Long reportId, Long taskId, Long autoId, Long resultId, Character isSuccess, Character isBug, String failReason, String bugStaff, Long creatorId, Date updateTime) {
       this.detailId = detailId;
       this.reportId = reportId;
       this.taskId = taskId;
       this.autoId = autoId;
       this.resultId = resultId;
       this.isSuccess = isSuccess;
       this.isBug = isBug;
       this.failReason = failReason;
       this.bugStaff = bugStaff;
       this.creatorId = creatorId;
       this.updateTime = updateTime;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_TASK_REPORT_DETAIL$SEQ")
    @SequenceGenerator(name="NA_AUTO_TASK_REPORT_DETAIL$SEQ",sequenceName="NA_AUTO_TASK_REPORT_DETAIL$SEQ",allocationSize=1)
    @Column(name="DETAIL_ID", unique=true, nullable=false, precision=14, scale=0)
    public long getDetailId() {
        return this.detailId;
    }
    
    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }
    
    @Column(name="REPORT_ID", precision=14, scale=0)
    public Long getReportId() {
        return this.reportId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    @Column(name="TASK_ID", precision=14, scale=0)
    public Long getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    @Column(name="AUTO_ID", precision=14, scale=0)
    public Long getAutoId() {
        return this.autoId;
    }
    
    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }
    
    @Column(name="RESULT_ID", precision=14, scale=0)
    public Long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="IS_SUCCESS", length = 1)
    public Character getIsSuccess() {
        return this.isSuccess;
    }
    
    public void setIsSuccess(Character isSuccess) {
        this.isSuccess = isSuccess;
    }
    
    @Column(name="IS_BUG", length = 1)
    public Character getIsBug() {
        return this.isBug;
    }
    
    public void setIsBug(Character isBug) {
        this.isBug = isBug;
    }
    
    @Column(name="FAIL_REASON", length=2000)
    public String getFailReason() {
        return this.failReason;
    }
    
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
    
    @Column(name="BUG_STAFF", length=40)
    public String getBugStaff() {
        return this.bugStaff;
    }
    
    public void setBugStaff(String bugStaff) {
        this.bugStaff = bugStaff;
    }
    
    @Column(name="CREATOR_ID", precision=14, scale=0)
    public Long getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TIME", length=7)
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }




}


