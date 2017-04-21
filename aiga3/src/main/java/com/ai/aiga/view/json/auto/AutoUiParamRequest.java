package com.ai.aiga.view.json.auto;

/**
 * 自动化用例参数请求类
 *
 * @author defaultekey
 * @date 2017/3/10
 */
public class AutoUiParamRequest {
    private Long paramId;//主键
    private Long compId;//组件ID
    private Long autoId;//自动化用例ID
    private Long templetId;//该参数暂时废弃
    private Long compOrder;//组件顺序
    private String paramName;//参数名称
    private String paramValue;//参数值
    private String paramExpect;//预期值
    private String paramSql;//参数SQL
    private String paramDesc;//参数描述
    private Long paramLevel;//参数等级

    public AutoUiParamRequest() {
    }

    public AutoUiParamRequest(Long paramId, Long compId, Long autoId, Long templetId, Long compOrder, String paramName, String paramValue, String paramExpect, String paramSql, String paramDesc, Long paramLevel) {
        this.paramId = paramId;
        this.compId = compId;
        this.autoId = autoId;
        this.templetId = templetId;
        this.compOrder = compOrder;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramExpect = paramExpect;
        this.paramSql = paramSql;
        this.paramDesc = paramDesc;
        this.paramLevel = paramLevel;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Long getTempletId() {
        return templetId;
    }

    public void setTempletId(Long templetId) {
        this.templetId = templetId;
    }

    public Long getCompOrder() {
        return compOrder;
    }

    public void setCompOrder(Long compOrder) {
        this.compOrder = compOrder;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamExpect() {
        return paramExpect;
    }

    public void setParamExpect(String paramExpect) {
        this.paramExpect = paramExpect;
    }

    public String getParamSql() {
        return paramSql;
    }

    public void setParamSql(String paramSql) {
        this.paramSql = paramSql;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Long getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Long paramLevel) {
        this.paramLevel = paramLevel;
    }
}
