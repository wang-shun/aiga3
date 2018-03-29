package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class SrvcallDayTransfer implements Serializable {
	
	private String serviceid;
	private String avgduration;
	private String accesstimes;
	private String errortimes;
	private String timeperoid;
	private String servicestatus;
	private String errmsg;
	private long maxduration;
	private long minduration;
	private long sumduration;
	private String statskind;
	private String statscode;
	
    
}
