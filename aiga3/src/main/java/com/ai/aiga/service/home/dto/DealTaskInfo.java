package com.ai.aiga.service.home.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DealTaskInfo implements Serializable {
	private String applyFirst;
	private String applySecond;
	private String applyThird;	
	private String dealFirst;
	private String dealSecond;
	private String dealThird;	
	private String hasSysRole;
	
	
	public void setApplyData(String applyFirst,String applySecond,String applyThird) {
		this.applyFirst = applyFirst;
		this.applySecond = applySecond;
		this.applyThird = applyThird;
	}
	public void setDealData(String dealFirst,String dealSecond,String dealThird) {
		this.dealFirst = dealFirst;
		this.dealSecond = dealSecond;
		this.dealThird = dealThird;
	}
}
