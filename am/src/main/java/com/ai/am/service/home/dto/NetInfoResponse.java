package com.ai.am.service.home.dto;

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
	
//	private Long onlinePlanCount;//上线变更数量
//	private Long abnormalCount;//异常数量
//	private Long faultCount;//故障数量
//	private String reSucRate;//前台成功率
//	private String esbSucRate;//esb成功率
//	private String cbossSucRate;//cboss成功率
//	private String month;
	private String [] month = new String[6];//月份数组
	private long[] onlinePlan = new long[6];
	private long[] abNormal = new long[6];
	private long[] fault = new long[6];
	private String [] reSucRate = new String[6];
	private String [] esbSucRate = new String[6];
	private String [] cbossSucRate = new String[6];
}

