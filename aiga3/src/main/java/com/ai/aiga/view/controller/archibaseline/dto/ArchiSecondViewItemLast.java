package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiSecondViewItemLast implements Serializable {
	private String id;
	private String name;
	private String isNodeName;
	
	public ArchiSecondViewItemLast() {}
	
	public ArchiSecondViewItemLast(String id,String name,String isNodeName) {
		this.id = id;
		this.name = name;
		this.isNodeName = isNodeName;
	}
	
	public ArchiSecondViewItemLast(int id,String name,String isNodeName) {
		this.id = String.valueOf(id);
		this.name = name;
		this.isNodeName = isNodeName;
	}
	
	
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
	public String getIsNodeName() {
		return isNodeName;
	}
	public void setIsNodeName(String isNodeName) {
		this.isNodeName = isNodeName;
	}
}
