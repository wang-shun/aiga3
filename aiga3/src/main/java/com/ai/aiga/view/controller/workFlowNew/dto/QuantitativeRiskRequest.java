package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审-风险评估得分
 * @author liuxx
 *@date 2017-04-27
 */
@Data
public class QuantitativeRiskRequest {
	private Long id;
	private String totalScore;//总分
    private String actualScore;//实际得分
    private String riskGrade;//风险等级
    private String approvalLevel;//评审等级
    private String guaranteeMechanism;//保障机制
    private Long planId;
    private String fileName;
    private String ext1;

}
