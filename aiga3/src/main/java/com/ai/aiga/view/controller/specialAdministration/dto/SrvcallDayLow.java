package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class SrvcallDayLow implements Serializable {
	
	private String serviceid;
	private String avgduration;
	private String accesstimes;
	private String errortimes;
    
}
