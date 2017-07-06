package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiSecondCrossContent implements Serializable{
	private String id;
	private String startCrossId;
	private String endCrossId;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartCrossId() {
		return startCrossId;
	}
	public void setStartCrossId(String startCrossId) {
		this.startCrossId = startCrossId;
	}
	public String getEndCrossId() {
		return endCrossId;
	}
	public void setEndCrossId(String endCrossId) {
		this.endCrossId = endCrossId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
