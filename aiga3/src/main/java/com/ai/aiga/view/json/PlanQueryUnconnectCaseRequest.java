package com.ai.aiga.view.json;

import java.util.Date;

/**
 * 自动化计划查询未关联用例
 * 
 * @author lovestar
 * @date 2017-03-17
 */
public class PlanQueryUnconnectCaseRequest {

	private Long planId; // 计划id
	private String caseName; // 用例名称
	private Long sysName;// 系统大类
	private Long subSysName;// 系统子类
	private Long funcName;// 功能点

	public PlanQueryUnconnectCaseRequest() {
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Long getSysName() {
		return sysName;
	}

	public void setSysName(Long sysName) {
		this.sysName = sysName;
	}

	public Long getSubSysName() {
		return subSysName;
	}

	public void setSubSysName(Long subSysName) {
		this.subSysName = subSysName;
	}

	public Long getFuncName() {
		return funcName;
	}

	public void setFuncName(Long funcName) {
		this.funcName = funcName;
	}

	public PlanQueryUnconnectCaseRequest(Long planId, String caseName, Long sysName, Long subSysName, Long funcName) {
		super();
		this.planId = planId;
		this.caseName = caseName;
		this.sysName = sysName;
		this.subSysName = subSysName;
		this.funcName = funcName;
	}

}
