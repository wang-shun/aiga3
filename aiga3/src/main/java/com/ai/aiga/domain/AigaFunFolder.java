package com.ai.aiga.domain;
// Generated 2017-3-2 10:38:17 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AigaFunFolder generated by hbm2java
 */
@Entity
@Table(name = "AIGA_FUN_FOLDER")
public class AigaFunFolder implements java.io.Serializable {

	private BigDecimal funId;
	private String sysName;
	private Date createTime;
	private Date updateTime;
	private BigDecimal sysId;
	private String busiLabel;
	private String baseFunLabel;
	private String dataCheckScript;
	private Short importantClass;
	private String menuPath;
	private Short funType;
	private String funDesc;
	private Short isInvalid;
	private BigDecimal addReasonType;
	private String addReason;
	private Short efficiencyTestType;
	private Short isEfficiencyTest;
	private Short devFirm;
	private String sysIdTemp;
	private BigDecimal subSysId;
	private String subSysIdTemp;
	private BigDecimal operatorId;
	private String operatorName;
	private BigDecimal creatorId;
	private String creatorName;

	public AigaFunFolder() {
	}

	public AigaFunFolder(BigDecimal funId) {
		this.funId = funId;
	}

	public AigaFunFolder(BigDecimal funId, String sysName, Date createTime, Date updateTime, BigDecimal sysId,
			String busiLabel, String baseFunLabel, String dataCheckScript, Short importantClass, String menuPath,
			Short funType, String funDesc, Short isInvalid, BigDecimal addReasonType, String addReason,
			Short efficiencyTestType, Short isEfficiencyTest, Short devFirm, String sysIdTemp, BigDecimal subSysId,
			String subSysIdTemp, BigDecimal operatorId, String operatorName, BigDecimal creatorId, String creatorName) {
		this.funId = funId;
		this.sysName = sysName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.sysId = sysId;
		this.busiLabel = busiLabel;
		this.baseFunLabel = baseFunLabel;
		this.dataCheckScript = dataCheckScript;
		this.importantClass = importantClass;
		this.menuPath = menuPath;
		this.funType = funType;
		this.funDesc = funDesc;
		this.isInvalid = isInvalid;
		this.addReasonType = addReasonType;
		this.addReason = addReason;
		this.efficiencyTestType = efficiencyTestType;
		this.isEfficiencyTest = isEfficiencyTest;
		this.devFirm = devFirm;
		this.sysIdTemp = sysIdTemp;
		this.subSysId = subSysId;
		this.subSysIdTemp = subSysIdTemp;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
	}

	@Id

	@Column(name = "FUN_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getFunId() {
		return this.funId;
	}

	public void setFunId(BigDecimal funId) {
		this.funId = funId;
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

	@Column(name = "SYS_ID", precision = 20, scale = 0)
	public BigDecimal getSysId() {
		return this.sysId;
	}

	public void setSysId(BigDecimal sysId) {
		this.sysId = sysId;
	}

	@Column(name = "BUSI_LABEL", length = 2000)
	public String getBusiLabel() {
		return this.busiLabel;
	}

	public void setBusiLabel(String busiLabel) {
		this.busiLabel = busiLabel;
	}

	@Column(name = "BASE_FUN_LABEL", length = 2000)
	public String getBaseFunLabel() {
		return this.baseFunLabel;
	}

	public void setBaseFunLabel(String baseFunLabel) {
		this.baseFunLabel = baseFunLabel;
	}

	@Column(name = "DATA_CHECK_SCRIPT", length = 4000)
	public String getDataCheckScript() {
		return this.dataCheckScript;
	}

	public void setDataCheckScript(String dataCheckScript) {
		this.dataCheckScript = dataCheckScript;
	}

	@Column(name = "IMPORTANT_CLASS", precision = 4, scale = 0)
	public Short getImportantClass() {
		return this.importantClass;
	}

	public void setImportantClass(Short importantClass) {
		this.importantClass = importantClass;
	}

	@Column(name = "MENU_PATH", length = 2000)
	public String getMenuPath() {
		return this.menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	@Column(name = "FUN_TYPE", precision = 4, scale = 0)
	public Short getFunType() {
		return this.funType;
	}

	public void setFunType(Short funType) {
		this.funType = funType;
	}

	@Column(name = "FUN_DESC", length = 2000)
	public String getFunDesc() {
		return this.funDesc;
	}

	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
	}

	@Column(name = "IS_INVALID", precision = 4, scale = 0)
	public Short getIsInvalid() {
		return this.isInvalid;
	}

	public void setIsInvalid(Short isInvalid) {
		this.isInvalid = isInvalid;
	}

	@Column(name = "ADD_REASON_TYPE", precision = 20, scale = 0)
	public BigDecimal getAddReasonType() {
		return this.addReasonType;
	}

	public void setAddReasonType(BigDecimal addReasonType) {
		this.addReasonType = addReasonType;
	}

	@Column(name = "ADD_REASON", length = 4000)
	public String getAddReason() {
		return this.addReason;
	}

	public void setAddReason(String addReason) {
		this.addReason = addReason;
	}

	@Column(name = "EFFICIENCY_TEST_TYPE", precision = 4, scale = 0)
	public Short getEfficiencyTestType() {
		return this.efficiencyTestType;
	}

	public void setEfficiencyTestType(Short efficiencyTestType) {
		this.efficiencyTestType = efficiencyTestType;
	}

	@Column(name = "IS_EFFICIENCY_TEST", precision = 4, scale = 0)
	public Short getIsEfficiencyTest() {
		return this.isEfficiencyTest;
	}

	public void setIsEfficiencyTest(Short isEfficiencyTest) {
		this.isEfficiencyTest = isEfficiencyTest;
	}

	@Column(name = "DEV_FIRM", precision = 4, scale = 0)
	public Short getDevFirm() {
		return this.devFirm;
	}

	public void setDevFirm(Short devFirm) {
		this.devFirm = devFirm;
	}

	@Column(name = "SYS_ID_TEMP")
	public String getSysIdTemp() {
		return this.sysIdTemp;
	}

	public void setSysIdTemp(String sysIdTemp) {
		this.sysIdTemp = sysIdTemp;
	}

	@Column(name = "SUB_SYS_ID", precision = 20, scale = 0)
	public BigDecimal getSubSysId() {
		return this.subSysId;
	}

	public void setSubSysId(BigDecimal subSysId) {
		this.subSysId = subSysId;
	}

	@Column(name = "SUB_SYS_ID_TEMP")
	public String getSubSysIdTemp() {
		return this.subSysIdTemp;
	}

	public void setSubSysIdTemp(String subSysIdTemp) {
		this.subSysIdTemp = subSysIdTemp;
	}

	@Column(name = "OPERATOR_ID", precision = 20, scale = 0)
	public BigDecimal getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(BigDecimal operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "OPERATOR_NAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "CREATOR_ID", precision = 20, scale = 0)
	public BigDecimal getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(BigDecimal creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATOR_NAME")
	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
