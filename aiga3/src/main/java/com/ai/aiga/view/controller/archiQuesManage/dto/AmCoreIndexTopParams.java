package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AmCoreIndexTopParams implements Serializable {

    private String startMonth;
    private String endMonth;
    private String[] indexName;
    private long[][] indexId;
  
}
