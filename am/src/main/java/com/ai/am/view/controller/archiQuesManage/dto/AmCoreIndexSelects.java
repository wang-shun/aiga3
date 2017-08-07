package com.ai.am.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
public class AmCoreIndexSelects implements Serializable {

    private String indexName;
    private String indexGroup;
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

    
}
