package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ArchSessionConnectResourceShow implements Serializable {

    private String fromSysName;
    private long total;
    private String remark;
    private String dbName;
    private String settMonth;
    
}
