package com.ai.aiga.service.home.dto;

import java.util.Date;
import java.util.List;

import com.ai.aiga.domain.NaProcessNodeRecord;

import lombok.Data;

/**
 * @ClassName: ProcessNodeResponse
 * @author: dongch
 * @date: 2017年5月6日 上午10:06:35
 * @Description:
 * 
 */
@Data
public class ProcessNodeResponse {
	
	private Long maxNum;
	private Integer activeNum;
	private Integer id;
	private List<NaProcessNodeRecord> flowLIst;
}

