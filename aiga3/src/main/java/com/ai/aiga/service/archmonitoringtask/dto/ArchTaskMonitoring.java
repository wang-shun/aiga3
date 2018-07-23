package com.ai.aiga.service.archmonitoringtask.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoring implements Serializable{

     private Date startDate;
     private long checkTotal;
     private long sessionTotal;
	 private long reportTotal;
	 private long collectTotal;
	 private double checkRate;
	 private double sessionRate;
	 private double reportRate;
	 private double collectRate;

	public ArchTaskMonitoring() {}

	public ArchTaskMonitoring(Date startDate, long checkTotal, long sessionTotal, long reportTotal, long collectTotal, double checkRate, double sessionRate, double reportRate, double collectRate) {
		this.startDate = startDate;
		this.checkTotal = checkTotal;
		this.sessionTotal = sessionTotal;
		this.reportTotal = reportTotal;
		this.collectTotal = collectTotal;
		this.checkRate = checkRate;
		this.sessionRate = sessionRate;
		this.reportRate = reportRate;
		this.collectRate = collectRate;
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

	public double getCheckRate() {
		return checkRate;
	}

	public void setCheckRate(double checkRate) {
		this.checkRate = checkRate;
	}

	public double getSessionRate() {
		return sessionRate;
	}

	public void setSessionRate(double sessionRate) {
		this.sessionRate = sessionRate;
	}

	public double getReportRate() {
		return reportRate;
	}

	public void setReportRate(double reportRate) {
		this.reportRate = reportRate;
	}

	public double getCollectRate() {
		return collectRate;
	}

	public void setCollectRate(double collectRate) {
		this.collectRate = collectRate;
	}

	@Override
	public String toString() {
		return "ArchTaskMonitoring{" +
				"startDate=" + startDate +
				", checkTotal=" + checkTotal +
				", sessionTotal=" + sessionTotal +
				", reportTotal=" + reportTotal +
				", collectTotal=" + collectTotal +
				", checkRate=" + checkRate +
				", sessionRate=" + sessionRate +
				", reportRate=" + reportRate +
				", collectRate=" + collectRate +
				'}';
	}
}


