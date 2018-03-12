package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchDbConnectHeatBaseMain implements Serializable {
	
	private String indexName;
	private String center;
    private String module;
    private String vessel;
    private String time;
    private Long value;
    private Long vesselvalue;
    
}
