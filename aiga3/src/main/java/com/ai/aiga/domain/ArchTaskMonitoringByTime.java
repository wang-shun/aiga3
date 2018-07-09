package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringByTime implements Serializable {


    private Date startDate;
    private int finishDate;//1天24个小时
    private long checkTotal;
    private long sessionTotal;
    private long reportTotal;
    private long collectTotal;
    private long taskTotal;

    public ArchTaskMonitoringByTime() {
    }

    public ArchTaskMonitoringByTime(Date startDate, int finishDate, long checkTotal, long sessionTotal, long reportTotal, long collectTotal, long taskTotal) {
        this.startDate = startDate;
        this.finishDate = finishDate;
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

    public int getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(int finishDate) {
        this.finishDate = finishDate;
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

    @Override
    public String toString() {
        return "ArchTaskMonitoringByTime{" +
                "startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", checkTotal=" + checkTotal +
                ", sessionTotal=" + sessionTotal +
                ", reportTotal=" + reportTotal +
                ", collectTotal=" + collectTotal +
                ", taskTotal=" + taskTotal +
                '}';
    }
}
