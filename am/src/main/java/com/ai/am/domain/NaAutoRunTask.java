package com.ai.am.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author defaultekey
 * @date 2017/3/17
 */
@Entity
@Table(name = "NA_AUTO_RUN_TASK")
public class NaAutoRunTask {
    private Long taskId;
    private Long planId;
    private String taskTag;
    private String taskName;
    private Long taskType;
    private Long cycleType;
    private Long cycleTiming;
    private Long runType;
    private Long taskResult;
    private Date beginRunTime;
    private Date timingRunTime;
    private Date endRunTime;
    private Long spendTime;
    private Long runTimes;
    private Long intervalTime;
    private Long endTimes;
    private String machineIp;
    private Long stopFlag;
    private Long smsType;
    private Long mailType;
    private Long parallelNum;
    private Long creatorId;
    private Long lastRunner;
    private Date createTime;
    private Long distributeNum;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_RUN_TASK$SEQ")
    @SequenceGenerator(name="NA_AUTO_RUN_TASK$SEQ",sequenceName="NA_AUTO_RUN_TASK$SEQ",allocationSize=1)
    @Column(name = "TASK_ID")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "PLAN_ID")
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    @Basic
    @Column(name = "TASK_TAG")
    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "TASK_TYPE")
    public Long getTaskType() {
        return taskType;
    }

    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "CYCLE_TYPE")
    public Long getCycleType() {
        return cycleType;
    }

    public void setCycleType(Long cycleType) {
        this.cycleType = cycleType;
    }

    @Basic
    @Column(name = "CYCLE_TIMING")
    public Long getCycleTiming() {
        return cycleTiming;
    }

    public void setCycleTiming(Long cycleTiming) {
        this.cycleTiming = cycleTiming;
    }

    @Basic
    @Column(name = "RUN_TYPE")
    public Long getRunType() {
        return runType;
    }

    public void setRunType(Long runType) {
        this.runType = runType;
    }

    @Basic
    @Column(name = "TASK_RESULT")
    public Long getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Long taskResult) {
        this.taskResult = taskResult;
    }

    @Basic
    @Column(name = "BEGIN_RUN_TIME")
    public Date getBeginRunTime() {
        return beginRunTime;
    }

    public void setBeginRunTime(Date beginRunTime) {
        this.beginRunTime = beginRunTime;
    }

    @Basic
    @Column(name = "TIMING_RUN_TIME")
    public Date getTimingRunTime() {
        return timingRunTime;
    }

    public void setTimingRunTime(Date timingRunTime) {
        this.timingRunTime = timingRunTime;
    }

    @Basic
    @Column(name = "END_RUN_TIME")
    public Date getEndRunTime() {
        return endRunTime;
    }

    public void setEndRunTime(Date endRunTime) {
        this.endRunTime = endRunTime;
    }

    @Basic
    @Column(name = "SPEND_TIME")
    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    @Basic
    @Column(name = "RUN_TIMES")
    public Long getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Long runTimes) {
        this.runTimes = runTimes;
    }

    @Basic
    @Column(name = "INTERVAL_TIME")
    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    @Basic
    @Column(name = "END_TIMES")
    public Long getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(Long endTimes) {
        this.endTimes = endTimes;
    }

    @Basic
    @Column(name = "MACHINE_IP")
    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    @Basic
    @Column(name = "STOP_FLAG")
    public Long getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Long stopFlag) {
        this.stopFlag = stopFlag;
    }

    @Basic
    @Column(name = "SMS_TYPE")
    public Long getSmsType() {
        return smsType;
    }

    public void setSmsType(Long smsType) {
        this.smsType = smsType;
    }

    @Basic
    @Column(name = "MAIL_TYPE")
    public Long getMailType() {
        return mailType;
    }

    public void setMailType(Long mailType) {
        this.mailType = mailType;
    }

    @Basic
    @Column(name = "PARALLEL_NUM")
    public Long getParallelNum() {
        return parallelNum;
    }

    public void setParallelNum(Long parallelNum) {
        this.parallelNum = parallelNum;
    }

    @Basic
    @Column(name = "CREATOR_ID")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "DISTRIBUTE_NUM")
    public Long getDistributeNum() {
        return distributeNum;
    }

    public void setDistributeNum(Long distributeNum) {
        this.distributeNum = distributeNum;
    }

    @Basic
    @Column(name = "LAST_RUNNER")
    public Long getLastRunner() {
        return lastRunner;
    }

    public void setLastRunner(Long lastRunner) {
        this.lastRunner = lastRunner;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
