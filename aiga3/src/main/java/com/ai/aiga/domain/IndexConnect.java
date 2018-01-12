package com.ai.aiga.domain;

import java.util.Date;

import lombok.Data;
@Data
public class IndexConnect  implements java.io.Serializable {
	private Long indexId;
    private String indexName;
    private String key2;
    private String key3;
    private String indexGroup;
    private String settMonth;
	private String resultValue;
    private Date insertTime;
	
	
	
	public IndexConnect(Long indexId, String indexName, String key2, String key3, String indexGroup,
			String settMonth, String resultValue, Date insertTime) {
		super();
		this.indexId = indexId;
		this.indexName = indexName;
		this.key2 = key2;
		this.key3 = key3;
		this.indexGroup = indexGroup;
		this.settMonth = settMonth;
		this.resultValue = resultValue;
		this.insertTime = insertTime;
	}



	public IndexConnect() {
		super();
	}
}
