package com.ai.am.view.controller.auto.dto;

import java.util.List;

/**
 * 自动化用例模板交互参数
 *
 * @author defaultekey
 * @date 2017/3/2
 */
public class AutoTemplateRequest {

    private Long tempId;//主键
    private Long caseId;//用例模板ID
    private String caseName;//用例模板名称
    private Long caseType;//用例类型
    private String caseTypeDesc;//用例类型描述
    private String testType;//测试类型
    private String testTypeDesc;//测试类型描述
    private String tempName;//自动化模板名称
    private Long sysId;//系统大类ID
    private String sysName;//系统大类名称
    private Long sysSubId;//系统子类ID
    private String sysSubName;//系统子类名称
    private Long busiId;//业务ID
    private String busiName;//业务名称
    private Long scId;//场景ID
    private String scName;//场景名称
    private Long funId;//功能ID
    private String funName;//功能点名称
    private Short important;//重要等级
    private String importantDesc;//重要等级描述
    private String operateDesc;//操作描述
    private List<AutoTemplateCompRequest> compRequestList;//组件参数集合
    public AutoTemplateRequest() {
    }

    public AutoTemplateRequest(Long tempId, Long caseId, String caseName, Long caseType, String caseTypeDesc, String testType, String testTypeDesc, String tempName, Long sysId, String sysName, Long sysSubId, String sysSubName, Long busiId, String busiName, Long scId, String scName, Long funId, String funName, Short important, String importantDesc, String operateDesc, List<AutoTemplateCompRequest> compRequestList) {
        this.tempId = tempId;
        this.caseId = caseId;
        this.caseName = caseName;
        this.caseType = caseType;
        this.caseTypeDesc = caseTypeDesc;
        this.testType = testType;
        this.testTypeDesc = testTypeDesc;
        this.tempName = tempName;
        this.sysId = sysId;
        this.sysName = sysName;
        this.sysSubId = sysSubId;
        this.sysSubName = sysSubName;
        this.busiId = busiId;
        this.busiName = busiName;
        this.scId = scId;
        this.scName = scName;
        this.funId = funId;
        this.funName = funName;
        this.important = important;
        this.importantDesc = importantDesc;
        this.operateDesc = operateDesc;
        this.compRequestList = compRequestList;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Long getCaseType() {
        return caseType;
    }

    public void setCaseType(Long caseType) {
        this.caseType = caseType;
    }

    public String getCaseTypeDesc() {
        return caseTypeDesc;
    }

    public void setCaseTypeDesc(String caseTypeDesc) {
        this.caseTypeDesc = caseTypeDesc;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestTypeDesc() {
        return testTypeDesc;
    }

    public void setTestTypeDesc(String testTypeDesc) {
        this.testTypeDesc = testTypeDesc;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public Long getSysSubId() {
        return sysSubId;
    }

    public void setSysSubId(Long sysSubId) {
        this.sysSubId = sysSubId;
    }

    public String getSysSubName() {
        return sysSubName;
    }

    public void setSysSubName(String sysSubName) {
        this.sysSubName = sysSubName;
    }

    public Long getBusiId() {
        return busiId;
    }

    public void setBusiId(Long busiId) {
        this.busiId = busiId;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public Short getImportant() {
        return important;
    }

    public void setImportant(Short important) {
        this.important = important;
    }

    public String getImportantDesc() {
        return importantDesc;
    }

    public void setImportantDesc(String importantDesc) {
        this.importantDesc = importantDesc;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public List<AutoTemplateCompRequest> getCompRequestList() {
        return compRequestList;
    }

    public void setCompRequestList(List<AutoTemplateCompRequest> compRequestList) {
        this.compRequestList = compRequestList;
    }
}
