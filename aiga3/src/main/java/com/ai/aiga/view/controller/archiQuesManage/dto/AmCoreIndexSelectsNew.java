package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
public class AmCoreIndexSelectsNew implements Serializable {

    private String indexName;
    private String indexGroup;
    private long groupId;
    private long[] indexId;
    
    
    public long[] getIndexId() {
		return indexId;
	}
	public void setIndexId(long[] indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexGroup() {
		return indexGroup;
	}
	public void setIndexGroup(String indexGroup) {
		this.indexGroup = indexGroup;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}
