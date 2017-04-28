package com.ai.aiga.view.controller.release.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @ClassName: NaReleaseReportRequest
 * @author: liujinfang
 * @date: 2017年4月20日 下午3:20:00
 * @Description:
 * 
 */
@Data
public class NaReleaseReportRequest {
	 private String taskName;
	 private String startTime;
     
     private String finishTime;

}

