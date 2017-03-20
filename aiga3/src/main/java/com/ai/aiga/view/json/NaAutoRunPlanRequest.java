package com.ai.aiga.view.json;

import java.util.Date;


/**
 * 自动化计划新增/修改表单参数
 * 
 * @author lovestar
 * @date 2017-03-17
 */
public class NaAutoRunPlanRequest {

	private Long planId; // 计划id
	private String planTag;// 计划编号
	private String planName; // 计划名称
	private Long cycleType;// 轮询方式1不轮循 2查询类轮循 3受理类轮循
	private String machineIp;// 默认执行机
	private Long runType;// 执行方式 1立即执行2定时执行3分布式执行
	private Long creatorId;//创建人
	private Date createTime;//创建时间
	private Date updateTime;//更新时间

	public NaAutoRunPlanRequest() {
	}

	public NaAutoRunPlanRequest(Long planId, String planTag, String planName, Long cycleType, String machineIp,
			Long runType) {
		super();
		this.planId = planId;
		this.planTag = planTag;
		this.planName = planName;
		this.cycleType = cycleType;
		this.machineIp = machineIp;
		this.runType = runType;
	}

	public Long getPlanId() {
		return planId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getPlanTag() {
		return planTag;
	}

	public void setPlanTag(String planTag) {
		this.planTag = planTag;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Long getCycleType() {
		return cycleType;
	}

	public void setCycleType(Long cycleType) {
		this.cycleType = cycleType;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public Long getRunType() {
		return runType;
	}

	public void setRunType(Long runType) {
		this.runType = runType;
	}

}
