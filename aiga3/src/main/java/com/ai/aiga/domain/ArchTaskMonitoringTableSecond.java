package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTableSecond implements Serializable {

    private Date startDate;
    private int times;
    private long cfgTaskId;
    private String taskName;
    private String businessClass;


    public ArchTaskMonitoringTableSecond() { }

    public ArchTaskMonitoringTableSecond(Date startDate, int times, long cfgTaskId, String taskName, String businessClass) {
        this.startDate = startDate;
        this.times = times;
        this.cfgTaskId = cfgTaskId;
        this.taskName = taskName;
        this.businessClass = businessClass;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
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

    @Override
    public String toString() {
        return "ArchTaskMonitoringTableThird{" +
                "startDate=" + startDate +
                ", times=" + times +
                ", cfgTaskId=" + cfgTaskId +
                ", taskName='" + taskName + '\'' +
                ", businessClass='" + businessClass + '\'' +
                '}';
    }
}
