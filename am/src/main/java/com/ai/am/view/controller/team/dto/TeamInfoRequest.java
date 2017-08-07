package com.ai.am.view.controller.team.dto;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: TeamInfoRequest
 * @author: taoyf
 * @date: 2017年4月20日 下午5:22:05
 * @Description:
 * 
 */
@Data
public class TeamInfoRequest {
	
    private Long teamId;
    private String teamType;
    private String createOpId;
    private Date createDate;
    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;

}

