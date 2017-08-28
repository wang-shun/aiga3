package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class GrandingTranslateInput implements Serializable {
	private String sysState; 
	private String ext1; 
	private Long idBelong;
	private Long idThird;
}
