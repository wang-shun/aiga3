package com.ai.aiga.service.home.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DealTaskInfo implements Serializable {
	private String userCode;
	private String userName;
	private String applyFirst;
	private String applySecond;
	private String applyThird;
	private String applyIndentyQues;	
	private String applyResolveQues;	
	private String applyCloseQues;	
	private String dealFirst;     
	private String dealSecond;
	private String dealThird;	
	private String dealIndentyQues;	
	private String dealResolveQues;	
	private String dealCloseQues;	
	private String hasSysRole;
	private String hasQuesRole;
	
	public void setApplySysData(String applyFirst,String applySecond,String applyThird) {
		this.applyFirst = applyFirst;
		this.applySecond = applySecond;
		this.applyThird = applyThird;
	}
	public void setDealSysData(String dealFirst,String dealSecond,String dealThird) {
		this.dealFirst = dealFirst;
		this.dealSecond = dealSecond;
		this.dealThird = dealThird;
	}
	public void setApplyQuesData(String applyIndentyQues,String applyResolveQues,String applyCloseQues) {
		this.applyIndentyQues = applyIndentyQues;
		this.applyResolveQues = applyResolveQues;
		this.applyCloseQues = applyCloseQues;
	}
	public void setDealQuesData(String dealIndentyQues,String dealResolveQues,String dealCloseQues) {
		this.dealIndentyQues = dealIndentyQues;
		this.dealResolveQues = dealResolveQues;
		this.dealCloseQues = dealCloseQues;
	}
}
