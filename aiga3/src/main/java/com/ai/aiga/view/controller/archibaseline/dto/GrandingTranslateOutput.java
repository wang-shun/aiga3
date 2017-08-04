package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

import com.ai.aiga.domain.ArchitectureSecond;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class GrandingTranslateOutput implements Serializable {
	private List<ArchitectureSecond> secData;
	private String idBelongName;
	private String sysStateName;
}
