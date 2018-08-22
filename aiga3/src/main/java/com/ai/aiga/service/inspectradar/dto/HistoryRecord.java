package com.ai.aiga.service.inspectradar.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class HistoryRecord implements Serializable{
	private Date createTime;
    private String totalMark;
    private String aqMark;
    private String rlMark;
    private String jkMark;
    private String gkyMark;
    private String rxkyMark;
    private String pzMark;
    private String rzMark;
    private String fcMark;
}
