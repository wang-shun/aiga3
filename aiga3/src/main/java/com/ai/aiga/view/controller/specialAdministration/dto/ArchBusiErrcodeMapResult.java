package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class ArchBusiErrcodeMapResult implements Serializable {
	
    private String person;
	private String center;
	private String resources;
    private Long total;
    private Long signin;
    
}
