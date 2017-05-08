package com.ai.aiga.view.controller.auto.dto;

import java.util.List;

/**
 *自动化计划关联用例查询参数
 *
 * @author liuxx
 * @date 2017/3/10
 */
public class PlanAutoCaseRequest {
    private Long planId;//主键
    private String autoName;//自动化用例名称
    private Long sysId;//系统大类ID
    private Long sysSubId;//系统子类ID
    private Long busiId;//业务ID
    private Long funId;//功能ID
    private Long scId;//场景ID

    public PlanAutoCaseRequest() {
    }

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getAutoName() {
		return autoName;
	}

	public void setAutoName(String autoName) {
		this.autoName = autoName;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getSysSubId() {
		return sysSubId;
	}

	public void setSysSubId(Long sysSubId) {
		this.sysSubId = sysSubId;
	}

	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public Long getScId() {
		return scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

  
}
