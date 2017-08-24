package com.ai.aiga.domain;

import java.util.Date;

import lombok.Data;
@Data
public class IndexConnect  implements java.io.Serializable {
	private Long indexId;
    private String indexName;
    private String indexGroup;
    private String settMonth;
	private String resultValue;
	
	
	
	public IndexConnect(Long indexId, String indexName, String indexGroup,
			String settMonth, String resultValue) {
		super();
		this.indexId = indexId;
		this.indexName = indexName;
		this.indexGroup = indexGroup;
		this.settMonth = settMonth;
		this.resultValue = resultValue;
	}



	public IndexConnect() {
		super();
	}
}
