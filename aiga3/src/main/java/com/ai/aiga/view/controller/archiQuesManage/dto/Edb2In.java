package com.ai.aiga.view.controller.archiQuesManage.dto;

/**
 * Created by zhuchao on 18-6-21.
 */
public class Edb2In {
    private String name;
    private String conns;
    private String minIdle;
    private String maxIdle;
    private String maxActive;
    private String min;
    private String max;
    private String fact;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConns() {
        return conns;
    }

    public void setConns(String conns) {
        this.conns = conns;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }
}
