package com.ai.am.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiThirdLevelView implements Serializable {
	private String id;
	private String name;
	private String isNodeName;
	private String isCross;
	private List<ArchiThirdViewCenterItem> item;
	
	public ArchiThirdLevelView() {}
	
	public ArchiThirdLevelView(String id,String name,String isNodeName) {
		this.id = id;
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";
	}
	
	public ArchiThirdLevelView(int id,String name,String isNodeName) {
		this.id = String.valueOf(id);
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";
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
	public List<ArchiThirdViewCenterItem> getItem() {
		return item;
	}
	public void setItem(List<ArchiThirdViewCenterItem> item) {
		this.item = item;
	}
}
