package com.ai.aiga.view.controller.archibaseline.dto.plandate;

import java.util.List;

import lombok.Data;

@Data
public class OnLineTimeSetOutput {
	private boolean ifSynchroData;
	private String outputMessage;
	private List<String> onlineDate;
}
