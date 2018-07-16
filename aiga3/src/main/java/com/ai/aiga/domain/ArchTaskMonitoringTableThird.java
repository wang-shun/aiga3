package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTableThird implements Serializable {

    private Date startDate;

    private long cfgTaskId;
    private String taskName;
    private double minutes;

    public ArchTaskMonitoringTableThird() {  }

    public ArchTaskMonitoringTableThird(Date startDate, long cfgTaskId, String taskName, double minutes) {
        this.startDate = startDate;
        this.cfgTaskId = cfgTaskId;
        this.taskName = taskName;
        this.minutes = minutes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }
}
