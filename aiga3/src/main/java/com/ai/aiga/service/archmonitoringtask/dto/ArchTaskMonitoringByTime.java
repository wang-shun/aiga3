package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringByTime implements Serializable {


    private Date startDate;
    private int startTime;//1天24个小时
    private String cfgTaskTypeCode;
    private int taskCount;


    private int checkTotal;
    private int sessionTotal;
    private int reportTotal;
    private int collectTotal;
    private int taskTotal;

    public ArchTaskMonitoringByTime() {}

    public ArchTaskMonitoringByTime(Date startDate, int startTime, String cfgTaskTypeCode, int taskCount, int checkTotal, int sessionTotal, int reportTotal, int collectTotal, int taskTotal) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.cfgTaskTypeCode = cfgTaskTypeCode;
        this.taskCount = taskCount;
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

    public String getCfgTaskTypeCode() {
        return cfgTaskTypeCode;
    }

    public void setCfgTaskTypeCode(String cfgTaskTypeCode) {
        this.cfgTaskTypeCode = cfgTaskTypeCode;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(int checkTotal) {
        this.checkTotal = checkTotal;
    }

    public int getSessionTotal() {
        return sessionTotal;
    }

    public void setSessionTotal(int sessionTotal) {
        this.sessionTotal = sessionTotal;
    }

    public int getReportTotal() {
        return reportTotal;
    }

    public void setReportTotal(int reportTotal) {
        this.reportTotal = reportTotal;
    }

    public int getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(int collectTotal) {
        this.collectTotal = collectTotal;
    }

    public int getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(int taskTotal) {
        this.taskTotal = taskTotal;
    }
}
