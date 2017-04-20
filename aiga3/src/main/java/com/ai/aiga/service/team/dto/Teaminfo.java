package com.ai.aiga.service.team.dto;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: Teaminfo
 * @author: taoyf
 * @date: 2017年4月20日 下午5:39:52
 * @Description:
 * 
 */
@Data
public class Teaminfo {
	
    private Long teamId;
    private String teamType;
    private String createOpId;
    private Date createDate;
    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;
    
    private String creatorName;
    

}

