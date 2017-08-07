package com.ai.am.view.json;
// Generated 2017-4-17 19:24:07 by Hibernate Tools 3.2.2.GA


import java.util.Date;

public class BossTestResultRequest  implements java.io.Serializable {


     private Long resultId;
     private Long onlinePlan;
     private String onlinePlanName;
     private Date onlineDate;
     private String testRemark;
     private Long bugCount;
     private String bugRemark;
     private String reason;
     private Long ifSolve;
     private String bugStatus;
     private Long onlineCondition;
     private String remark;
     private String solution;
     private Long planId;
     private String bossName;
     private Long taskId;
     private Long type;

    public BossTestResultRequest() {
    }

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public Long getOnlinePlan() {
		return onlinePlan;
	}

	public void setOnlinePlan(Long onlinePlan) {
		this.onlinePlan = onlinePlan;
	}

	public String getOnlinePlanName() {
		return onlinePlanName;
	}

	public void setOnlinePlanName(String onlinePlanName) {
		this.onlinePlanName = onlinePlanName;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getTestRemark() {
		return testRemark;
	}

	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
	}

	public Long getBugCount() {
		return bugCount;
	}

	public void setBugCount(Long bugCount) {
		this.bugCount = bugCount;
	}

	public String getBugRemark() {
		return bugRemark;
	}

	public void setBugRemark(String bugRemark) {
		this.bugRemark = bugRemark;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getIfSolve() {
		return ifSolve;
	}

	public void setIfSolve(Long ifSolve) {
		this.ifSolve = ifSolve;
	}

	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}

	public Long getOnlineCondition() {
		return onlineCondition;
	}

	public void setOnlineCondition(Long onlineCondition) {
		this.onlineCondition = onlineCondition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}


	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	
   


}


