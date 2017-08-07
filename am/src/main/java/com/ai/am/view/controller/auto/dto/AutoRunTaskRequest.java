package com.ai.am.view.controller.auto.dto;

import java.util.Date;

/**
 * 自动化执行任务参数
 *
 * @author defaultekey
 * @date 2017/3/20
 */
public class AutoRunTaskRequest {
    private Long taskId;//主键
    private Long planId;//计划ID
    private String taskTag;//任务编号
    private String taskName;//任务名称
    private Long taskType;//任务类型
    private Long cycleType;//轮循方式
    private Long runType;//执行方式
    private Long taskResult;//任务结果
    private Date beginRunTime;//开始时间
    private Date endRunTime;//结束时间
    private Long spendTime;//总耗时
    private Long runTimes;//轮循执行次数
    private Long intervalTime;//轮循执行间隔时间
    private Long endTimes;//轮循执行结束次数
    private String machineIp;//机器IP
    private Long stopFlag;//是否停止
    private Long smsType;//是否发送短信
    private Long mailType;//是否发送邮件
    private Long parallelNum;//并行数
    private Long creatorId;//创建人
    private Long lastRunner;//最后执行人
    private Date createTime;//创建时间
    public AutoRunTaskRequest() {
    }

    public AutoRunTaskRequest(Long taskId, Long planId, String taskTag, String taskName, Long taskType, Long cycleType, Long runType, Long taskResult, Date beginRunTime, Date endRunTime, Long spendTime, Long runTimes, Long intervalTime, Long endTimes, String machineIp, Long stopFlag, Long smsType, Long mailType, Long parallelNum, Long creatorId, Long lastRunner, Date createTime) {
        this.taskId = taskId;
        this.planId = planId;
        this.taskTag = taskTag;
        this.taskName = taskName;
        this.taskType = taskType;
        this.cycleType = cycleType;
        this.runType = runType;
        this.taskResult = taskResult;
        this.beginRunTime = beginRunTime;
        this.endRunTime = endRunTime;
        this.spendTime = spendTime;
        this.runTimes = runTimes;
        this.intervalTime = intervalTime;
        this.endTimes = endTimes;
        this.machineIp = machineIp;
        this.stopFlag = stopFlag;
        this.smsType = smsType;
        this.mailType = mailType;
        this.parallelNum = parallelNum;
        this.creatorId = creatorId;
        this.lastRunner = lastRunner;
        this.createTime = createTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskType() {
        return taskType;
    }

    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }

    public Long getCycleType() {
        return cycleType;
    }

    public void setCycleType(Long cycleType) {
        this.cycleType = cycleType;
    }

    public Long getRunType() {
        return runType;
    }

    public void setRunType(Long runType) {
        this.runType = runType;
    }

    public Long getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Long taskResult) {
        this.taskResult = taskResult;
    }

    public Date getBeginRunTime() {
        return beginRunTime;
    }

    public void setBeginRunTime(Date beginRunTime) {
        this.beginRunTime = beginRunTime;
    }

    public Date getEndRunTime() {
        return endRunTime;
    }

    public void setEndRunTime(Date endRunTime) {
        this.endRunTime = endRunTime;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public Long getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Long runTimes) {
        this.runTimes = runTimes;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Long getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(Long endTimes) {
        this.endTimes = endTimes;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public Long getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Long stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Long getSmsType() {
        return smsType;
    }

    public void setSmsType(Long smsType) {
        this.smsType = smsType;
    }

    public Long getMailType() {
        return mailType;
    }

    public void setMailType(Long mailType) {
        this.mailType = mailType;
    }

    public Long getParallelNum() {
        return parallelNum;
    }

    public void setParallelNum(Long parallelNum) {
        this.parallelNum = parallelNum;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLastRunner() {
        return lastRunner;
    }

    public void setLastRunner(Long lastRunner) {
        this.lastRunner = lastRunner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
