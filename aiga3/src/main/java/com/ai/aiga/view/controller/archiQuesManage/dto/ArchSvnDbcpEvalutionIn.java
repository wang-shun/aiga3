package com.ai.aiga.view.controller.archiQuesManage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhuchao on 18-6-14.
 */

@Data
public class ArchSvnDbcpEvalutionIn implements Serializable {
    private  Long  tpsnumbers;
    private String timetype;
    private Long serviceCalledTime;
    private Long deployednumbers;
    private String databases;

}