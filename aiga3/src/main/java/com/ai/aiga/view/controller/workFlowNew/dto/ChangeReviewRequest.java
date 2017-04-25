package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更交付物列表查询条件
 * @author liuxx
 *@dete 2017-04-24
 */
@Data
public class ChangeReviewRequest {
	private Long planId;//计划id
	private String ext1;//附件id
}
