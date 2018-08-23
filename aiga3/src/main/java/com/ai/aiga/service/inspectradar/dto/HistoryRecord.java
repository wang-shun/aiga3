package com.ai.aiga.service.inspectradar.dto;

import lombok.Data;

@Data
public class HistoryRecord {
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
