package com.ai.aiga.view.controller.planOnline.dto;

import lombok.Data;

/**
 * @ClassName: NaGroupRequireListExcel
 * @author: liujinfang
 * @date: 2017年4月26日 下午3:37:08
 * @Description:
 * 
 */
@Data
public class NaGroupRequireListExcel implements java.io.Serializable{
	private String devTaskId;
    private String requireName;
    private String testMan;
    private String requireMan;

}

