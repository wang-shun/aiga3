package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ArchSessionConnectResourceFront implements Serializable {

	private String id;
    private String fromSysName;
    private long total;
    private long total1;
    private long total7;
    private long total31;
    private double persent;
    private double persent1;
    private double persent7;
    private double persent31;
    private String settMonth;
    
}
