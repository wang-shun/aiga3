package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiChangeMessage implements Serializable {
	private List<ViewSeries> series;
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

	public List<ViewSeries> getSeries() {
		return series;
	}

	public void setSeries(List<ViewSeries> series) {
		this.series = series;
	}
}
