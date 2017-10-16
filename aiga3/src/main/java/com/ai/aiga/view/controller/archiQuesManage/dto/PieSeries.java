package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
//nmsn
@SuppressWarnings("serial")
public class PieSeries implements Serializable {
	private String id;
	private String name;
	private String value;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
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
