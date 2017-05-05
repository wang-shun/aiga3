package com.ai.aiga.service.home.dto;

import lombok.Data;

/**
 * @ClassName: CaseCountResponse
 * @author: dongch
 * @date: 2017年5月4日 上午10:19:45
 * @Description:
 * 
 */
@Data
public class CaseCountResponse {
	
	private Integer checkCaseTotalCount;//验收用例数
	private Integer proCaseTotalCount;//生产用例数
	private String checkSucRate;//验收环境自动转换率
	private String proSucRate;//生产环境自动转换率
}

