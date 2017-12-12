package com.ai.aiga.service.home.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class DealTaskInfo implements Serializable {
	private String userCode;
	private String userName;
	private String applyFirst;			//申请中一级域
	private String applySecond;			//申请中二级子域
	private String applyThird;			//申请中三级系统
	private String applyIndentyQues;	//待确认问题单
	private String applyResolveQues;	//待解决问题单
	private String applyCloseQues;		//解决中问题单
	private String dealFirst;     		//待处理	 一级域
	private String dealSecond;			//待处理	 二级子域
	private String dealThird;			//待处理	 三级系统
	private String dealIndentyQues;		//待处理	 确认
	private String dealResolveQues;		//待处理	 解决
	private String dealCloseQues;		//待处理	 关单
	private String hasSysRole;			//是否有基线认定权限
	private String hasQuesRole;			//是否有问题处理权限
	private String noPassSystem;        //驳回系统申请单
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
