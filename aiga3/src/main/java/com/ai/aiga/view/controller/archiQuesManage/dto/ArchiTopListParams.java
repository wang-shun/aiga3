package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArchiTopListParams implements Serializable {

    private String indexName;
    private String indexGroup;
    private String startMonth;
    private String endMonth;
    private String key1;
    private String key2;
    private String key3;   
}
