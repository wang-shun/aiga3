package com.ai.aiga.view.json;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StaffRequest {
	private String code;
	private String name;
	private Long organizeId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOrganizeId() {
		return organizeId;
	}
	public void setOrganizeId(Long organizeId) {
		this.organizeId = organizeId;
	}
	
	

}
