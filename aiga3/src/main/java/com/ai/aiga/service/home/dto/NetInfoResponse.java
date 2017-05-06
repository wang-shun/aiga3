package com.ai.aiga.service.home.dto;

import lombok.Data;

/**
 * @ClassName: NetInfoResponse
 * @author: dongch
 * @date: 2017年5月5日 下午4:12:26
 * @Description:
 * 
 */
@Data
public class NetInfoResponse {
	
	private Long onlinePlanCount;//上线变更数量
	private Long abnormalCount;//异常数量
	private Long faultCount;//故障数量
	private String reSucRate;//前台成功率
	private String esbSucRate;//esb成功率
	private String cbossSucRate;//cboss成功率
	private String month;
}

