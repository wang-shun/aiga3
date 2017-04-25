package com.ai.aiga.view.json;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NaChangePlanOnileRequest {
	private Long onlinePlan;
    private String onlinePlanName;
    private Long planState;
    private String createOpId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date doneDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    private Byte result;
    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;
    private Byte sign;
    private Byte types;
    private Byte timely;
    private Byte isFinished;
    private Byte autoRunResult;
    private Date fileUploadLastTime;
	
    
}
