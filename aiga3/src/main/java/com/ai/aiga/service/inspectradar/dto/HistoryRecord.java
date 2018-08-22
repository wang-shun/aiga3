package com.ai.aiga.service.inspectradar.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class HistoryRecord implements Serializable{
	private String createDate;
    private float totalMark;
    private float aqMark;
    private float rlMark;
    private float jkMark;
    private float gkyMark;
    private float rxkyMark;
    private float pzMark;
    private float rzMark;
    private float fcMark;
}
