package com.ai.am.domain;
// Generated 2017-4-11 11:48:41 by Hibernate Tools 3.2.2.GA


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
 * PlanDetailManifest generated by hbm2java
 */
@Entity
@Table(name="PLAN_DETAIL_MANIFEST"
)
public class PlanDetailManifest  implements java.io.Serializable {


     private long manifestId;
     private long planId;
     private String qaProgress;
     private String qaSituation;
     private String qaStaff;
     private String qaManager;
     private Integer qaBugNumber;
     private Date reqDate;
     private String reqNo;
     private String reqName;
     private String reqOrigin;
     private String reqLevel;
     private String reqPriority;
     private String reqCategory;
     private String reqType;
     private Date reqUpdate;
     private String reqReason;
     private Date reqFirstDate;
     private Date reqLastDate;
     private Date reqOnlineDate;
     private String devNo;
     private String devName;
     private String devStatus;
     private String reqImportant;
     private String sysName;
     private String subSysName;
     private String qaTeam;
     private String devStaff;
     private String devManager;
     private String reqManager;
     private String reqStaff;
     private String remarks;
     private Integer devWorkload;
     private String devAnalyse;
     private String reqSkippingCycle;
     private String qaCrossSystem;
     private String reqGroup;
     private String devDataScript;
     private String reqRollback;
     private String reqRollbackMode;
     private String reqRbCostTime;
     private Long creatorId;
     private Date createTime;
     private String reqGroupFocus;
     private String reqUpdateType;

    public PlanDetailManifest() {
    }

	
    public PlanDetailManifest(long manifestId, long planId) {
        this.manifestId = manifestId;
        this.planId = planId;
    }
    public PlanDetailManifest(long manifestId, long planId, String qaProgress, String qaSituation, String qaStaff, String qaManager, Integer qaBugNumber, Date reqDate, String reqNo, String reqName, String reqOrigin, String reqLevel, String reqPriority, String reqCategory, String reqType, Date reqUpdate, String reqReason, Date reqFirstDate, Date reqLastDate, Date reqOnlineDate, String devNo, String devName, String devStatus, String reqImportant, String sysName, String subSysName, String qaTeam, String devStaff, String devManager, String reqManager, String reqStaff, String remarks, Integer devWorkload, String devAnalyse, String reqSkippingCycle, String qaCrossSystem, String reqGroup, String devDataScript, String reqRollback, String reqRollbackMode, String reqRbCostTime, Long creatorId, Date createTime, String reqGroupFocus, String reqUpdateType) {
       this.manifestId = manifestId;
       this.planId = planId;
       this.qaProgress = qaProgress;
       this.qaSituation = qaSituation;
       this.qaStaff = qaStaff;
       this.qaManager = qaManager;
       this.qaBugNumber = qaBugNumber;
       this.reqDate = reqDate;
       this.reqNo = reqNo;
       this.reqName = reqName;
       this.reqOrigin = reqOrigin;
       this.reqLevel = reqLevel;
       this.reqPriority = reqPriority;
       this.reqCategory = reqCategory;
       this.reqType = reqType;
       this.reqUpdate = reqUpdate;
       this.reqReason = reqReason;
       this.reqFirstDate = reqFirstDate;
       this.reqLastDate = reqLastDate;
       this.reqOnlineDate = reqOnlineDate;
       this.devNo = devNo;
       this.devName = devName;
       this.devStatus = devStatus;
       this.reqImportant = reqImportant;
       this.sysName = sysName;
       this.subSysName = subSysName;
       this.qaTeam = qaTeam;
       this.devStaff = devStaff;
       this.devManager = devManager;
       this.reqManager = reqManager;
       this.reqStaff = reqStaff;
       this.remarks = remarks;
       this.devWorkload = devWorkload;
       this.devAnalyse = devAnalyse;
       this.reqSkippingCycle = reqSkippingCycle;
       this.qaCrossSystem = qaCrossSystem;
       this.reqGroup = reqGroup;
       this.devDataScript = devDataScript;
       this.reqRollback = reqRollback;
       this.reqRollbackMode = reqRollbackMode;
       this.reqRbCostTime = reqRbCostTime;
       this.creatorId = creatorId;
       this.createTime = createTime;
       this.reqGroupFocus = reqGroupFocus;
       this.reqUpdateType = reqUpdateType;
    }
   
