package com.ai.am.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArchDbConnectSelects implements Serializable {

	private String indexName;
    private Long indexId;
    private String key1;
    private String key2;
    private String key3;

}
