package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AmCoreIndexGroupParams implements Serializable {

    private String startMonth;
    private String endMonth;
    private long[][] indexId;
  
}
