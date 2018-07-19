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

    //第二张表除了cfgTaskId，taskName，businessClass之外，还需添加times字段
    private int times;

    //第三张表除了cfgTaskId，taskName，businessClass之外，minutes字段
    private double minutes;

    public ArchTaskMonitoringTable() {}

    public ArchTaskMonitoringTable(Date startDate, String condition, String startTime, long cfgTaskId, String taskName, String businessClass, int times, double minutes) {
        this.startDate = startDate;
        this.condition = condition;
        this.startTime = startTime;
        this.cfgTaskId = cfgTaskId;
        this.taskName = taskName;
        this.businessClass = businessClass;
        this.times = times;
        this.minutes = minutes;
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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "ArchTaskMonitoringTable{" +
                "startDate=" + startDate +
                ", condition='" + condition + '\'' +
                ", startTime='" + startTime + '\'' +
                ", cfgTaskId=" + cfgTaskId +
                ", taskName='" + taskName + '\'' +
                ", businessClass='" + businessClass + '\'' +
                ", times=" + times +
                ", minutes=" + minutes +
                '}';
    }
}
