package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审信息
 * @author liuxx
 *@dete 2017-04-24
 */
@Data
public class ChangeReviewSaveRequest {
	private Long id;//评审id
	private Long fileId;//附件id;
	private String fileName;//附件名称
	private String conclusion;//评审结论
	private String reviewResult;//结果
	private String reviewer;//评审人
	private String reviewDate;//评审日期
	private String planReviewDate;//计划评审日期
	private Long reviewNum;//评审次数
	private String remark;//备注
}
