package com.ai.aiga.service.workFlowNew.dto;
/**
 * 变更评审-结果验证
 * @author liuxx
 * @date 2017年4月25日21:43:25
 */
import lombok.Data;

@Data
public class ChangeResultValidateRequest {
    private Long id;
    private String isChangeValidate;//变更验证
    private String validateContent;//验证内容
    private String testTime;//测试时间
    private String testStep;//测试步骤
    private String testMan;//测试人员
    private Long planId;
    private String fileName;
    private String ext1;
}
