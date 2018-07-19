package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ArchSessionConnectResourceParams implements Serializable {

    private String startMonth;
    private String endMonth;
    private String fromSysName;
    private String dbName;
    
}
