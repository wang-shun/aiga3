package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;
import java.util.List;

import com.ai.aiga.domain.ArchitectureStaticData;

@SuppressWarnings("serial")
public class ArchiThirdViewOutput implements Serializable {
	private String isCross;
	private List<ArchitectureStaticData> stateItems;
	private ArchiThirdContent content;
	public String getIsCross() {
		return isCross;
	}
	public void setIsCross(String isCross) {
		this.isCross = isCross;
	}
	public List<ArchitectureStaticData> getStateItems() {
		return stateItems;
	}
	public void setStateItems(List<ArchitectureStaticData> stateItems) {
		this.stateItems = stateItems;
	}
	public ArchiThirdContent getContent() {
		return content;
	}
	public void setContent(ArchiThirdContent content) {
		this.content = content;
	}
}
