package com.ai.aiga.service.workFlowNew.dto;


import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @author liuxx
 *@date 2017年5月4日10:46:59
 */
@Data
public class NaCodePathCompile implements java.io.Serializable {


	private String sysName;
	private Long maxNum;
	private Map<String , List<NaCodePathCompileExp>>	 naCodePathCompileExp;


}
