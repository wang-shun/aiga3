package com.ai.am.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @ClassName: CodePathRequest
 * @author: liujinfang
 * @date: 2017年4月25日 下午2:57:46
 * @Description:
 * 
 */
@Data
public class CodePathRequestExcel implements java.io.Serializable{
	   private String listId;
	     private String sysName;
	     private String modelName;
	     private String packageName;
	     private Long state;
	     private String remark;
	     @DateTimeFormat(pattern = "yyyy-MM-dd")
	     private Date planDate;
	     private Long isFinished;
	     private Long updateCount;
	     private Long result;
	     private Long complimeCount;

}

