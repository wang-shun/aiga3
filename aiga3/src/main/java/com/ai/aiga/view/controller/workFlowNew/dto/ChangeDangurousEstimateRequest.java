package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-变更风险评估
 * @author liuxx
 * @date 2017年4月25日21:56:11
 */
import lombok.Data;

@Data
public class ChangeDangurousEstimateRequest {

    private Long id;
    private String types;//对生产系统的影响，对安全的影响，对集团考核的影响
    private String contents;//具体内容
    private Long planId;
    private String fileName;
    private String ext1;
}
