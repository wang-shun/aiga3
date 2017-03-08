package com.ai.aiga.view.json;

import java.sql.Clob;

/**
 * 自动化用例模板与组件关系请求参数
 *
 * @author defaultekey
 * @date 2017/3/5
 */
public class AutoTemplateCompRequest {
    private Long relaId;//主键
    private Long tempId;//模板ID
    private String tempName;//自动化用例模板名称
    private Long caseId;//用例模板名称
    private Long compId;//组件ID
    private String compName;//组件名称
    private String compDesc;//组件描述
    private String compScript;//组件脚本
    private Long compOrder;//组件顺序

    public AutoTemplateCompRequest(Long relaId, Long tempId, String tempName, Long caseId, Long compId, String compName, String compDesc, String compScript, Long compOrder) {
        this.relaId = relaId;
        this.tempId = tempId;
        this.tempName = tempName;
        this.caseId = caseId;
        this.compId = compId;
        this.compName = compName;
        this.compDesc = compDesc;
        this.compScript = compScript;
        this.compOrder = compOrder;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public AutoTemplateCompRequest() {
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompDesc() {
        return compDesc;
    }

    public void setCompDesc(String compDesc) {
        this.compDesc = compDesc;
    }

    public String getCompScript() {
        return compScript;
    }

    public void setCompScript(String compScript) {
        this.compScript = compScript;
    }

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getCompOrder() {
        return compOrder;
    }

    public void setCompOrder(Long compOrder) {
        this.compOrder = compOrder;
    }
}
