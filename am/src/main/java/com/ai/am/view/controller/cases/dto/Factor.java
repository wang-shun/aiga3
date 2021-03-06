package com.ai.am.view.controller.cases.dto;

public class Factor {
	
	private Long factorId;
	private String factorName;
	private String remark;
	private Integer factorOrder;
	private Long factorType;

	public Long getFactorType() {
		return factorType;
	}

	public void setFactorType(Long factorType) {
		this.factorType = factorType;
	}

	public Long getFactorId() {
		return factorId;
	}
	public void setFactorId(Long factorId) {
		this.factorId = factorId;
	}
	public String getFactorName() {
		return factorName;
	}
	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getFactorOrder() {
		return factorOrder;
	}
	public void setFactorOrder(Integer factorOrder) {
		this.factorOrder = factorOrder;
	}

	@Override
	public String toString() {
		return "Factor{" +
				"factorId=" + factorId +
				", factorName='" + factorName + '\'' +
				", remark='" + remark + '\'' +
				", factorOrder=" + factorOrder +
				", factorType=" + factorType +
				'}';
	}
}
