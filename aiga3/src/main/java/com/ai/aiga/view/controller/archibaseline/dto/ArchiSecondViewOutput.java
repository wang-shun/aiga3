package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiSecondViewOutput implements Serializable {
	private String isCross;
	private List<ArchiSecondCrossContent> crossContent;
	private List<ArchiSecondContent> content;

	public String getIsCross() {
		return isCross;
	}
	public void setIsCross(String isCross) {
		this.isCross = isCross;
	}
	public List<ArchiSecondCrossContent> getCrossContent() {
		return crossContent;
	}
	public void setCrossContent(List<ArchiSecondCrossContent> crossContent) {
		this.crossContent = crossContent;
	}
	public List<ArchiSecondContent> getContent() {
		return content;
	}
	public void setContent(List<ArchiSecondContent> content) {
		this.content = content;
	}
}
