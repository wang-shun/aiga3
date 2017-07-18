package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiSystemItem implements Serializable {
    private String id;
    private String bgColor;
    private String name;
    private String isNodeName;
    
	public ArchiSystemItem() {}
	
	public ArchiSystemItem(String id,Object name, Object bgColor,String isNodeName) {
		this.id = id;
		this.name = String.valueOf(name);
		this.isNodeName = isNodeName;
		this.bgColor = String.valueOf(bgColor);
	}
	
	public ArchiSystemItem(int id, Object name, Object bgColor,String isNodeName) {
		this.id = String.valueOf(id);
		this.name = String.valueOf(name);
		this.isNodeName = isNodeName;
		this.bgColor = String.valueOf(bgColor);
	}
	
	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
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
