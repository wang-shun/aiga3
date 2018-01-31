package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
@Data
public class ArchSvnDbcpSelects implements Serializable {

    private String center;
    private String module;
    private String db;
    private Date insertTime;
    
}
