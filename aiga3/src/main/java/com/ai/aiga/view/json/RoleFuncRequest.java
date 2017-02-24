package com.ai.aiga.view.json;

/**
 * 角色菜单权限关联关系
 *
 * @author defaultekey
 * @date 2017/2/22
 */
public class RoleFuncRequest {
    private Long roleId;//角色ID
    private String funcIds;//多个菜单ID以 , 拼接在一起

    public RoleFuncRequest() {
    }

    public RoleFuncRequest(Long roleId, String funcIds) {
        this.roleId = roleId;
        this.funcIds = funcIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getFuncIds() {
        return funcIds;
    }

    public void setFuncIds(String funcIds) {
        this.funcIds = funcIds;
    }
}
