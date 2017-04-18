package com.ai.aiga.domain;
// Generated 2017-4-17 19:24:07 by Hibernate Tools 3.2.2.GA



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
 * AigaBossTestResult generated by hbm2java
 */
@Entity
@Table(name="AIGA_BOSS_TEST_RESULT"
)
public class AigaBossTestResult  implements java.io.Serializable {


     private Long resultId;
     private Long onlinePlan;
     private String onlinePlanName;
     private Date onlineDate;
     private String testRemark;
     private Long bugCount;
     private String bugRemark;
     private String reason;
     private Long ifSolve;
     private String bugStatus;
     private Long onlineCondition;
     private String remark;
     private String solution;
     private Long planId;
     private Long execTimeCount;
     private Long totalTimeCount;
     private String bossName;
     private Long taskId;
     private Long execCount;
     private Long analyTimeCount;
     private Long type;

    public AigaBossTestResult() {
    }

	
    public AigaBossTestResult(Long resultId) {
        this.resultId = resultId;
    }
    public AigaBossTestResult(Long resultId, Long onlinePlan, String onlinePlanName, Date onlineDate, String testRemark, Long bugCount, String bugRemark, String reason, Long ifSolve, String bugStatus, Long onlineCondition, String remark, String solution, Long planId, Long execTimeCount, Long totalTimeCount, String bossName, Long taskId, Long execCount, Long analyTimeCount, Long type) {
       this.resultId = resultId;
       this.onlinePlan = onlinePlan;
       this.onlinePlanName = onlinePlanName;
       this.onlineDate = onlineDate;
       this.testRemark = testRemark;
       this.bugCount = bugCount;
       this.bugRemark = bugRemark;
       this.reason = reason;
       this.ifSolve = ifSolve;
       this.bugStatus = bugStatus;
       this.onlineCondition = onlineCondition;
       this.remark = remark;
       this.solution = solution;
       this.planId = planId;
       this.execTimeCount = execTimeCount;
       this.totalTimeCount = totalTimeCount;
       this.bossName = bossName;
       this.taskId = taskId;
       this.execCount = execCount;
       this.analyTimeCount = analyTimeCount;
       this.type = type;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_BOSS_TEST_RESULT$SEQ")
     @SequenceGenerator(name="AIGA_BOSS_TEST_RESULT$SEQ",sequenceName="AIGA_BOSS_TEST_RESULT$SEQ",allocationSize=1)
    @Column(name="RESULT_ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="ONLINE_PLAN", precision=22, scale=0)
    public Long getOnlinePlan() {
        return this.onlinePlan;
    }
    
    public void setOnlinePlan(Long onlinePlan) {
        this.onlinePlan = onlinePlan;
    }
    
    @Column(name="ONLINE_PLAN_NAME", length=200)
    public String getOnlinePlanName() {
        return this.onlinePlanName;
    }
    
    public void setOnlinePlanName(String onlinePlanName) {
        this.onlinePlanName = onlinePlanName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ONLINE_DATE", length=7)
    public Date getOnlineDate() {
        return this.onlineDate;
    }
    
    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }
    
    @Column(name="TEST_REMARK", length=4000)
    public String getTestRemark() {
        return this.testRemark;
    }
    
    public void setTestRemark(String testRemark) {
        this.testRemark = testRemark;
    }
    
    @Column(name="BUG_COUNT", precision=22, scale=0)
    public Long getBugCount() {
        return this.bugCount;
    }
    
    public void setBugCount(Long bugCount) {
        this.bugCount = bugCount;
    }
    
    @Column(name="BUG_REMARK", length=4000)
    public String getBugRemark() {
        return this.bugRemark;
    }
    
    public void setBugRemark(String bugRemark) {
        this.bugRemark = bugRemark;
    }
    
    @Column(name="REASON", length=4000)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Column(name="IF_SOLVE", precision=22, scale=0)
    public Long getIfSolve() {
        return this.ifSolve;
    }
    
    public void setIfSolve(Long ifSolve) {
        this.ifSolve = ifSolve;
    }
    
    @Column(name="BUG_STATUS", length=200)
    public String getBugStatus() {
        return this.bugStatus;
    }
    
    public void setBugStatus(String bugStatus) {
        this.bugStatus = bugStatus;
    }
    
    @Column(name="ONLINE_CONDITION", precision=22, scale=0)
    public Long getOnlineCondition() {
        return this.onlineCondition;
    }
    
    public void setOnlineCondition(Long onlineCondition) {
        this.onlineCondition = onlineCondition;
    }
    
    @Column(name="REMARK", length=4000)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="SOLUTION", length=4000)
    public String getSolution() {
        return this.solution;
    }
    
    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="EXEC_TIME_COUNT", precision=22, scale=0)
    public Long getExecTimeCount() {
        return this.execTimeCount;
    }
    
    public void setExecTimeCount(Long execTimeCount) {
        this.execTimeCount = execTimeCount;
    }
    
    @Column(name="TOTAL_TIME_COUNT", precision=22, scale=0)
    public Long getTotalTimeCount() {
        return this.totalTimeCount;
    }
    
    public void setTotalTimeCount(Long totalTimeCount) {
        this.totalTimeCount = totalTimeCount;
    }
    
    @Column(name="BOSS_NAME", length=200)
    public String getBossName() {
        return this.bossName;
    }
    
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
    
    @Column(name="TASK_ID", precision=22, scale=0)
    public Long getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    @Column(name="EXEC_COUNT", precision=22, scale=0)
    public Long getExecCount() {
        return this.execCount;
    }
    
    public void setExecCount(Long execCount) {
        this.execCount = execCount;
    }
    
    @Column(name="ANALY_TIME_COUNT", precision=22, scale=0)
    public Long getAnalyTimeCount() {
        return this.analyTimeCount;
    }
    
    public void setAnalyTimeCount(Long analyTimeCount) {
        this.analyTimeCount = analyTimeCount;
    }
    
    @Column(name="TYPE", precision=22, scale=0)
    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }




}

