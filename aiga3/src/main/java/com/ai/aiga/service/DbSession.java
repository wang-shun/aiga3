package com.ai.aiga.service;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class DbSession implements Serializable {
	public String status;
	public String msg;
	public Object data;
}
