package com.ai.aiga.domain;

import java.io.Serializable;

public class ArchTaskMonitoringTableFour implements Serializable {

    private String startDate;
    private int firstMinutes;
    private int secondMinutes;
    private int thirdMinutes;
    private int fourMinutes;
    private float firstPro;
    private float secondPro;
    private float thirdPro;
    private float fourPro;

    public ArchTaskMonitoringTableFour() {}

    public ArchTaskMonitoringTableFour(String startDate, int firstMinutes, int secondMinutes, int thirdMinutes, int fourMinutes, float firstPro, float secondPro, float thirdPro, float fourPro) {
        this.startDate = startDate;
        this.firstMinutes = firstMinutes;
        this.secondMinutes = secondMinutes;
        this.thirdMinutes = thirdMinutes;
        this.fourMinutes = fourMinutes;
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

    public int getFirstMinutes() {
        return firstMinutes;
    }

    public void setFirstMinutes(int firstMinutes) {
        this.firstMinutes = firstMinutes;
    }

    public int getSecondMinutes() {
        return secondMinutes;
    }

    public void setSecondMinutes(int secondMinutes) {
        this.secondMinutes = secondMinutes;
    }

    public int getThirdMinutes() {
        return thirdMinutes;
    }

    public void setThirdMinutes(int thirdMinutes) {
        this.thirdMinutes = thirdMinutes;
    }

    public int getFourMinutes() {
        return fourMinutes;
    }

    public void setFourMinutes(int fourMinutes) {
        this.fourMinutes = fourMinutes;
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
