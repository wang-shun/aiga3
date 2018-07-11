package com.ai.aiga.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTableSecond implements Serializable {

    private String startDate;
    private int finishDate;
    private int checkTotal;
    private int sessionTotal;
    private int reportTotal;
    private int collectTotal;
    private int taskTotal;

    public ArchTaskMonitoringTableSecond() {}

    public ArchTaskMonitoringTableSecond(String startDate, int finishDate, int checkTotal, int sessionTotal, int reportTotal, int collectTotal, int taskTotal) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.checkTotal = checkTotal;
        this.sessionTotal = sessionTotal;
        this.reportTotal = reportTotal;
        this.collectTotal = collectTotal;
        this.taskTotal = taskTotal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(int finishDate) {
        this.finishDate = finishDate;
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
