package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchDbConnectHeatBaseDetail implements Serializable {
	
	private String indexName;
	private String center;
    private String module;
    private String db;
    private Long value;
    private Long vesselvalue;
    private double persentage;
    
}
