package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审-风险评估量化表
 * @author lovestar
 *@date2017-04-27
 */
@Data
public class RiskRatingScaleRequest {
	private Long id;
    private String measureFactor;//衡量因素
    private String condition;//条件
    private String selfEvaluation;//自评
    private String score;//得分
    private Long planId;
    private String fileName;
    private String ext1;

}
