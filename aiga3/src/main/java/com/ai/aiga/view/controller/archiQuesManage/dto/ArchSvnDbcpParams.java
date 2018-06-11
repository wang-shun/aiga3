package com.ai.aiga.view.controller.archiQuesManage.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhuchao on 18-6-1.
 */
@Data
public class ArchSvnDbcpParams {
    private String centerName;
    private String center;
    private String module;
    private String db;
    private Long initialSize;
    private Long maxActive;
    private Long maxIdle;
    private Long minIdle;
    private Long maxWait;
    private Date insertTime;
    private String isChange;
}