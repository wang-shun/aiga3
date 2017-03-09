package com.ai.aiga.domain;
// Generated 2017-3-2 10:38:17 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
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
 * AigaSystemFolder generated by hbm2java
 */
@Entity
@Table(name = "AIGA_SYSTEM_FOLDER")
public class AigaSystemFolder implements java.io.Serializable {

	private BigDecimal sysId;
	private String sysName;
	private Date createTime;
	private Date updateTime;
	private String remarks;
	private String firm;
	private Short importantClass;
	private Short sysOfDomain;
	private Short isInvalid;

	public AigaSystemFolder() {
	}

	public AigaSystemFolder(BigDecimal sysId) {
		this.sysId = sysId;
	}

	public AigaSystemFolder(BigDecimal sysId, String sysName, Date createTime, Date updateTime, String remarks,
			String firm, Short importantClass, Short sysOfDomain, Short isInvalid) {
		this.sysId = sysId;
		this.sysName = sysName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remarks = remarks;
		this.firm = firm;
		this.importantClass = importantClass;
		this.sysOfDomain = sysOfDomain;
		this.isInvalid = isInvalid;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_SYSTEM_FOLDER$SEQ")
	@SequenceGenerator(name="AIGA_SYSTEM_FOLDER$SEQ",sequenceName="AIGA_SYSTEM_FOLDER$SEQ",allocationSize=1)
	@Column(name = "SYS_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getSysId() {
		return this.sysId;
	}

	public void setSysId(BigDecimal sysId) {
		this.sysId = sysId;
	}

	@Column(name = "SYS_NAME", length = 2000)
	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "REMARKS", length = 4000)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "FIRM")
	public String getFirm() {
		return this.firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	@Column(name = "IMPORTANT_CLASS", precision = 4, scale = 0)
	public Short getImportantClass() {
		return this.importantClass;
	}

	public void setImportantClass(Short importantClass) {
		this.importantClass = importantClass;
	}

	@Column(name = "SYS_OF_DOMAIN", precision = 4, scale = 0)
	public Short getSysOfDomain() {
		return this.sysOfDomain;
	}

	public void setSysOfDomain(Short sysOfDomain) {
		this.sysOfDomain = sysOfDomain;
	}

	@Column(name = "IS_INVALID", precision = 4, scale = 0)
	public Short getIsInvalid() {
		return this.isInvalid;
	}

	public void setIsInvalid(Short isInvalid) {
		this.isInvalid = isInvalid;
	}

}
