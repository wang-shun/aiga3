package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiThirdPaaSView implements Serializable {
	private String id;
	private String name;
	private String isNodeName;					//	是否是数据节点
	private String isCross;						//  是否是跨层层级  非数据节点有效
	private List<ArchiThirdLevelView> item;

	public ArchiThirdPaaSView() {}
	
	public ArchiThirdPaaSView(String id,String name,String isNodeName) {
		this.id = id;
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";
	}
	
	public ArchiThirdPaaSView(int id,String name,String isNodeName) {
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
	public List<ArchiThirdLevelView> getItem() {
		return item;
	}
	public void setItem(List<ArchiThirdLevelView> item) {
		this.item = item;
	}
}
