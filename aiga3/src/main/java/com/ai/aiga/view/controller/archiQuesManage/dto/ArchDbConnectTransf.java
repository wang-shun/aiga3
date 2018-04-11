package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ArchDbConnectTransf implements Serializable {

    private Long indexId;
    private String settMonth;
    private String key1;
    private String key2;
    private String key3;
    private String resultValue;
    private Long groupId;
    private String insertTime;

}
