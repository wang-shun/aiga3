package com.ai.aiga.view.json;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: OnlineSysReleaseStageExcel
 * @author: liujinfang
 * @date: 2017年4月18日 下午8:22:31
 * @Description:
 * 
 */
@Data
public class OnlineSysReleaseStageExcel {
	private String sysName;
    private Date startTime;
    private Date finishTime;
    private String historyTime;
    private String remark;

}

