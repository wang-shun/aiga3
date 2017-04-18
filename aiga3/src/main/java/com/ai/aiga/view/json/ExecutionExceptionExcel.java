package com.ai.aiga.view.json;

import lombok.Data;

/**
 * @ClassName: ExecutionExceptionExcel
 * @author: liujinfang
 * @date: 2017年4月17日 下午6:02:27
 * @Description:
 * 
 */
@Data
public class ExecutionExceptionExcel {
	 private Long id;
     private String time;
     private String scriptName;
     private String problemPhenomenon;
     private String reason;
     private String resolvent;
}

