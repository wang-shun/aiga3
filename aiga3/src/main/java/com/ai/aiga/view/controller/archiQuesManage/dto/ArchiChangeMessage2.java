package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiChangeMessage2 implements Serializable {
	private List<ViewSeries2> series;
	private List<String> legend;
	private List<String> xAxis;
	
	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<ViewSeries2> getSeries() {
		return series;
	}

	public void setSeries(List<ViewSeries2> series) {
		this.series = series;
	}
}
