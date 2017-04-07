package com.ai.aiga.webservice.soap.dto;

import java.util.Date;

public class FromBmcDTO {
    private Date startTime; //开始时间
    private Date finishedTime;//结束时间
    private String step;//步骤（验收环境包含5个步骤（下载、编译打包、推送、发布、启停）、生产环境包含4个步骤（下载、推送、发布、启停））
    private String result;//结果
    private String reason;//失败原因
    private Long codeId;//代码包路径id
    private  String ext2;//环境
	public FromBmcDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getCodeId() {
		return codeId;
	}
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

}
