package com.ai.aiga.service.home.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DealTaskInfo implements Serializable {
	private String applyFirst;
	private String applySecond;
	private String applyThird;	
}
