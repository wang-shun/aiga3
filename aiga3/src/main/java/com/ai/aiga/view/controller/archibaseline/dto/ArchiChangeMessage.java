package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiChangeMessage implements Serializable {
	private List<ViewSeries> series;
	private String[] legend;
	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	public List<ViewSeries> getSeries() {
		return series;
	}

	public void setSeries(List<ViewSeries> series) {
		this.series = series;
	}
}
