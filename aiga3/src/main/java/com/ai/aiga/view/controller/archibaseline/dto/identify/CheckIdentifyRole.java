package com.ai.aiga.view.controller.archibaseline.dto.identify;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class CheckIdentifyRole implements Serializable {
	private String staffId;
	private String isRole;
	private String roles;
}
