package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-目标设备
 * @author liuxx
 *@date 2017年4月25日21:07:29
 */

import lombok.Data;
@Data
public class ChangeGoalDeviceRequest {
    private Long id;
    private String resourceType; //资源类型
    private String deviceName;//设备名称
    private String ipAddress;//ip地址
    private String sysName;//归属系统
    private String searchCode;//搜索代码
    private String changeContent;//变更内容
    private Long planId;
    private String fileName;
    private String ext1;
}
