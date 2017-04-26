package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审-变更概况保存信息
 * @author liuxx
 *@date2017-04-25
 */
@Data
public class ChangeConditionRequest {
	private Long id;
    private String changeTitle;//变更标题
    private String changeGroup;//变更所属专业组
    private String changeObjectType;//变更对象类型
    private String changeMethodType;//变更方法类型
    private String changeReason;//变更申请理由
    private Long planId;//计划id
    private String fileName;//附件名称
    private String ext1;//附件id
}
