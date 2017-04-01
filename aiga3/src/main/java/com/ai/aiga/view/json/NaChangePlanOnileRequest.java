package com.ai.aiga.view.json;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NaChangePlanOnileRequest {
	private Long onlinePlan;
    private String onlinePlanName;
    private Long planState;
    private String createOpId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date doneDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    private Byte result;
    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;
    private Byte sign;
    private Byte types;
    private Byte timely;
    private Byte isFinished;
    private Byte autoRunResult;
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
	public Long getPlanState() {
		return planState;
	}
	public void setPlanState(Long planState) {
		this.planState = planState;
	}
	public String getCreateOpId() {
		return createOpId;
	}
	public void setCreateOpId(String createOpId) {
		this.createOpId = createOpId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Byte getResult() {
		return result;
	}
	public void setResult(Byte result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public Byte getSign() {
		return sign;
	}
	public void setSign(Byte sign) {
		this.sign = sign;
	}
	public Byte getTypes() {
		return types;
	}
	public void setTypes(Byte types) {
		this.types = types;
	}
	public Byte getTimely() {
		return timely;
	}
	public void setTimely(Byte timely) {
		this.timely = timely;
	}
	public Byte getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Byte isFinished) {
		this.isFinished = isFinished;
	}
	public Byte getAutoRunResult() {
		return autoRunResult;
	}
	public void setAutoRunResult(Byte autoRunResult) {
		this.autoRunResult = autoRunResult;
	}
	@Override
	public String toString() {
		return "NaChangePlanOnileRequest [onlinePlan=" + onlinePlan + ", onlinePlanName=" + onlinePlanName
				+ ", planState=" + planState + ", createOpId=" + createOpId + ", createDate=" + createDate
				+ ", doneDate=" + doneDate + ", planDate=" + planDate + ", result=" + result + ", remark=" + remark
				+ ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", sign=" + sign + ", types=" + types
				+ ", timely=" + timely + ", isFinished=" + isFinished + ", autoRunResult=" + autoRunResult + "]";
	}
    
}
