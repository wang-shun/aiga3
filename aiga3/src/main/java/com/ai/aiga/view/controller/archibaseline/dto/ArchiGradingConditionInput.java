package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArchiGradingConditionInput  implements Serializable {

	private long applyId;
	private String name;
	private Long onlysysId;
	private String cloudOrderId;
	private String state;
	
}
