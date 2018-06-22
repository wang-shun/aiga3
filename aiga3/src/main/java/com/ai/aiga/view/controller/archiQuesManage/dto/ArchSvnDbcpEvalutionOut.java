package com.ai.aiga.view.controller.archiQuesManage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhuchao on 18-6-14.
 */
@Data
public class ArchSvnDbcpEvalutionOut implements Serializable{
    private  String database;
    private String connections;
    private  String minIdle;
    private String maxIdle;
    private String choose;
    private String maxActive;
    private String connectionFactor;
    private Long min;
    private Long max;
    private Long fact;
}
