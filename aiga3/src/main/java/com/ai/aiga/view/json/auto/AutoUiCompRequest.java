package com.ai.aiga.view.json.auto;

import java.util.List;

/**
 * 自动化用例组件请求参数
 *
 * @author defaultekey
 * @date 2017/3/10
 */
public class AutoUiCompRequest {
    private Long relaId;//主键
    private Long autoId;//自动化用例ID
    private Long compId;//组件ID
    private Long compOrder;//组件顺序
    private String compName;//组件名称
    private String compDesc;//组件描述
    private String compScript;//组件脚本
    private List<AutoUiParamRequest> paramList;//存放参数集合

    public AutoUiCompRequest() {
    }

    public AutoUiCompRequest(Long relaId, Long autoId, Long compId, Long compOrder, String compName, String compDesc, String compScript, List<AutoUiParamRequest> paramList) {
        this.relaId = relaId;
        this.autoId = autoId;
        this.compId = compId;
        this.compOrder = compOrder;
        this.compName = compName;
        this.compDesc = compDesc;
        this.compScript = compScript;
        this.paramList = paramList;
    }

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
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

    public List<AutoUiParamRequest> getParamList() {
        return paramList;
    }

    public void setParamList(List<AutoUiParamRequest> paramList) {
        this.paramList = paramList;
    }
}
