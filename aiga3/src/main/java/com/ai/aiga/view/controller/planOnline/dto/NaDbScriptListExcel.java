package com.ai.aiga.view.controller.planOnline.dto;

import lombok.Data;

/**
 * @ClassName: NaDbScriptListExcel
 * @author: liujinfang
 * @date: 2017年4月25日 下午6:35:25
 * @Description:
 * 
 */
@Data
public class NaDbScriptListExcel implements java.io.Serializable{
	private String cuttingScript;
    private Long execute;
    private String executeTime;

}