     @Id 
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAN_DETAIL_MANIFEST$SEQ")
 	@SequenceGenerator(name = "PLAN_DETAIL_MANIFEST$SEQ", sequenceName = "PLAN_DETAIL_MANIFEST$SEQ", allocationSize = 1)
    @Column(name="MANIFEST_ID", unique=true, nullable=false, precision=14, scale=0)
    public long getManifestId() {
        return this.manifestId;
    }
    
    public void setManifestId(long manifestId) {
        this.manifestId = manifestId;
    }
    
    @Column(name="PLAN_ID", nullable=false, precision=14, scale=0)
    public long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(long planId) {
        this.planId = planId;
    }
    
    @Column(name="QA_PROGRESS", length=100)
    public String getQaProgress() {
        return this.qaProgress;
    }
    
    public void setQaProgress(String qaProgress) {
        this.qaProgress = qaProgress;
    }
    
    @Column(name="QA_SITUATION", length=400)
    public String getQaSituation() {
        return this.qaSituation;
    }
    
    public void setQaSituation(String qaSituation) {
        this.qaSituation = qaSituation;
    }
    
    @Column(name="QA_STAFF", length=100)
    public String getQaStaff() {
        return this.qaStaff;
    }
    
    public void setQaStaff(String qaStaff) {
        this.qaStaff = qaStaff;
    }
    
    @Column(name="QA_MANAGER", length=100)
    public String getQaManager() {
        return this.qaManager;
    }
    
    public void setQaManager(String qaManager) {
        this.qaManager = qaManager;
    }
    
    @Column(name="QA_BUG_NUMBER", precision=8, scale=0)
    public Integer getQaBugNumber() {
        return this.qaBugNumber;
    }
    
    public void setQaBugNumber(Integer qaBugNumber) {
        this.qaBugNumber = qaBugNumber;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_DATE", length=11)
    public Date getReqDate() {
        return this.reqDate;
    }
    
    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }
    
