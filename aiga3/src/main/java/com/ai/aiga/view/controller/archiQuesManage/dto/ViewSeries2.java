package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViewSeries2 implements Serializable {
	private String name;
	private String type;
	private double[] data;
	
	public ViewSeries2(){
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
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}
}
