package com.ai.aiga.view.controller.archibaseline.dto;

import java.util.List;

public class secSysPojo {
	private List<secSysMessage> content;
	private String totalElements;
	public List<secSysMessage> getContent() {
		return content;
	}
	public void setContent(List<secSysMessage> content) {
		this.content = content;
	}
	public String getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

}
