package com.ai.aiga.view.json;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: OnlineSysReleaseExcel
 * @author: liujinfang
 * @date: 2017年4月18日 下午8:13:14
 * @Description:
 * 
 */
@Data
public class OnlineSysReleaseExcel {
	 private String sysName;
     private Date startTime;
     private Date finishTime;
     private String historyTime;
     private String remark;

}

