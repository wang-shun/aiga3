package com.ai.aiga.view.controller.workFlowNew.dto;

import java.util.Date;

import lombok.Data;

/**
 * 保存变更评审结论
 * @author liuxx
 * @date 2014-04-25
  *
 */
@Data
public class ChangeReviewResultRequest {

	private Long id;//评审结论id
    private Long planId;//计划id
    private Long conclusion;//结果
    private String reviewResult;//结论
    private Date reviewDate;//实际评审时间
    private String reviewer;//评审人
    private String remark;
    private String fileId;//附件id
    private Date planReviewDate ;// 计划评审时间
    private  Long reviewNum ;//评审次数
}
