package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchDbConnectHeatBaseSelects implements Serializable {

	private String insertTime;
    private String indexName;
    private String key3;//module
    private String db;
    private String percentage;
    
}
