package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViewSeriesL implements Serializable {
	private String name;
	private String type;
	private long[] data;
	
	public ViewSeriesL(){
		this.type = "bar";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long[] getData() {
		return data;
	}
	public void setData(long[] data) {
		this.data = data;
	}
}
