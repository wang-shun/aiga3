package com.ai.aiga.domain;
// Generated 2017-2-14 17:02:40 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AigaProTestTask generated by hbm2java
 */
@Entity
@Table(name = "AIGA_PRO_TEST_TASK")
public class AigaProTestTask implements java.io.Serializable {

	private BigDecimal taskId;
	private String taskTag;
	private String taskName;
	private Short curStatus;
	private Date factCompleteTime;
	private BigDecimal distributeStaffid;
	private String distributeStaffname;
	private BigDecimal firmDistributeStaffid;
	private String firmDistributeStaffname;
	private String devFirm;
	private String testFirm;
	private String workOrderExplain;
	private Date createTime;
	private String planTag;
	private BigDecimal planId;
	private BigDecimal creatorId;
	private String creatorName;
	private Short isProFunTest;
	private Short isProMustrelTest;
	private Short curPhase;
	private BigDecimal testType;
	private BigDecimal perfTaskId;

	public AigaProTestTask() {
	}

	public AigaProTestTask(BigDecimal taskId) {
		this.taskId = taskId;
	}

	public AigaProTestTask(BigDecimal taskId, String taskTag, String taskName, Short curStatus, Date factCompleteTime,
			BigDecimal distributeStaffid, String distributeStaffname, BigDecimal firmDistributeStaffid,
			String firmDistributeStaffname, String devFirm, String testFirm, String workOrderExplain, Date createTime,
			String planTag, BigDecimal planId, BigDecimal creatorId, String creatorName, Short isProFunTest,
			Short isProMustrelTest, Short curPhase, BigDecimal testType, BigDecimal perfTaskId) {
		this.taskId = taskId;
		this.taskTag = taskTag;
		this.taskName = taskName;
		this.curStatus = curStatus;
		this.factCompleteTime = factCompleteTime;
		this.distributeStaffid = distributeStaffid;
		this.distributeStaffname = distributeStaffname;
		this.firmDistributeStaffid = firmDistributeStaffid;
		this.firmDistributeStaffname = firmDistributeStaffname;
		this.devFirm = devFirm;
		this.testFirm = testFirm;
		this.workOrderExplain = workOrderExplain;
		this.createTime = createTime;
		this.planTag = planTag;
		this.planId = planId;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.isProFunTest = isProFunTest;
		this.isProMustrelTest = isProMustrelTest;
		this.curPhase = curPhase;
		this.testType = testType;
		this.perfTaskId = perfTaskId;
	}

	@Id
	@Column(name = "TASK_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getTaskId() {
		return this.taskId;
	}

	public void setTaskId(BigDecimal taskId) {
		this.taskId = taskId;
	}

	@Column(name = "TASK_TAG", length = 200)
	public String getTaskTag() {
		return this.taskTag;
	}

	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}

	@Column(name = "TASK_NAME", length = 200)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "CUR_STATUS", precision = 4, scale = 0)
	public Short getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(Short curStatus) {
		this.curStatus = curStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FACT_COMPLETE_TIME", length = 7)
	public Date getFactCompleteTime() {
		return this.factCompleteTime;
	}

	public void setFactCompleteTime(Date factCompleteTime) {
		this.factCompleteTime = factCompleteTime;
	}

	@Column(name = "DISTRIBUTE_STAFFID", precision = 20, scale = 0)
	public BigDecimal getDistributeStaffid() {
		return this.distributeStaffid;
	}

	public void setDistributeStaffid(BigDecimal distributeStaffid) {
		this.distributeStaffid = distributeStaffid;
	}

	@Column(name = "DISTRIBUTE_STAFFNAME", length = 200)
	public String getDistributeStaffname() {
		return this.distributeStaffname;
	}

	public void setDistributeStaffname(String distributeStaffname) {
		this.distributeStaffname = distributeStaffname;
	}

	@Column(name = "FIRM_DISTRIBUTE_STAFFID", precision = 20, scale = 0)
	public BigDecimal getFirmDistributeStaffid() {
		return this.firmDistributeStaffid;
	}

	public void setFirmDistributeStaffid(BigDecimal firmDistributeStaffid) {
		this.firmDistributeStaffid = firmDistributeStaffid;
	}

	@Column(name = "FIRM_DISTRIBUTE_STAFFNAME", length = 200)
	public String getFirmDistributeStaffname() {
		return this.firmDistributeStaffname;
	}

	public void setFirmDistributeStaffname(String firmDistributeStaffname) {
		this.firmDistributeStaffname = firmDistributeStaffname;
	}

	@Column(name = "DEV_FIRM", length = 200)
	public String getDevFirm() {
		return this.devFirm;
	}

	public void setDevFirm(String devFirm) {
		this.devFirm = devFirm;
	}

	@Column(name = "TEST_FIRM", length = 200)
	public String getTestFirm() {
		return this.testFirm;
	}

	public void setTestFirm(String testFirm) {
		this.testFirm = testFirm;
	}

	@Column(name = "WORK_ORDER_EXPLAIN", length = 400)
	public String getWorkOrderExplain() {
		return this.workOrderExplain;
	}

	public void setWorkOrderExplain(String workOrderExplain) {
		this.workOrderExplain = workOrderExplain;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "PLAN_TAG", length = 200)
	public String getPlanTag() {
		return this.planTag;
	}

	public void setPlanTag(String planTag) {
		this.planTag = planTag;
	}

	@Column(name = "PLAN_ID", precision = 20, scale = 0)
	public BigDecimal getPlanId() {
		return this.planId;
	}

	public void setPlanId(BigDecimal planId) {
		this.planId = planId;
	}

	@Column(name = "CREATOR_ID", precision = 20, scale = 0)
	public BigDecimal getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(BigDecimal creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATOR_NAME", length = 200)
	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "IS_PRO_FUN_TEST", precision = 4, scale = 0)
	public Short getIsProFunTest() {
		return this.isProFunTest;
	}

	public void setIsProFunTest(Short isProFunTest) {
		this.isProFunTest = isProFunTest;
	}

	@Column(name = "IS_PRO_MUSTREL_TEST", precision = 4, scale = 0)
	public Short getIsProMustrelTest() {
		return this.isProMustrelTest;
	}

	public void setIsProMustrelTest(Short isProMustrelTest) {
		this.isProMustrelTest = isProMustrelTest;
	}

	@Column(name = "CUR_PHASE", precision = 4, scale = 0)
	public Short getCurPhase() {
		return this.curPhase;
	}

	public void setCurPhase(Short curPhase) {
		this.curPhase = curPhase;
	}

	@Column(name = "TEST_TYPE", precision = 20, scale = 0)
	public BigDecimal getTestType() {
		return this.testType;
	}

	public void setTestType(BigDecimal testType) {
		this.testType = testType;
	}

	@Column(name = "PERF_TASK_ID", precision = 20, scale = 0)
	public BigDecimal getPerfTaskId() {
		return this.perfTaskId;
	}

	public void setPerfTaskId(BigDecimal perfTaskId) {
		this.perfTaskId = perfTaskId;
	}

}
