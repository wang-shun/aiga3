package com.ai.aiga.view.controller.archibaseline.dto;

import java.util.List;

public class gradingPojo {
	private List<gradingMessage> content;
	private String totalElements;
	public List<gradingMessage> getContent() {
		return content;
	}
	public void setContent(List<gradingMessage> content) {
		this.content = content;
	}
	public String getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}
}
