package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ArchTaskMonitoringHintView implements Serializable {


    private String  startTime;
    private String finishTime;
    //key value 接收map值
    private int key;
    private int value;
    private double keys;
    private int values;


    public ArchTaskMonitoringHintView() {}

    public ArchTaskMonitoringHintView(String startTime, String finishTime, int key, int value, double keys, int values) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.key = key;
        this.value = value;
        this.keys = keys;
        this.values = values;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getKeys() {
        return keys;
    }

    public void setKeys(double keys) {
        this.keys = keys;
    }

    public int getValues() {
        return values;
    }

    public void setValues(int values) {
        this.values = values;
    }
}
