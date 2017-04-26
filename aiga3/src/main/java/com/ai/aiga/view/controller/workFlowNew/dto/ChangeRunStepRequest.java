package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-变更实施步骤
 * @author liuxx
 * @date 2017年4月25日21:41:54
 */
import lombok.Data;

@Data
public class ChangeRunStepRequest {
    private Long id;
    private String isConfigArg; //是否配置参数
    private String isRestartHost;//是否重启主机
    private String isRestartCollection;//是否重启集群
    private String isChangeHardware;//是否更换硬件
    private String isRestartDb;//是否重启数据库
    private String isRestartApplication;//是否重启应用服务
    private String runStep;//实施步骤
    private String runPerson;//实施人员
    private String startRunTime;//实施开始时间
    private String stopRunTime;//实施结束时间
    private String operationScript;//操作脚本
    private Long planId;
    private String fileName;
    private String ext1;
}
