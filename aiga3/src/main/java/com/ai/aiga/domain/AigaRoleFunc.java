package com.ai.aiga.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/2/24
 */
@Entity
@Table(name = "AIGA_ROLE_FUNC")
public class AigaRoleFunc {
    private long funcRoleTrlatId;
    private Long funcId;
    private Long roleId;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_ROLE_FUNC$SEQ")
    @SequenceGenerator(name="AIGA_ROLE_FUNC$SEQ",sequenceName="AIGA_ROLE_FUNC$SEQ",allocationSize=1)
    @Column(name = "FUNC_ROLE_TRLAT_ID")
    public long getFuncRoleTrlatId() {
        return funcRoleTrlatId;
    }

    public void setFuncRoleTrlatId(long funcRoleTrlatId) {
        this.funcRoleTrlatId = funcRoleTrlatId;
    }

    @Basic
    @Column(name = "FUNC_ID")
    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
