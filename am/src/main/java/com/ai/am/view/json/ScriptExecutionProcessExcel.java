package com.ai.am.view.json;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: ScriptExecutionProcessExcel
 * @author: liujinfang
 * @date: 2017年4月18日 下午8:01:25
 * @Description:
 * 
 */
@Data
public class ScriptExecutionProcessExcel  implements java.io.Serializable{
	private String batch;
    private Date startTime;
    private Date finishTime;
    private String historyTime;
    private String remark;
}

