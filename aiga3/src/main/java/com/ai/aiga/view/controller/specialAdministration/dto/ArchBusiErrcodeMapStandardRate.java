package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchBusiErrcodeMapStandardRate implements Serializable {
	
    private String center;
    private Long standardin;
    private Long standardout;
    private double percentage;
    
}
