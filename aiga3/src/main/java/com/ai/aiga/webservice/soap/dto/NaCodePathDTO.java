package com.ai.aiga.webservice.soap.dto;
// Generated 2017-3-29 11:29:38 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class NaCodePathDTO implements java.io.Serializable {

	private Long ids;
	private String sysName;
	private String modelName;
	private String packageName;
	private Long state;
	private String remark;
	private Date planDate;
	public NaCodePathDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NaCodePathDTO(Long ids, String sysName, String modelName, String packageName, Long state, String remark,
			Date planDate) {
		super();
		this.ids = ids;
		this.sysName = sysName;
		this.modelName = modelName;
		this.packageName = packageName;
		this.state = state;
		this.remark = remark;
		this.planDate = planDate;
	}
	public Long getIds() {
		return ids;
	}
	public void setIds(Long ids) {
		this.ids = ids;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

}
