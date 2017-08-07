package com.ai.am.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViewSeries implements Serializable {
	private String name;
	private String type;
	private int[] data;
	
	public ViewSeries(){
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
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
}
