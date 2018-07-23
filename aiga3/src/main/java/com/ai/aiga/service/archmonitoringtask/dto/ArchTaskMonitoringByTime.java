package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringByTime implements Serializable {


    private Date startDate;
    private int startTime;//1天24个小时
    private long checkTotal;
    private long sessionTotal;
    private long reportTotal;
    private long collectTotal;
    private long taskTotal;

    

    public ArchTaskMonitoringByTime() {}

    public ArchTaskMonitoringByTime(Date startDate, int startTime, long checkTotal, long sessionTotal, long reportTotal, long collectTotal, long taskTotal) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.checkTotal = checkTotal;
        this.sessionTotal = sessionTotal;
        this.reportTotal = reportTotal;
        this.collectTotal = collectTotal;
        this.taskTotal = taskTotal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
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

    public long getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(long taskTotal) {
        this.taskTotal = taskTotal;
    }
}
