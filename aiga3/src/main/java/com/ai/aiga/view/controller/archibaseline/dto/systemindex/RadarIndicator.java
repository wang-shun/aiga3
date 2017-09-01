package com.ai.aiga.view.controller.archibaseline.dto.systemindex;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class RadarIndicator implements Serializable {
	private String name;
	private Long max;
	public RadarIndicator(){}
	public RadarIndicator(String name, Long max) {
		this.max = max;
		this.name = name;
	}
}
