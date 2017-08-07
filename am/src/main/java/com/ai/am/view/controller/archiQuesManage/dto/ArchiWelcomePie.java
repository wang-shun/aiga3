package com.ai.am.view.controller.archiQuesManage.dto;
//nmsn
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiWelcomePie implements Serializable {
	private List<PieSeries> series;
	private List<String> legend;
	

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<PieSeries> getSeries() {
		return series;
	}

	public void setSeries(List<PieSeries> series) {
		this.series = series;
	}
}
