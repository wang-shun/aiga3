package com.ai.aiga.view.controller.archiQuesManage.dto;
// Generated 2018-1-29 15:02:07 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

/**
 * ArchSvnDbcp generated by hbm2java
 */
@Data
public class ArchSvnDbcpTotal  implements java.io.Serializable {

    private String center;
    private String module;
    private String db;
    private Long initialSize;
    private Long maxActive;
    private Long maxIdle;
    private Long minIdle;
    private Long maxWait;
    private Date insertTime;
    private String change;
    private String report;
    
}


