package com.ai.aiga.service.inspectradar.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
@Data
public class RecentRecord implements Serializable{
    private Long resultId;
    private Long sysId;
    private String totalMark;
    private String aqMark;
    private String rlMark;
    private String jkMark;
    private String gkyMark;
	private String rxkyMark;
    private String pzMark;
    private String rzMark;
    private String fcMark;
    private String createDate;
    private String sponsor;
    private String ext1;
    private String ext2;
    private String ext3;

}
