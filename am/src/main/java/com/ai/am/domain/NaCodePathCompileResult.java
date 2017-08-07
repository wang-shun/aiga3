package com.ai.am.domain;
// Generated 2017-4-27 15:07:17 by Hibernate Tools 3.2.2.GA

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
 * NaCodePathCompileResult generated by hbm2java
 */
@Entity
@Table(name = "NA_CODE_PATH_COMPILE_RESULT")
public class NaCodePathCompileResult implements java.io.Serializable {

	private Long resultId;
	private String sysName;
	private String status;
	private String haderrors;
	private String hadwarnings;
	private String isabort;
	private String iscancelled;
	private String targeturi;
	private String value;
	private Date planDate;
	private Long compileNum;
	private String ext1;
	private String ext2;
	private Date startTime;
	private Date stopTime;
	private String result;
	private String sysDesc;

	public NaCodePathCompileResult() {
	}

	public NaCodePathCompileResult(Long resultId) {
		this.resultId = resultId;
	}

	public NaCodePathCompileResult(Long resultId, String sysName, String status, String haderrors, String hadwarnings,
			String isabort, String iscancelled, String targeturi, String value, Date planDate, Long compileNum,
			String ext1, String ext2) {
		this.resultId = resultId;
		this.sysName = sysName;
		this.status = status;
		this.haderrors = haderrors;
		this.hadwarnings = hadwarnings;
		this.isabort = isabort;
		this.iscancelled = iscancelled;
		this.targeturi = targeturi;
		this.value = value;
		this.planDate = planDate;
		this.compileNum = compileNum;
		this.ext1 = ext1;
		this.ext2 = ext2;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_CODE_PATH_COMPILE_R$SEQ")
	@SequenceGenerator(name = "NA_CODE_PATH_COMPILE_R$SEQ", sequenceName = "NA_CODE_PATH_COMPILE_R$SEQ", allocationSize = 1)
	@Column(name = "RESULT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getResultId() {
		return this.resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	@Column(name = "SYS_NAME", length = 100)
	public String getSysName() {
		return this.sysName;
	}

	@Column(name = "SYS_DESC", length = 100)
	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}

	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "STOP_TIME")
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	@Column(name = "RESULT", length = 100)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "HADERRORS", length = 10)
	public String getHaderrors() {
		return this.haderrors;
	}

	public void setHaderrors(String haderrors) {
		this.haderrors = haderrors;
	}

	@Column(name = "HADWARNINGS", length = 10)
	public String getHadwarnings() {
		return this.hadwarnings;
	}

	public void setHadwarnings(String hadwarnings) {
		this.hadwarnings = hadwarnings;
	}

	@Column(name = "ISABORT", length = 10)
	public String getIsabort() {
		return this.isabort;
	}

	public void setIsabort(String isabort) {
		this.isabort = isabort;
	}

	@Column(name = "ISCANCELLED", length = 10)
	public String getIscancelled() {
		return this.iscancelled;
	}

	public void setIscancelled(String iscancelled) {
		this.iscancelled = iscancelled;
	}

	@Column(name = "TARGETURI", length = 1000)
	public String getTargeturi() {
		return this.targeturi;
	}

	public void setTargeturi(String targeturi) {
		this.targeturi = targeturi;
	}

	@Column(name = "VALUE", length = 1000)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PLAN_DATE", length = 7)
	public Date getPlanDate() {
		return this.planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	@Column(name = "COMPILE_NUM", precision = 22, scale = 0)
	public Long getCompileNum() {
		return this.compileNum;
	}

	public void setCompileNum(Long compileNum) {
		this.compileNum = compileNum;
	}

	@Column(name = "EXT_1", length = 1000)
	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "EXT_2", length = 1000)
	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

}
