package com.ai.aiga.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTable implements Serializable {
    private String startDate;
    private long checkTotal;
    private long sessionTotal;
    private long reportTotal;
    private long collectTotal;
    private double successRate;

    public ArchTaskMonitoringTable() {}

    public ArchTaskMonitoringTable(String startDate, long checkTotal, long sessionTotal, long reportTotal, long collectTotal, double successRate) {
        this.startDate = startDate;
        this.checkTotal = checkTotal;
        this.sessionTotal = sessionTotal;
        this.reportTotal = reportTotal;
        this.collectTotal = collectTotal;
        this.successRate = successRate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(long checkTotal) {
        this.checkTotal = checkTotal;
    }

    public long getSessionTotal() {
        return sessionTotal;
    }

    public void setSessionTotal(long sessionTotal) {
        this.sessionTotal = sessionTotal;
    }

    public long getReportTotal() {
        return reportTotal;
    }

    public void setReportTotal(long reportTotal) {
        this.reportTotal = reportTotal;
    }

    public long getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(long collectTotal) {
        this.collectTotal = collectTotal;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

}
