package com.ai.am.view.controller.release.dto;

import lombok.Data;

/**
 * @ClassName: DbExecutionExceptionRequest
 * @author: liujinfang
 * @date: 2017年4月27日 上午10:29:05
 * @Description:
 * 
 */
@Data
public class DbExecutionExceptionRequest implements java.io.Serializable {
	private Long id;
    private String time;
    private String scriptName;
    private String problemPhenomenon;
    private String reason;
    private String resolvent;
    private Long planId;
    private String ext1;
    private String ext2;
    private String ext3;

}

