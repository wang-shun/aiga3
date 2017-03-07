package com.ai.aiga.view.json;

import java.sql.Clob;

public class NaUiParamRequest {
	private Long compId;
	private Long paramId;
	private String paramName;
	private Clob paramValue;
	private String paramDesc;
	private String paramSql;
	private String paramExpect;
	private Long creatorId;
	public Long getCompId() {
		return compId;
	}
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public Long getParamId() {
		return paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Clob getParamValue() {
		return paramValue;
	}
	public void setParamValue(Clob paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	public String getParamSql() {
		return paramSql;
	}
	public void setParamSql(String paramSql) {
		this.paramSql = paramSql;
	}
	public String getParamExpect() {
		return paramExpect;
	}
	public void setParamExpect(String paramExpect) {
		this.paramExpect = paramExpect;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	
}
