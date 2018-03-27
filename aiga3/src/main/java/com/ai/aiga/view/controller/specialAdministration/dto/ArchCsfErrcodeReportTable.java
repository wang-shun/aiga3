package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class ArchCsfErrcodeReportTable implements Serializable {
	
	private String centerName;
    private Long errcodeCfgNum;
    private Long cfgCsfNum;
    private Long totlaCsfNum;
    private String errcodeCoverRate;
    private String errcodeCoverRatePctg;
    private String errcodeSpecRate;
    private String errcodeSpecRatePctg;
    private String pmOfChinamobile;
    private String pmOfAsiainfo;
    private String monthDate;
    
}
