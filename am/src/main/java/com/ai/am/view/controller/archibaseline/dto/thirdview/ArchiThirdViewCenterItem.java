package com.ai.am.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiThirdViewCenterItem implements Serializable {
	private String id;
	private String name;
	private String isNodeName;
	private String isCross;
	private List<ArchiSystemItem> item;
	
	public ArchiThirdViewCenterItem() {}
	
	public ArchiThirdViewCenterItem(String id,String name,String isNodeName) {
		this.id = id;
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";
	}
	
	public ArchiThirdViewCenterItem(int id,String name,String isNodeName) {
		this.id = String.valueOf(id);
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";
	}
	public List<ArchiSystemItem> getItem() {
		return item;
	}
	public void setItem(List<ArchiSystemItem> item) {
		this.item = item;
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
	public String getIsCross() {
		return isCross;
	}
	public void setIsCross(String isCross) {
		this.isCross = isCross;
	}
}
