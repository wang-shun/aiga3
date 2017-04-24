package com.ai.aiga.view.json.report;

/**
 * @ClassName: CaseConstructionRequest
 * @author: dongch
 * @date: 2017年4月24日 下午3:05:10
 * @Description:
 * 
 */
public class CaseConstructionRequest {
	private Long type;
	private String month;
	private String monthbusi;
	private String monthgroup;
	private Long sysId;
	private Long busiId;
	private Long groupId;
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthbusi() {
		return monthbusi;
	}
	public void setMonthbusi(String monthbusi) {
		this.monthbusi = monthbusi;
	}
	public String getMonthgroup() {
		return monthgroup;
	}
	public void setMonthgroup(String monthgroup) {
		this.monthgroup = monthgroup;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public Long getBusiId() {
		return busiId;
	}
	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}

