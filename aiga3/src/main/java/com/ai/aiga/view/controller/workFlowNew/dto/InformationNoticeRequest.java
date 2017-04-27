package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-信息通告
 * @author liuxx
 * @date 2017年4月25日21:56:40
 */
import lombok.Data;

@Data
public class InformationNoticeRequest {
    private Long id;
    private String noticeType;//变更执行开始通报，变更执行结束通报，变更测试结果通报，变更执行异常通报
    private String noticeTime;//通报时间
    private String noticeContent;//通报内容
    private Long planId;
    private String fileName;
    private String ext1;
}
