package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("serial")
public class ArchTaskMonitoringTopFirst implements Serializable {

    private Date startDate;
    private long cfgTaskId;
    private long countNum;

    public ArchTaskMonitoringTopFirst() {}

    public ArchTaskMonitoringTopFirst(Date startDate, long cfgTaskId, long countNum) {
        this.startDate = startDate;
        this.cfgTaskId = cfgTaskId;
        this.countNum = countNum;
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

    public long getCountNum() {
        return countNum;
    }

    public void setCountNum(long countNum) {
        this.countNum = countNum;
    }

    @Override
    public String toString() {
        return "ArhTaskMonitoringTopFirst{" +
                "startDate=" + startDate +
                ", cfgTaskId=" + cfgTaskId +
                ", countNum=" + countNum +
                '}';
    }
}
