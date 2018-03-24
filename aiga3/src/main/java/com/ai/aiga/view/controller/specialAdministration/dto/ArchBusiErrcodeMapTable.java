package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class ArchBusiErrcodeMapTable implements Serializable {
	
    private String persona;
    private String personb;
	private String center;
	private String resource;
    private Long total;
    private Long signin;
    private Long signon;
    private double percentage;
    private double percentagerate;
    private double standard;
    private double standardrate;
    private String stime;
    
}
