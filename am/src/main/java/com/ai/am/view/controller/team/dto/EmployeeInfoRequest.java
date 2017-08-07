package com.ai.am.view.controller.team.dto;

import lombok.Data;

/**
 * @ClassName: EmployeeInfoRequest
 * @author: taoyf
 * @date: 2017年4月20日 下午5:24:30
 * @Description:
 * 
 */
@Data
public class EmployeeInfoRequest {
	
    private Long id;
    private String emName;
    private String phoneNum;
    private String email;
    private String ext1;
    private String ext2;
    private String ext3;

}

