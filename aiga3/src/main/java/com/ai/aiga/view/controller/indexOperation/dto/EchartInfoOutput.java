package com.ai.aiga.view.controller.indexOperation.dto;

import java.util.List;

import lombok.Data;
@Data
public class EchartInfoOutput {
	private List<EchartViewSeries> series;
	private List<String> legend;
	private List<String> xAxis;
}
