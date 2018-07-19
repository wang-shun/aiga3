package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTop implements Serializable {

    private Date startDate;
    private long cfgTaskId;
    private long countSum;

    public ArchTaskMonitoringTop() {}

    public ArchTaskMonitoringTop(Date startDate, long cfgTaskId, long countSum) {
        this.startDate = startDate;
        this.cfgTaskId = cfgTaskId;
        this.countSum = countSum;
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

    public long getCountSum() {
        return countSum;
    }

    public void setCountSum(long countSum) {
        this.countSum = countSum;
    }
}
