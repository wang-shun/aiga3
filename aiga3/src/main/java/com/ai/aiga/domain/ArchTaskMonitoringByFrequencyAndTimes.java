package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringByFrequencyAndTimes implements Serializable {

    private Date startDate;
    private long firstTimes;
    private long secondTimes;
    private long thirdTimes;
    private long fourTimes;
    private long firstMinutes;
    private long secondMinutes;
    private long thirdMinutes;
    private long fourMinutes;

    public ArchTaskMonitoringByFrequencyAndTimes() { }

    public ArchTaskMonitoringByFrequencyAndTimes(Date startDate, long firstTimes, long secondTimes, long thirdTimes, long fourTimes, long firstMinutes, long secondMinutes, long thirdMinutes, long fourMinutes) {
        this.startDate = startDate;
        this.firstTimes = firstTimes;
        this.secondTimes = secondTimes;
        this.thirdTimes = thirdTimes;
        this.fourTimes = fourTimes;
        this.firstMinutes = firstMinutes;
        this.secondMinutes = secondMinutes;
        this.thirdMinutes = thirdMinutes;
        this.fourMinutes = fourMinutes;
    }

    public long getThirdTimes() {
        return thirdTimes;
    }

    public void setThirdTimes(long thirdTimes) {
        this.thirdTimes = thirdTimes;
    }

    public long getFirstMinutes() {
        return firstMinutes;
    }

    public void setFirstMinutes(long firstMinutes) {
        this.firstMinutes = firstMinutes;
    }

    public long getSecondMinutes() {
        return secondMinutes;
    }

    public void setSecondMinutes(long secondMinutes) {
        this.secondMinutes = secondMinutes;
    }

    public long getThirdMinutes() {
        return thirdMinutes;
    }

    public void setThirdMinutes(long thirdMinutes) {
        this.thirdMinutes = thirdMinutes;
    }

    public long getFourMinutes() {
        return fourMinutes;
    }

    public void setFourMinutes(long fourMinutes) {
        this.fourMinutes = fourMinutes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getFirstTimes() {
        return firstTimes;
    }

    public void setFirstTimes(long firstTimes) {
        this.firstTimes = firstTimes;
    }

    public long getSecondTimes() {
        return secondTimes;
    }

    public void setSecondTimes(long secondTimes) {
        this.secondTimes = secondTimes;
    }

    public long getFourTimes() {
        return fourTimes;
    }

    public void setFourTimes(long fourTimes) {
        this.fourTimes = fourTimes;
    }

}
