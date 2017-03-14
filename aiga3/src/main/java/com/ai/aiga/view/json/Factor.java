package com.ai.aiga.view.json;

public class Factor {
	
	private Long factorId;
	private String factorName;
	private String remark;
	
	
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
	@Override
	public String toString() {
		return "Factor [factorId=" + factorId + ", factorName=" + factorName + ", remark=" + remark + "]";
	}
	

}
