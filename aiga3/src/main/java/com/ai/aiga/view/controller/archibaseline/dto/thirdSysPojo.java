package com.ai.aiga.view.controller.archibaseline.dto;

import java.util.List;


public class thirdSysPojo {
	private List<thirdSysMessage> content;
	private String totalElements;
	public String getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

	public List<thirdSysMessage> getContent() {
		return content;
	}

	public void setContent(List<thirdSysMessage> content) {
		this.content = content;
	} 
}
