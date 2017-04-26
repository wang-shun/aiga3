package com.ai.aiga.service.workFlowNew.dto;
/**
 * 变更评审-告警屏蔽
 * @author liuxx
 */
import lombok.Data;

@Data
public class WarningShieldRequest {
    private Long id;
    private String shieldHost; //屏蔽主机
    private String ipAddress;//ip地址
    private String shieldTime;//告警屏蔽时间
    private String shieldContent;//屏蔽内容
    private Long planId;
    private String fileName;
    private String ext1;
}
