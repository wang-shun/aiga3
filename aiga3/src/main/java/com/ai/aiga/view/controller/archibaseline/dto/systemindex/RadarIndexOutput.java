package com.ai.aiga.view.controller.archibaseline.dto.systemindex;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class RadarIndexOutput implements Serializable {
	private List<RadarIndicator> indicator;
	private List<Long> value;
}
