package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoring implements Serializable{

	//	 private long groupId;
//	 private String paramValue;
//	 private long cfgTaskId;
//	 private String indexName;
//   private String taskName;
//   private String results;
//	 private long total;
//   private String state;
     private Date startDate;
     private long checkTotal;
     private long sessionTotal;
	 private long reportTotal;
	 private long collectTotal;
	 private double successRate;



	public ArchTaskMonitoring() {
	}

	public ArchTaskMonitoring(Date startDate, long checkTotal, long sessionTotal, long reportTotal, long collectTotal, double successRate) {
		this.startDate = startDate;
		this.checkTotal = checkTotal;
		this.sessionTotal = sessionTotal;
		this.reportTotal = reportTotal;
		this.collectTotal = collectTotal;
		this.successRate = successRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public long getCheckTotal() {
		return checkTotal;
	}

	public void setCheckTotal(long checkTotal) {
		this.checkTotal = checkTotal;
	}

	public long getSessionTotal() {
		return sessionTotal;
	}

	public void setSessionTotal(long sessionTotal) {
		this.sessionTotal = sessionTotal;
	}

	public long getReportTotal() {
		return reportTotal;
	}

	public void setReportTotal(long reportTotal) {
		this.reportTotal = reportTotal;
	}

	public long getCollectTotal() {
		return collectTotal;
	}

	public void setCollectTotal(long collectTotal) {
		this.collectTotal = collectTotal;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	@Override
	public String toString() {
		return "ArchTaskMonitoring{" +
				"startDate=" + startDate +
				", checkTotal=" + checkTotal +
				", sessionTotal=" + sessionTotal +
				", reportTotal=" + reportTotal +
				", collectTotal=" + collectTotal +
				", successRate=" + successRate +
				'}';
	}
}


