package com.ai.aiga.view.controller.archiQuesManage.dto.quesstatepie;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class quesStatePieOutput implements Serializable {
	private List<String> legend;
	private List<quesStatePieData> seriesInner;
	private List<quesStatePieData> seriesOuter;
	public List<String> getLegend() {
		return legend;
	}
	public void setLegend(List<String> legend) {
		this.legend = legend;
	}
	public List<quesStatePieData> getSeriesInner() {
		return seriesInner;
	}
	public void setSeriesInner(List<quesStatePieData> seriesInner) {
		this.seriesInner = seriesInner;
	}
	public List<quesStatePieData> getSeriesOuter() {
		return seriesOuter;
	}
	public void setSeriesOuter(List<quesStatePieData> seriesOuter) {
		this.seriesOuter = seriesOuter;
	}
}
