package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AmCoreIndexParams implements Serializable {

    private String indexName;
    private String indexGroup;
    private String startMonth;
    private String endMonth;
}
