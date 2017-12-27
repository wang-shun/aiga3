package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

import com.ai.aiga.domain.ArchitectureSecond;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class GrandingTranslateOutput implements Serializable {
	private Long idFirst;
	private String idFirstName;
	private String idBelongName;
	private String sysStateName;
	private String adviceThirdId;
	private List<ArchitectureSecond> secData;
}
