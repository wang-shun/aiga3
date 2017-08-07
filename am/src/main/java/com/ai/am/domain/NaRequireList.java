package com.ai.am.domain;
// Generated 2017-3-29 16:33:25 by Hibernate Tools 3.2.2.GA



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
 * NaRequireList generated by hbm2java
 */
@Entity
@Table(name="NA_REQUIRE_LIST"
    ,schema="AIGA"
)
public class NaRequireList  implements java.io.Serializable {


     private Long id;
     private String testMan;
     private String testManager;
     private String requireCode;
     private String requireName;
     private String bigSystem;
     private String system;
     private String devMan;
     private String devManager;
     private String requireMan;
     private Long planId;
     private String remarks;
     private Long introducedState;
     private Byte testResult;
     private Long testProgress;
     private Long testCondition;
     private Long defectNumber;
     private Date requireDate;
     private String requireSource;
     private Long requireLevel;
     private Long requirePriority;
     private Byte groupCheck;
     private Long requireClassify;
     private Long requireType;
     private Long requireOnlineType;
     private Date onlineDate;
     private String onlineReason;
     private Date firstTime;
     private Date lastTime;
     private Date upDate;
     private Long devTaskId;
     private String devTaskName;
     private Long taskState;
     private Long isImportantRequire;
     private String testGroup;
     private String requireApplicant;
     private Long workloadEvaluation;
     private String situationAnalysis;
     private Long isCycleDemand;
     private Long isSystemAdjust;
     private Long groupDemand;
     private Long isDatebaseScript;
     private Long reviewState;

