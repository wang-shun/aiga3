package com.ai.aiga.webservice.soap.dto;
// Generated 2017-3-29 11:29:38 by Hibernate Tools 3.2.2.GA

import java.util.Date;


public class NaCodePathDTO implements java.io.Serializable {

	
    private long id; //
    private String listId;
    private String proName;      //应用名
    private String selPackage;     //包名
    private String  planDate;   //预计上线日期
    private String modelName; //模块名
    private String remark;
    private Long state;
    private Long result;
	public NaCodePathDTO() {
	
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getSelPackage() {
		return selPackage;
	}
	public void setSelPackage(String selPackage) {
		this.selPackage = selPackage;
	}


	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}

	
	
}
