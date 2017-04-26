package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;
/**
 * 变更评审-时间及人员
 * @author liuxx
 * @date 2017年4月25日20:50:32
 *
 */
@Data
public class ChangeTimePersonRequest {
    private Long id;
    private String applyTime;//变更申请提交时间
    private String startRunTime;//执行开始时间
    private String stopRunTime;//执行结束时间
    private String personRole;//人员角色
    private String personName;//姓名
    private String personPhone;//联系电话
    private String personGroup;//所属专业组
    private String personDuty;//人员职责
    private Long planId;//
    private String fileName;
    private String ext1;

}
