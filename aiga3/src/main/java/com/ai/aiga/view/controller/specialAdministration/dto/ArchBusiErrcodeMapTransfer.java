package com.ai.aiga.view.controller.specialAdministration.dto;

import java.io.Serializable;
import lombok.Data;
@Data
public class ArchBusiErrcodeMapTransfer implements Serializable {
	
	private String insertTime;
    private String person;
    private String center;
    private String dataResource;
    private Long errcodeMapId;
    private String csfServiceCode;
    private String i18nErrcode;
    private String i18nErrcodeDesc;
    private String esbErrcode;
    private String esbErrcodeDesc;
    private String csfErrcode;
    private String csfErrcodeDesc;
    private String createDate;
    private String stateDate;
    private String state;
    private String remarks;
    private String checkResult;
    
}