    @Column(name="REQ_NO", length=100)
    public String getReqNo() {
        return this.reqNo;
    }
    
    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }
    
    @Column(name="REQ_NAME", length=1000)
    public String getReqName() {
        return this.reqName;
    }
    
    public void setReqName(String reqName) {
        this.reqName = reqName;
    }
    
    @Column(name="REQ_ORIGIN", length=100)
    public String getReqOrigin() {
        return this.reqOrigin;
    }
    
    public void setReqOrigin(String reqOrigin) {
        this.reqOrigin = reqOrigin;
    }
    
    @Column(name="REQ_LEVEL", length=40)
    public String getReqLevel() {
        return this.reqLevel;
    }
    
    public void setReqLevel(String reqLevel) {
        this.reqLevel = reqLevel;
    }
    
    @Column(name="REQ_PRIORITY", length=40)
    public String getReqPriority() {
        return this.reqPriority;
    }
    
    public void setReqPriority(String reqPriority) {
        this.reqPriority = reqPriority;
    }
    
    @Column(name="REQ_CATEGORY", length=40)
    public String getReqCategory() {
        return this.reqCategory;
    }
    
    public void setReqCategory(String reqCategory) {
        this.reqCategory = reqCategory;
    }
    
    @Column(name="REQ_TYPE", length=40)
    public String getReqType() {
        return this.reqType;
    }
    
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_UPDATE", length=11)
    public Date getReqUpdate() {
        return this.reqUpdate;
    }
    
    public void setReqUpdate(Date reqUpdate) {
        this.reqUpdate = reqUpdate;
    }
    
    @Column(name="REQ_REASON", length=2000)
    public String getReqReason() {
        return this.reqReason;
    }
    
    public void setReqReason(String reqReason) {
        this.reqReason = reqReason;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_FIRST_DATE", length=11)
    public Date getReqFirstDate() {
        return this.reqFirstDate;
    }
    
    public void setReqFirstDate(Date reqFirstDate) {
        this.reqFirstDate = reqFirstDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_LAST_DATE", length=11)
    public Date getReqLastDate() {
        return this.reqLastDate;
    }
    
    public void setReqLastDate(Date reqLastDate) {
        this.reqLastDate = reqLastDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_ONLINE_DATE", length=11)
    public Date getReqOnlineDate() {
        return this.reqOnlineDate;
    }
    
    public void setReqOnlineDate(Date reqOnlineDate) {
        this.reqOnlineDate = reqOnlineDate;
    }
    
    @Column(name="DEV_NO", length=100)
    public String getDevNo() {
        return this.devNo;
    }
    
    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }
    
    @Column(name="DEV_NAME", length=1000)
    public String getDevName() {
        return this.devName;
    }
    
    public void setDevName(String devName) {
        this.devName = devName;
    }
    
    @Column(name="DEV_STATUS", length=100)
    public String getDevStatus() {
        return this.devStatus;
    }
    
    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }
    
    @Column(name="REQ_IMPORTANT", length=40)
    public String getReqImportant() {
        return this.reqImportant;
    }
    
    public void setReqImportant(String reqImportant) {
        this.reqImportant = reqImportant;
    }
    
    @Column(name="SYS_NAME", length=100)
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
    
    @Column(name="QA_TEAM", length=100)
    public String getQaTeam() {
        return this.qaTeam;
    }
    
    public void setQaTeam(String qaTeam) {
        this.qaTeam = qaTeam;
    }
    
    @Column(name="DEV_STAFF", length=100)
    public String getDevStaff() {
        return this.devStaff;
    }
    
    public void setDevStaff(String devStaff) {
        this.devStaff = devStaff;
    }
    
    @Column(name="DEV_MANAGER", length=100)
    public String getDevManager() {
        return this.devManager;
    }
    
    public void setDevManager(String devManager) {
        this.devManager = devManager;
    }
    
    @Column(name="REQ_MANAGER", length=100)
    public String getReqManager() {
        return this.reqManager;
    }
    
    public void setReqManager(String reqManager) {
        this.reqManager = reqManager;
    }
    
    @Column(name="REQ_STAFF", length=100)
    public String getReqStaff() {
        return this.reqStaff;
    }
    
    public void setReqStaff(String reqStaff) {
        this.reqStaff = reqStaff;
    }
    
    @Column(name="REMARKS", length=2000)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name="DEV_WORKLOAD", precision=8, scale=0)
    public Integer getDevWorkload() {
        return this.devWorkload;
    }
    
    public void setDevWorkload(Integer devWorkload) {
        this.devWorkload = devWorkload;
    }
    
    @Column(name="DEV_ANALYSE", length=4000)
    public String getDevAnalyse() {
        return this.devAnalyse;
    }
    
    public void setDevAnalyse(String devAnalyse) {
        this.devAnalyse = devAnalyse;
    }
    
    @Column(name="REQ_SKIPPING_CYCLE", length=40)
    public String getReqSkippingCycle() {
        return this.reqSkippingCycle;
    }
    
    public void setReqSkippingCycle(String reqSkippingCycle) {
        this.reqSkippingCycle = reqSkippingCycle;
    }
    
    @Column(name="QA_CROSS_SYSTEM", length=40)
    public String getQaCrossSystem() {
        return this.qaCrossSystem;
    }
    
    public void setQaCrossSystem(String qaCrossSystem) {
        this.qaCrossSystem = qaCrossSystem;
    }
    
    @Column(name="REQ_GROUP", length=40)
    public String getReqGroup() {
        return this.reqGroup;
    }
    
    public void setReqGroup(String reqGroup) {
        this.reqGroup = reqGroup;
    }
    
    @Column(name="DEV_DATA_SCRIPT", length=40)
    public String getDevDataScript() {
        return this.devDataScript;
    }
    
    public void setDevDataScript(String devDataScript) {
        this.devDataScript = devDataScript;
    }
    
    @Column(name="REQ_ROLLBACK", length=100)
    public String getReqRollback() {
        return this.reqRollback;
    }
    
    public void setReqRollback(String reqRollback) {
        this.reqRollback = reqRollback;
    }
    
    @Column(name="REQ_ROLLBACK_MODE", length=100)
    public String getReqRollbackMode() {
        return this.reqRollbackMode;
    }
    
    public void setReqRollbackMode(String reqRollbackMode) {
        this.reqRollbackMode = reqRollbackMode;
    }
    
    @Column(name="REQ_RB_COST_TIME", length=100)
    public String getReqRbCostTime() {
        return this.reqRbCostTime;
    }
    
    public void setReqRbCostTime(String reqRbCostTime) {
        this.reqRbCostTime = reqRbCostTime;
    }
    
    @Column(name="CREATOR_ID", precision=14, scale=0)
    public Long getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=11)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="REQ_GROUP_FOCUS", length=40)
    public String getReqGroupFocus() {
        return this.reqGroupFocus;
    }
    
    public void setReqGroupFocus(String reqGroupFocus) {
        this.reqGroupFocus = reqGroupFocus;
    }
    
    @Column(name="REQ_UPDATE_TYPE", length=100)
    public String getReqUpdateType() {
        return this.reqUpdateType;
    }
    
    public void setReqUpdateType(String reqUpdateType) {
        this.reqUpdateType = reqUpdateType;
    }




}


