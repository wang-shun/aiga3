package com.ai.aiga.view.json.auto;

import java.util.List;

/**
 * 自动化用例请求参数
 *
 * @author defaultekey
 * @date 2017/3/10
 */
public class AutoCaseRequest {
    private Long autoId;//主键
    private Long testId;//测试用例ID
    private Long tempId;//自动化用例模板ID
    private String testType;//测试类型
    private Byte caseType;//用例类型
    private String autoName;//自动化用例名称
    private Long sysId;//系统大类ID
    private Long sysSubId;//系统子类ID
    private Long busiId;//业务ID
    private Long funId;//功能ID
    private Long scId;//场景ID
    private Short important;//重要等级
    private Long environmentType;//环境类型
    private Long status;//用例状态
    private Long hasAuto;//是否已实现自动化
    private String autoDesc;//用例描述
    private Long paramLevel;//参数等级
    private List<AutoUiCompRequest> compList;//组件集合

    public AutoCaseRequest() {
    }

    public AutoCaseRequest(Long autoId, Long testId, Long tempId, String testType, Byte caseType, String autoName, Long sysId, Long sysSubId, Long busiId, Long funId, Long scId, Short important, Long environmentType, Long status, Long hasAuto, String autoDesc, Long paramLevel, List<AutoUiCompRequest> compList) {
        this.autoId = autoId;
        this.testId = testId;
        this.tempId = tempId;
        this.testType = testType;
        this.caseType = caseType;
        this.autoName = autoName;
        this.sysId = sysId;
        this.sysSubId = sysSubId;
        this.busiId = busiId;
        this.funId = funId;
        this.scId = scId;
        this.important = important;
        this.environmentType = environmentType;
        this.status = status;
        this.hasAuto = hasAuto;
        this.autoDesc = autoDesc;
        this.paramLevel = paramLevel;
        this.compList = compList;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Byte getCaseType() {
        return caseType;
    }

    public void setCaseType(Byte caseType) {
        this.caseType = caseType;
    }

    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getSysSubId() {
        return sysSubId;
    }

    public void setSysSubId(Long sysSubId) {
        this.sysSubId = sysSubId;
    }

    public Long getBusiId() {
        return busiId;
    }

    public void setBusiId(Long busiId) {
        this.busiId = busiId;
    }

    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public Short getImportant() {
        return important;
    }

    public void setImportant(Short important) {
        this.important = important;
    }

    public Long getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(Long environmentType) {
        this.environmentType = environmentType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getHasAuto() {
        return hasAuto;
    }

    public void setHasAuto(Long hasAuto) {
        this.hasAuto = hasAuto;
    }

    public String getAutoDesc() {
        return autoDesc;
    }

    public void setAutoDesc(String autoDesc) {
        this.autoDesc = autoDesc;
    }

    public Long getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Long paramLevel) {
        this.paramLevel = paramLevel;
    }

    public List<AutoUiCompRequest> getCompList() {
        return compList;
    }

    public void setCompList(List<AutoUiCompRequest> compList) {
        this.compList = compList;
    }
}
