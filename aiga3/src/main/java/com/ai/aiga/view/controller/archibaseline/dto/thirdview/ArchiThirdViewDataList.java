package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class ArchiThirdViewDataList implements Serializable {
	private List<ArchiThirdViewData> content;

	public List<ArchiThirdViewData> getContent() {
		return content;
	}

	public void setContent(List<ArchiThirdViewData> content) {
		this.content = content;
	}

}
