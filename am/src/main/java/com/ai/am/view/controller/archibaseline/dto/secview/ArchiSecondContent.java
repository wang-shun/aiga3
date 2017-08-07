package com.ai.am.view.controller.archibaseline.dto.secview;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiSecondContent implements Serializable {
	private String id;
	private String name;
	private String isNodeName;					//	是否是数据节点
	private String isCross;						//  是否是跨层层级  非数据节点有效
	private List<ArchiSecondViewItem> item;
	
	public String getIsCross() {
		return isCross;
	}

	public void setIsCross(String isCross) {
		this.isCross = isCross;
	}

	public ArchiSecondContent() {	
	}
	
	public ArchiSecondContent(String id,String name,String isNodeName) {
		this.id = id;
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";				//此构造方法下默认不跨层	
	}
	
	public ArchiSecondContent(int id,String name,String isNodeName) {
		this.id = String.valueOf(id);
		this.name = name;
		this.isNodeName = isNodeName;
		this.isCross = "0";          	//此构造方法下默认不跨层	
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
	public List<ArchiSecondViewItem> getItem() {
		return item;
	}
	public void setItem(List<ArchiSecondViewItem> item) {
		this.item = item;
	}
}
