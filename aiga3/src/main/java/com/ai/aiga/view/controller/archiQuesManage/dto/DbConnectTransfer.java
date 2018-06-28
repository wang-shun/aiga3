package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DbConnectTransfer implements Serializable {

	private String db;
	private String dbName;
	private long min;
    private long max;
    private long fact;
    private long fact1;
    private long fact31;
    private String health;
    private double dayrate;
    private double monthrate;
    private String time;
}
