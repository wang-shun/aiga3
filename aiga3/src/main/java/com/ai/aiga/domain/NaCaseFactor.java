package com.ai.aiga.domain;
// Generated 2017-3-1 10:25:12 by Hibernate Tools 3.2.2.GA

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

/**
 * NaCaseFactor generated by hbm2java
 */
@Entity
@Table(name = "NA_CASE_FACTOR")
public class NaCaseFactor implements java.io.Serializable {

	private long factorId;
	private long caseId;
	private String factorName;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updateId;
	private Date updateTime;

	public NaCaseFactor() {
	}

	public NaCaseFactor(long factorId, long caseId, String factorName) {
		this.factorId = factorId;
		this.caseId = caseId;
		this.factorName = factorName;
	}

	public NaCaseFactor(long factorId, long caseId, String factorName, String remark, Long creatorId, Date createTime,
			Long updateId, Date updateTime) {
		this.factorId = factorId;
		this.caseId = caseId;
		this.factorName = factorName;
		this.remark = remark;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.updateId = updateId;
		this.updateTime = updateTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_CASE_FACTOR$SEQ")
	@SequenceGenerator(name = "NA_CASE_FACTOR$SEQ", sequenceName = "NA_CASE_FACTOR$SEQ", allocationSize = 1)
	@Column(name = "FACTOR_ID", unique = true, nullable = false, precision = 14, scale = 0)
	public long getFactorId() {
		return this.factorId;
	}

	public void setFactorId(long factorId) {
		this.factorId = factorId;
	}

	@Column(name = "CASE_ID", nullable = false, precision = 14, scale = 0)
	public long getCaseId() {
		return this.caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	@Column(name = "FACTOR_NAME", nullable = false, length = 100)
	public String getFactorName() {
		return this.factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	@Column(name = "REMARK", length = 400)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATOR_ID", precision = 14, scale = 0)
	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_ID", precision = 14, scale = 0)
	public Long getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 11)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
