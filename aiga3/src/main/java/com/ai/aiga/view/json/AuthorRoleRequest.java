package com.ai.aiga.view.json;

/**
 * 员工角色权限关联关系类
 *
 * @author defaultekey
 * @date 2017/2/22
 */
public class AuthorRoleRequest {
    private Long staffId;//员工唯一ID
    private String roleIds;//角色ID,多个角色ID以 , 拼接

    public AuthorRoleRequest() {
    }

    public AuthorRoleRequest(Long staffId, String roleIds) {
        this.staffId = staffId;
        this.roleIds = roleIds;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
