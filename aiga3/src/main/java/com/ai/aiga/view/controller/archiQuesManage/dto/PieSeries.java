package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
//nmsn
@SuppressWarnings("serial")
public class PieSeries implements Serializable {
	private String name;
	private String value;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
