package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoringByFrequencyAndTimes implements Serializable {

    private Date startDate;
    private String numberTimes;
    private String numberMinutes;
    private int times;
    private int minutes;

    public ArchTaskMonitoringByFrequencyAndTimes() { }

    public ArchTaskMonitoringByFrequencyAndTimes(Date startDate, String numberTimes, String numberMinutes, int times, int minutes) {
        this.startDate = startDate;
        this.numberTimes = numberTimes;
        this.numberMinutes = numberMinutes;
        this.times = times;
        this.minutes = minutes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNumberTimes() {
        return numberTimes;
    }

    public void setNumberTimes(String numberTimes) {
        this.numberTimes = numberTimes;
    }

    public String getNumberMinutes() {
        return numberMinutes;
    }

    public void setNumberMinutes(String numberMinutes) {
        this.numberMinutes = numberMinutes;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
