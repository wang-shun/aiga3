package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ArchiIndexTotalMessage implements Serializable {
	
	private List<String> legendData;
	private List<SeriesData> seriesData;
	
	public List<String> getLegendData() {
		return legendData;
	}
	public void setLegendData(List<String> legendData) {
		this.legendData = legendData;
	}
	public List<SeriesData> getSeriesData() {
		return seriesData;
	}
	public void setSeriesData(List<SeriesData> seriesData) {
		this.seriesData = seriesData;
	}
	
	
}
