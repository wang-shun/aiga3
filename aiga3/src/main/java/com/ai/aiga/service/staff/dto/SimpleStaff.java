package com.ai.aiga.service.staff.dto;

import lombok.Data;

/**
 * @ClassName: SimpleStaff
 * @author: taoyf
 * @date: 2017年4月20日 下午3:00:27
 * @Description:
 * 
 */
@Data
public class SimpleStaff {
	
	private Long staffId;
	private String code;
	private String name;
	private Integer state;
	private Long organizeId;
	private String organizeName;
	private String organizeCode;
	

}

