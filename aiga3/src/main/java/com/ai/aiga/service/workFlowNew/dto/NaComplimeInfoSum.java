package com.ai.aiga.service.workFlowNew.dto;

import java.io.Serializable;

import lombok.Data;

/** * @author LIUXX
    * @date 
    */
@Data
public class NaComplimeInfoSum implements Serializable{
	 private String sysName ; //系统名称
	private Long maxNum;//每个系统编译次数

}
