package com.ai.aiga.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchTaskMonitoringTableThird implements Serializable {

    private String startDate;
    private int firstTimes;
    private int secondTimes;
    private int thirdTimes;
    private int fourTimes;
    private float firstPro;
    private float secondPro;
    private float thirdPro;
    private float fourPro;

    public ArchTaskMonitoringTableThird() { }

    public ArchTaskMonitoringTableThird(String startDate, int firstTimes, int secondTimes, int thirdTimes, int fourTimes, float firstPro, float secondPro, float thirdPro, float fourPro) {
        this.startDate = startDate;
        this.firstTimes = firstTimes;
        this.secondTimes = secondTimes;
        this.thirdTimes = thirdTimes;
        this.fourTimes = fourTimes;
        this.firstPro = firstPro;
        this.secondPro = secondPro;
        this.thirdPro = thirdPro;
        this.fourPro = fourPro;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getFirstTimes() {
        return firstTimes;
    }

    public void setFirstTimes(int firstTimes) {
        this.firstTimes = firstTimes;
    }

    public int getSecondTimes() {
        return secondTimes;
    }

    public void setSecondTimes(int secondTimes) {
        this.secondTimes = secondTimes;
    }

    public int getThirdTimes() {
        return thirdTimes;
    }

    public void setThirdTimes(int thirdTimes) {
        this.thirdTimes = thirdTimes;
    }

    public int getFourTimes() {
        return fourTimes;
    }

    public void setFourTimes(int fourTimes) {
        this.fourTimes = fourTimes;
    }

    public float getFirstPro() {
        return firstPro;
    }

    public void setFirstPro(float firstPro) {
        this.firstPro = firstPro;
    }

    public float getSecondPro() {
        return secondPro;
    }

    public void setSecondPro(float secondPro) {
        this.secondPro = secondPro;
    }

    public float getThirdPro() {
        return thirdPro;
    }

    public void setThirdPro(float thirdPro) {
        this.thirdPro = thirdPro;
    }

    public float getFourPro() {
        return fourPro;
    }

    public void setFourPro(float fourPro) {
        this.fourPro = fourPro;
    }

}
