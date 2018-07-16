package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class ArchTaskMonitoringTable implements Serializable {
    private Date startDate;
    private String cfgTaskTypeCode;

    private long taskLogId;
    private long cfgTaskId;
    private String taskName;
    private String businessClass;
    private String results;
    private String startTime;
    private String finishTime;

    public ArchTaskMonitoringTable() {}

    public ArchTaskMonitoringTable(Date startDate, String cfgTaskTypeCode, long taskLogId, long cfgTaskId, String taskName, String businessClass, String results, String startTime, String finishTime) {
        this.startDate = startDate;
        this.cfgTaskTypeCode = cfgTaskTypeCode;
        this.taskLogId = taskLogId;
        this.cfgTaskId = cfgTaskId;
        this.taskName = taskName;
        this.businessClass = businessClass;
        this.results = results;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCfgTaskTypeCode() {
        return cfgTaskTypeCode;
    }

    public void setCfgTaskTypeCode(String cfgTaskTypeCode) {
        this.cfgTaskTypeCode = cfgTaskTypeCode;
    }

    public long getTaskLogId() {
        return taskLogId;
    }

    public void setTaskLogId(long taskLogId) {
        this.taskLogId = taskLogId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "ArchTaskMonitoringTable{" +
                "startDate='" + startDate + '\'' +
                ", cfgTaskTypeCode='" + cfgTaskTypeCode + '\'' +
                ", taskLogId=" + taskLogId +
                ", cfgTaskId=" + cfgTaskId +
                ", taskName='" + taskName + '\'' +
                ", businessClass='" + businessClass + '\'' +
                ", results='" + results + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                '}';
    }
}
