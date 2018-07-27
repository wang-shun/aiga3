package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringReport implements Serializable {
    private Date startDate;
    //第一条报告内容
    private int lastdayTaskCount;
    private int lastdayAddTaskCount;
    private int allTaskCount;
    private int countChange;
    private float taskSuccessRate;
    private float taskSuccessRateChange;
    private double taskAvgTime;
    private double taskAvgTimeChange;
    //第二条报告内容
    private String sysDate;
    private long taskOverCount;
    private long failTaskCount;
    private long taskConnectCount;
    private long taskMoreAvgCount;

    public ArchTaskMonitoringReport() { }

    public ArchTaskMonitoringReport(Date startDate, int lastdayTaskCount, int lastdayAddTaskCount, int allTaskCount, int countChange, float taskSuccessRate, float taskSuccessRateChange, double taskAvgTime, double taskAvgTimeChange, String sysDate, long taskOverCount, long failTaskCount, long taskConnectCount, long taskMoreAvgCount) {
        this.startDate = startDate;
        this.lastdayTaskCount = lastdayTaskCount;
        this.lastdayAddTaskCount = lastdayAddTaskCount;
        this.allTaskCount = allTaskCount;
        this.countChange = countChange;
        this.taskSuccessRate = taskSuccessRate;
        this.taskSuccessRateChange = taskSuccessRateChange;
        this.taskAvgTime = taskAvgTime;
        this.taskAvgTimeChange = taskAvgTimeChange;
        this.sysDate = sysDate;
        this.taskOverCount = taskOverCount;
        this.failTaskCount = failTaskCount;
        this.taskConnectCount = taskConnectCount;
        this.taskMoreAvgCount = taskMoreAvgCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getLastdayTaskCount() {
        return lastdayTaskCount;
    }

    public void setLastdayTaskCount(int lastdayTaskCount) {
        this.lastdayTaskCount = lastdayTaskCount;
    }

    public int getLastdayAddTaskCount() {
        return lastdayAddTaskCount;
    }

    public void setLastdayAddTaskCount(int lastdayAddTaskCount) {
        this.lastdayAddTaskCount = lastdayAddTaskCount;
    }

    public int getAllTaskCount() {
        return allTaskCount;
    }

    public void setAllTaskCount(int allTaskCount) {
        this.allTaskCount = allTaskCount;
    }

    public int getCountChange() {
        return countChange;
    }

    public void setCountChange(int countChange) {
        this.countChange = countChange;
    }

    public float getTaskSuccessRate() {
        return taskSuccessRate;
    }

    public void setTaskSuccessRate(float taskSuccessRate) {
        this.taskSuccessRate = taskSuccessRate;
    }

    public float getTaskSuccessRateChange() {
        return taskSuccessRateChange;
    }

    public void setTaskSuccessRateChange(float taskSuccessRateChange) {
        this.taskSuccessRateChange = taskSuccessRateChange;
    }

    public double getTaskAvgTime() {
        return taskAvgTime;
    }

    public void setTaskAvgTime(double taskAvgTime) {
        this.taskAvgTime = taskAvgTime;
    }

    public double getTaskAvgTimeChange() {
        return taskAvgTimeChange;
    }

    public void setTaskAvgTimeChange(double taskAvgTimeChange) {
        this.taskAvgTimeChange = taskAvgTimeChange;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public long getTaskOverCount() {
        return taskOverCount;
    }

    public void setTaskOverCount(long taskOverCount) {
        this.taskOverCount = taskOverCount;
    }

    public long getFailTaskCount() {
        return failTaskCount;
    }

    public void setFailTaskCount(long failTaskCount) {
        this.failTaskCount = failTaskCount;
    }

    public long getTaskConnectCount() {
        return taskConnectCount;
    }

    public void setTaskConnectCount(long taskConnectCount) {
        this.taskConnectCount = taskConnectCount;
    }

    public long getTaskMoreAvgCount() {
        return taskMoreAvgCount;
    }

    public void setTaskMoreAvgCount(long taskMoreAvgCount) {
        this.taskMoreAvgCount = taskMoreAvgCount;
    }
}