    public NaRequireList() {
    }

	
    public NaRequireList(Long id, Long planId) {
        this.id = id;
        this.planId = planId;
    }
    public NaRequireList(Long id, String testMan, String testManager, String requireCode, String requireName, String bigSystem, String system, String devMan, String devManager, String requireMan, Long planId, String remarks, Long introducedState, Byte testResult, Long testProgress, Long testCondition, Long defectNumber, Date requireDate, String requireSource, Long requireLevel, Long requirePriority, Byte groupCheck, Long requireClassify, Long requireType, Long requireOnlineType, Date onlineDate, String onlineReason, Date firstTime, Date lastTime, Date upDate, Long devTaskId, String devTaskName, Long taskState, Long isImportantRequire, String testGroup, String requireApplicant, Long workloadEvaluation, String situationAnalysis, Long isCycleDemand, Long isSystemAdjust, Long groupDemand, Long isDatebaseScript, Long reviewState) {
       this.id = id;
       this.testMan = testMan;
       this.testManager = testManager;
       this.requireCode = requireCode;
       this.requireName = requireName;
       this.bigSystem = bigSystem;
       this.system = system;
       this.devMan = devMan;
       this.devManager = devManager;
       this.requireMan = requireMan;
       this.planId = planId;
       this.remarks = remarks;
       this.introducedState = introducedState;
       this.testResult = testResult;
       this.testProgress = testProgress;
       this.testCondition = testCondition;
       this.defectNumber = defectNumber;
       this.requireDate = requireDate;
       this.requireSource = requireSource;
       this.requireLevel = requireLevel;
       this.requirePriority = requirePriority;
       this.groupCheck = groupCheck;
       this.requireClassify = requireClassify;
       this.requireType = requireType;
       this.requireOnlineType = requireOnlineType;
       this.onlineDate = onlineDate;
       this.onlineReason = onlineReason;
       this.firstTime = firstTime;
       this.lastTime = lastTime;
       this.upDate = upDate;
       this.devTaskId = devTaskId;
       this.devTaskName = devTaskName;
       this.taskState = taskState;
       this.isImportantRequire = isImportantRequire;
       this.testGroup = testGroup;
       this.requireApplicant = requireApplicant;
       this.workloadEvaluation = workloadEvaluation;
       this.situationAnalysis = situationAnalysis;
       this.isCycleDemand = isCycleDemand;
       this.isSystemAdjust = isSystemAdjust;
       this.groupDemand = groupDemand;
       this.isDatebaseScript = isDatebaseScript;
       this.reviewState = reviewState;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_REQUIRE_LIST$SEQ")
     @SequenceGenerator(name="NA_REQUIRE_LIST$SEQ",sequenceName="NA_REQUIRE_LIST$SEQ",allocationSize=1)
    @Column(name="ID", nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="TEST_MAN", length=200)
    public String getTestMan() {
        return this.testMan;
    }
    
    public void setTestMan(String testMan) {
        this.testMan = testMan;
    }
    
    @Column(name="TEST_MANAGER", length=200)
    public String getTestManager() {
        return this.testManager;
    }
    
    public void setTestManager(String testManager) {
        this.testManager = testManager;
    }
    
    @Column(name="REQUIRE_CODE", length=200)
    public String getRequireCode() {
        return this.requireCode;
    }
    
    public void setRequireCode(String requireCode) {
        this.requireCode = requireCode;
    }
    
    @Column(name="REQUIRE_NAME", length=200)
    public String getRequireName() {
        return this.requireName;
    }
    
    public void setRequireName(String requireName) {
        this.requireName = requireName;
    }
    
    @Column(name="BIG_SYSTEM", length=200)
    public String getBigSystem() {
        return this.bigSystem;
    }
    
    public void setBigSystem(String bigSystem) {
        this.bigSystem = bigSystem;
    }
    
    @Column(name="SYSTEM", length=200)
    public String getSystem() {
        return this.system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    @Column(name="DEV_MAN", length=200)
    public String getDevMan() {
        return this.devMan;
    }
    
    public void setDevMan(String devMan) {
        this.devMan = devMan;
    }
    
    @Column(name="DEV_MANAGER", length=200)
    public String getDevManager() {
        return this.devManager;
    }
    
    public void setDevManager(String devManager) {
        this.devManager = devManager;
    }
    
    @Column(name="REQUIRE_MAN", length=200)
    public String getRequireMan() {
        return this.requireMan;
    }
    
    public void setRequireMan(String requireMan) {
        this.requireMan = requireMan;
    }
    
    @Column(name="PLAN_ID", nullable=false, precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="REMARKS", length=1000)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name="INTRODUCED_STATE", precision=22, scale=0)
    public Long getIntroducedState() {
        return this.introducedState;
    }
    
    public void setIntroducedState(Long introducedState) {
        this.introducedState = introducedState;
    }
    
    @Column(name="TEST_RESULT", precision=2, scale=0)
    public Byte getTestResult() {
        return this.testResult;
    }
    
    public void setTestResult(Byte testResult) {
        this.testResult = testResult;
    }
    
    @Column(name="TEST_PROGRESS", precision=22, scale=0)
    public Long getTestProgress() {
        return this.testProgress;
    }
    
    public void setTestProgress(Long testProgress) {
        this.testProgress = testProgress;
    }
    
    @Column(name="TEST_CONDITION", precision=22, scale=0)
    public Long getTestCondition() {
        return this.testCondition;
    }
    
    public void setTestCondition(Long testCondition) {
        this.testCondition = testCondition;
    }
    
    @Column(name="DEFECT_NUMBER", precision=22, scale=0)
    public Long getDefectNumber() {
        return this.defectNumber;
    }
    
    public void setDefectNumber(Long defectNumber) {
        this.defectNumber = defectNumber;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQUIRE_DATE", length=7)
    public Date getRequireDate() {
        return this.requireDate;
    }
    
    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }
    
    @Column(name="REQUIRE_SOURCE", length=100)
    public String getRequireSource() {
        return this.requireSource;
    }
    
    public void setRequireSource(String requireSource) {
        this.requireSource = requireSource;
    }
    
    @Column(name="REQUIRE_LEVEL", precision=22, scale=0)
    public Long getRequireLevel() {
        return this.requireLevel;
    }
    
    public void setRequireLevel(Long requireLevel) {
        this.requireLevel = requireLevel;
    }
    
    @Column(name="REQUIRE_PRIORITY", precision=22, scale=0)
    public Long getRequirePriority() {
        return this.requirePriority;
    }
    
    public void setRequirePriority(Long requirePriority) {
        this.requirePriority = requirePriority;
    }
    
    @Column(name="GROUP_CHECK", precision=2, scale=0)
    public Byte getGroupCheck() {
        return this.groupCheck;
    }
    
    public void setGroupCheck(Byte groupCheck) {
        this.groupCheck = groupCheck;
    }
    
    @Column(name="REQUIRE_CLASSIFY", precision=22, scale=0)
    public Long getRequireClassify() {
        return this.requireClassify;
    }
    
    public void setRequireClassify(Long requireClassify) {
        this.requireClassify = requireClassify;
    }
    
    @Column(name="REQUIRE_TYPE", precision=22, scale=0)
    public Long getRequireType() {
        return this.requireType;
    }
    
    public void setRequireType(Long requireType) {
        this.requireType = requireType;
    }
    
    @Column(name="REQUIRE_ONLINE_TYPE", precision=22, scale=0)
    public Long getRequireOnlineType() {
        return this.requireOnlineType;
    }
    
    public void setRequireOnlineType(Long requireOnlineType) {
        this.requireOnlineType = requireOnlineType;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ONLINE_DATE", length=7)
    public Date getOnlineDate() {
        return this.onlineDate;
    }
    
    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }
    
    @Column(name="ONLINE_REASON", length=1000)
    public String getOnlineReason() {
        return this.onlineReason;
    }
    
    public void setOnlineReason(String onlineReason) {
        this.onlineReason = onlineReason;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FIRST_TIME", length=7)
    public Date getFirstTime() {
        return this.firstTime;
    }
    
    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_TIME", length=7)
    public Date getLastTime() {
        return this.lastTime;
    }
    
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UP_DATE", length=7)
    public Date getUpDate() {
        return this.upDate;
    }
    
    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }
    
    @Column(name="DEV_TASK_ID", precision=22, scale=0)
    public Long getDevTaskId() {
        return this.devTaskId;
    }
    
    public void setDevTaskId(Long devTaskId) {
        this.devTaskId = devTaskId;
    }
    
    @Column(name="DEV_TASK_NAME", length=200)
    public String getDevTaskName() {
        return this.devTaskName;
    }
    
    public void setDevTaskName(String devTaskName) {
        this.devTaskName = devTaskName;
    }
    
    @Column(name="TASK_STATE", precision=22, scale=0)
    public Long getTaskState() {
        return this.taskState;
    }
    
    public void setTaskState(Long taskState) {
        this.taskState = taskState;
    }
    
    @Column(name="IS_IMPORTANT_REQUIRE", precision=22, scale=0)
    public Long getIsImportantRequire() {
        return this.isImportantRequire;
    }
    
    public void setIsImportantRequire(Long isImportantRequire) {
        this.isImportantRequire = isImportantRequire;
    }
    
    @Column(name="TEST_GROUP", length=200)
    public String getTestGroup() {
        return this.testGroup;
    }
    
    public void setTestGroup(String testGroup) {
        this.testGroup = testGroup;
    }
    
    @Column(name="REQUIRE_APPLICANT", length=200)
    public String getRequireApplicant() {
        return this.requireApplicant;
    }
    
    public void setRequireApplicant(String requireApplicant) {
        this.requireApplicant = requireApplicant;
    }
    
    @Column(name="WORKLOAD_EVALUATION", precision=22, scale=0)
    public Long getWorkloadEvaluation() {
        return this.workloadEvaluation;
    }
    
    public void setWorkloadEvaluation(Long workloadEvaluation) {
        this.workloadEvaluation = workloadEvaluation;
    }
    
    @Column(name="SITUATION_ANALYSIS", length=200)
    public String getSituationAnalysis() {
        return this.situationAnalysis;
    }
    
    public void setSituationAnalysis(String situationAnalysis) {
        this.situationAnalysis = situationAnalysis;
    }
    
    @Column(name="IS_CYCLE_DEMAND", precision=22, scale=0)
    public Long getIsCycleDemand() {
        return this.isCycleDemand;
    }
    
    public void setIsCycleDemand(Long isCycleDemand) {
        this.isCycleDemand = isCycleDemand;
    }
    
    @Column(name="IS_SYSTEM_ADJUST", precision=22, scale=0)
    public Long getIsSystemAdjust() {
        return this.isSystemAdjust;
    }
    
    public void setIsSystemAdjust(Long isSystemAdjust) {
        this.isSystemAdjust = isSystemAdjust;
    }
    
    @Column(name="GROUP_DEMAND", precision=22, scale=0)
    public Long getGroupDemand() {
        return this.groupDemand;
    }
    
    public void setGroupDemand(Long groupDemand) {
        this.groupDemand = groupDemand;
    }
    
    @Column(name="IS_DATEBASE_SCRIPT", precision=22, scale=0)
    public Long getIsDatebaseScript() {
        return this.isDatebaseScript;
    }
    
    public void setIsDatebaseScript(Long isDatebaseScript) {
        this.isDatebaseScript = isDatebaseScript;
    }
    
    @Column(name="REVIEW_STATE", precision=22, scale=0)
    public Long getReviewState() {
        return this.reviewState;
    }
    
    public void setReviewState(Long reviewState) {
        this.reviewState = reviewState;
    }




}


