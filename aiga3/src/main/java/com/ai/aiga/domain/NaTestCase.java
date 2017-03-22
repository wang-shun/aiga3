package com.ai.aiga.domain;
// Generated 2017-3-1 10:25:12 by Hibernate Tools 3.2.2.GA

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaTestCase generated by hbm2java
 */
@Entity
@Table(name = "NA_TEST_CASE")
public class NaTestCase implements java.io.Serializable {

	private long testId;
	private String testName;
	private long caseId;
	private String testType;
	private byte caseType;
	private String testDesc;
	private String preResult;
	private Short important;
	private Long sysId;
	private Long sysSubId;
	private Long funId;
	private Long scId;
	private Long busiId;
	private Long creatorId;
	private Date createTime;
	private Long updateId;
	private Date updateTime;

	public NaTestCase() {
	}

	public NaTestCase(long testId, String testName, long caseId, byte caseType) {
		this.testId = testId;
		this.testName = testName;
		this.caseId = caseId;
		this.caseType = caseType;
	}

	public NaTestCase(long testId, String testName, long caseId, String testType, byte caseType, String testDesc,
			String preResult, Short important, Long sysId, Long sysSubId, Long funId, Long scId, Long busiId,
			Long creatorId, Date createTime, Long updateId, Date updateTime) {
		this.testId = testId;
		this.testName = testName;
		this.caseId = caseId;
		this.testType = testType;
		this.caseType = caseType;
		this.testDesc = testDesc;
		this.preResult = preResult;
		this.important = important;
		this.sysId = sysId;
		this.sysSubId = sysSubId;
		this.funId = funId;
		this.scId = scId;
		this.busiId = busiId;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.updateId = updateId;
		this.updateTime = updateTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_TEST_CASE$SEQ")
	@SequenceGenerator(name = "NA_TEST_CASE$SEQ", sequenceName = "NA_TEST_CASE$SEQ", allocationSize = 1)
	@Column(name = "TEST_ID", unique = true, nullable = false, precision = 14, scale = 0)
	public long getTestId() {
		return this.testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	@Column(name = "TEST_NAME", nullable = false, length = 400)
	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Column(name = "CASE_ID", nullable = false, precision = 14, scale = 0)
	public long getCaseId() {
		return this.caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	@Column(name = "TEST_TYPE", length = 40)
	public String getTestType() {
		return this.testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	@Column(name = "CASE_TYPE", nullable = false, precision = 2, scale = 0)
	public byte isCaseType() {
		return this.caseType;
	}

	public void setCaseType(byte caseType) {
		this.caseType = caseType;
	}

	@Lob
	@Column(name = "TEST_DESC")
	public String getTestDesc() {
		return this.testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	@Lob
	@Column(name = "PRE_RESULT")
	public String getPreResult() {
		return this.preResult;
	}

	public void setPreResult(String preResult) {
		this.preResult = preResult;
	}

	@Column(name = "IMPORTANT", precision = 3, scale = 0)
	public Short getImportant() {
		return this.important;
	}

	public void setImportant(Short important) {
		this.important = important;
	}

	@Column(name = "SYS_ID", precision = 14, scale = 0)
	public Long getSysId() {
		return this.sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	@Column(name = "SYS_SUB_ID", precision = 14, scale = 0)
	public Long getSysSubId() {
		return this.sysSubId;
	}

	public void setSysSubId(Long sysSubId) {
		this.sysSubId = sysSubId;
	}

	@Column(name = "FUN_ID", precision = 14, scale = 0)
	public Long getFunId() {
		return this.funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	@Column(name = "SC_ID", precision = 14, scale = 0)
	public Long getScId() {
		return this.scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

	@Column(name = "BUSI_ID", precision = 14, scale = 0)
	public Long getBusiId() {
		return this.busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
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
