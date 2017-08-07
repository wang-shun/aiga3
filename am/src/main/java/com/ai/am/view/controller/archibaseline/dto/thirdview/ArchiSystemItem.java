package com.ai.am.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiSystemItem implements Serializable {
    private String id;
    private String bgColor;
    private String name;
    private String isNodeName;
    private String mediaType;
    
	public ArchiSystemItem() {}
	
	public ArchiSystemItem(String id, Object name, Object bgColor, String isNodeName, Object mediaType) {
		this.id = id;
		this.name = String.valueOf(name);
		this.isNodeName = isNodeName;
		this.bgColor = String.valueOf(bgColor);
		if("app".equals(String.valueOf(mediaType))) {
			this.mediaType = "1";
		}else {
			this.mediaType = "0";
		}
	}
	
	public ArchiSystemItem(int id, Object name, Object bgColor, String isNodeName, Object mediaType) {
		this.id = String.valueOf(id);
		this.name = String.valueOf(name);
		this.isNodeName = isNodeName;
		this.bgColor = String.valueOf(bgColor);
		if("app".equals(String.valueOf(mediaType))) {
			this.mediaType = "1";
		}else {
			this.mediaType = "0";
		}
	}
	
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
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
