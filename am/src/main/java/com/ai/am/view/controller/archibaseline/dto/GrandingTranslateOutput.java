package com.ai.am.view.controller.archibaseline.dto;

import java.io.Serializable;
import java.util.List;

import com.ai.am.domain.ArchitectureSecond;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class GrandingTranslateOutput implements Serializable {
	private List<ArchitectureSecond> secData;
	private String idBelongName;
	private String sysStateName;
}
