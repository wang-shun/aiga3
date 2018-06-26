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
     private Date finishDate;

    public ArchTaskMonitoring() {
    }

    public ArchTaskMonitoring(long groupId, String paramValue, long cfgTaskId, String indexName, String taskName, String results, String state, Date finishDate) {
       this.groupId = groupId;
       this.paramValue = paramValue;
       this.cfgTaskId = cfgTaskId;
       this.indexName = indexName;
       this.taskName = taskName;
       this.results = results;
       this.state = state;
       this.finishDate = finishDate;
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

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	} 
}


