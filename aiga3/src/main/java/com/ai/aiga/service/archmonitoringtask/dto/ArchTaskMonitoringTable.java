package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class ArchTaskMonitoringTable implements Serializable {

    private Date startDate;
    private String condition;

    //第一张表需要以下字段
    private String startTime;
    private long cfgTaskId;
    private String taskName;
    private String businessClass;
    private String results;

    //第二张表除了cfgTaskId，taskName，businessClass之外，还需添加successTimes,failTimes,successRate字段
    private int successTimes;
    private int failTimes;
    private float successRate;

    //第三张表除了cfgTaskId，taskName，businessClass之外，avgTime,taskExpr
    private float avgTime;
    private String taskExpr;

    public ArchTaskMonitoringTable() {}

    public ArchTaskMonitoringTable(Date startDate, String condition, String startTime, long cfgTaskId, String taskName, String businessClass, String results, int successTimes, int failTimes, float successRate, float avgTime, String taskExpr) {
        this.startDate = startDate;
        this.condition = condition;
        this.startTime = startTime;
        this.cfgTaskId = cfgTaskId;
        this.taskName = taskName;
        this.businessClass = businessClass;
        this.results = results;
        this.successTimes = successTimes;
        this.failTimes = failTimes;
        this.successRate = successRate;
        this.avgTime = avgTime;
        this.taskExpr = taskExpr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getCfgTaskId() {
        return cfgTaskId;
    }

    public void setCfgTaskId(long cfgTaskId) {
        this.cfgTaskId = cfgTaskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public int getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(int successTimes) {
        this.successTimes = successTimes;
    }

    public int getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(int failTimes) {
        this.failTimes = failTimes;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
    }

    public float getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(float avgTime) {
        this.avgTime = avgTime;
    }

    public String getTaskExpr() {
        return taskExpr;
    }

    public void setTaskExpr(String taskExpr) {
        this.taskExpr = taskExpr;
    }

}
