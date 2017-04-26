package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-业务验证
 * @author liuxx
 * @date 2017年4月25日21:44:12
 */
import lombok.Data;

@Data
public class ChangeServiceValidateRequest {
    private Long id;
    private String isServiceValidate;//是否业务验证
    private String sysName;//业务系统
    private String serviceFunction;//业务功能
    private String testTime;//测试时间
    private String testStep;//测试步骤
    private String testMan;//测试人员
    private Long planId;
    private String fileName;
    private String ext1;
}
