package com.ai.aiga.view.controller.planOnline.dto;

import lombok.Data;

/**
 * @ClassName: DatabaseConfiScriptExcel
 * @author: liujinfang
 * @date: 2017年4月25日 下午5:09:16
 * @Description:
 * 
 */
@Data
public class DatabaseConfiScriptExcel implements java.io.Serializable{
	 private String database;
     private Long scriptNumber;
     private String executeTime;

}

