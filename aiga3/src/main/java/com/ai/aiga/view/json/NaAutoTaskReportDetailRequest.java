package com.ai.aiga.view.json;

public class NaAutoTaskReportDetailRequest {
	private Long reportId;
    private Long taskId;
    private Long autoId;
    private Long resultId;
    private Byte isSuccess;
    private Byte isBug;
    private String failReason;
    private String bugStaff;
    private Long creatorId;
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getAutoId() {
		return autoId;
	}
	public void setAutoId(Long autoId) {
		this.autoId = autoId;
	}
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public Byte getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Byte isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Byte getIsBug() {
		return isBug;
	}
	public void setIsBug(Byte isBug) {
		this.isBug = isBug;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getBugStaff() {
		return bugStaff;
	}
	public void setBugStaff(String bugStaff) {
		this.bugStaff = bugStaff;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
    
}