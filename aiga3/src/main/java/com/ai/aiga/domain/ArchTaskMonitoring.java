package com.ai.aiga.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ArchTaskMonitoring implements Serializable{	
	 private long groupId;
	 private String paramValue;
	 private long cfgTaskId;
	 private String indexName;
     private String taskName;
     private String results;
     private String state;
     private Date startDate;
     private long total;
     private long successTotal;
     private long failTotal;

    public ArchTaskMonitoring() {
    }

    public ArchTaskMonitoring(long groupId, String paramValue, long cfgTaskId, String indexName, String taskName, String results, String state,
    	Date startDate, long total, long successTotal, long failTotal) {
       this.groupId = groupId;
       this.paramValue = paramValue;
       this.cfgTaskId = cfgTaskId;
       this.indexName = indexName;
       this.taskName = taskName;
       this.results = results;
       this.state = state;
       this.startDate = startDate;       
       this.total = total;
       this.successTotal = successTotal;
       this.failTotal = failTotal;
    }

     public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSuccessTotal() {
		return successTotal;
	}

	public void setSuccessTotal(long successTotal) {
		this.successTotal = successTotal;
	}

	public long getFailTotal() {
		return failTotal;
	}

	public void setFailTotal(long failTotal) {
		this.failTotal = failTotal;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public long getCfgTaskId() {
		return cfgTaskId;
	}

	public void setCfgTaskId(long cfgTaskId) {
		this.cfgTaskId = cfgTaskId;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	} 
}


