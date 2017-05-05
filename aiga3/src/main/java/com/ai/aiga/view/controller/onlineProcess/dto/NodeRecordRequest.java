package com.ai.aiga.view.controller.onlineProcess.dto;

import java.util.Date;

import lombok.Data;
@Data
public class NodeRecordRequest {
	private Long processId;
    private String processName;
    private Date time;
    private Long type;
    private Long node;
    private String ext1;
    private String ext2;
    private String ext3;
    private Long  planId;
    private Date  planDate;
}
