package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审配置更新
 * @author liuxx
 *@date 2017年4月25日21:02:03
 */
@Data
public class ChangeUpdateRequest {
    private Long id;
    private String configIsUpdate;//配置是否更新
    private String resourceType;//资源类型
    private String name;//名称
    private String configUpdateBefore;//配置更新前
    private String configUpdateAfter;//配置更新后
    private Long planId;
    private String fileName;
    private String ext1;
}
