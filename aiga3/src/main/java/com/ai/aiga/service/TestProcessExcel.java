package com.ai.aiga.service;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: TestProcessExcel
 * @author: liujinfang
 * @date: 2017年4月18日 下午8:19:05
 * @Description:
 * 
 */
@Data
public class TestProcessExcel {
	 private Long testType;
     private Date startTime;
     private Date finishTime;
     private String historyTime;
     private String remark;

}

