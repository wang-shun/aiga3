package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTop implements Serializable {

    private Date startDate;
    private long cfgTaskId;
    private long countSum;
    private String failRate;
    private int failSum;
    private int totalSum;

    public ArchTaskMonitoringTop() {}

    public ArchTaskMonitoringTop(Date startDate, long cfgTaskId, long countSum, String failRate, int failSum, int totalSum) {
        this.startDate = startDate;
        this.cfgTaskId = cfgTaskId;
        this.countSum = countSum;
        this.failRate = failRate;
        this.failSum = failSum;
        this.totalSum = totalSum;
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

    public String getFailRate() {
        return failRate;
    }

    public void setFailRate(String failRate) {
        this.failRate = failRate;
    }

    public int getFailSum() {
        return failSum;
    }

    public void setFailSum(int failSum) {
        this.failSum = failSum;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
}
