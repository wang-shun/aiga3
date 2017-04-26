package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审-变更内容保存
 * @author liuxx
 *@date2017-04-25
 */
@Data
public class ChangeContentsRequest {
	 private Long id;
     private String questionDesc;//问题描述
     private String changeObject;//变更对象
     private String sysName;//业务系统归属
     private String changeGoal;//变更目标
     private String changeDangerous;//变更风险等级
     private String impactRange;//变更影响范围
     private String groupCheckImpact;//集团考核影响
     private String groupImpact;//集团直采影响
     private String imfoImpact;//信息安全影响
     private Long planId;//计划id
     private String fileName;//附件名称
     private String ext1;//附件id
}
