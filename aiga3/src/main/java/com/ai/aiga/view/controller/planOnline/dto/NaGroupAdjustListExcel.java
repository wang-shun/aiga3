package com.ai.aiga.view.controller.planOnline.dto;

import lombok.Data;

/**
 * @ClassName: NaGroupAdjustListExcel
 * @author: liujinfang
 * @date: 2017年4月26日 下午3:51:55
 * @Description:
 * 
 */
@Data
public class NaGroupAdjustListExcel implements java.io.Serializable {
	private String devTaskId;
    private String requireName;
    private String testMan;
    private String otherSysName;
    private String otherSysMan;
    private String otherSysManTel;

}

