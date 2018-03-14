package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchDbConnectHeatBaseCoreTable implements Serializable {

	private String indexName;
    private String center;
    private String module;
    private String db;
    private Long vesselNum;
    private Long minIdle;
    private Long maxIdle;
    private Long gtminIdle;
    private Long totalSession;
    private double persentage;
    private String insertTime;
    
}
