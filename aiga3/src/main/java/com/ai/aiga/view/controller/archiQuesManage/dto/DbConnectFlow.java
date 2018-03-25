package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DbConnectFlow implements Serializable {

	private String db;
	private long min;
    private long max;
    private long fact;
    private String health;
    private double dayrate;
    private double monthrate;
    private String time;
}
