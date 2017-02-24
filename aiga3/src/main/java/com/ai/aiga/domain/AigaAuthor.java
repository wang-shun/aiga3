package com.ai.aiga.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/2/21
 */
@Entity
@Table(name = "AIGA_AUTHOR")
public class AigaAuthor {
    private Long roleAuthorId;
    private Long roleId;
    private Long staffId;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_AUTHOR$SEQ")
    @SequenceGenerator(name="AIGA_AUTHOR$SEQ",sequenceName="AIGA_AUTHOR$SEQ",allocationSize=1)
    @Column(name = "ROLE_AUTHOR_ID")
    public Long getRoleAuthorId() {
        return roleAuthorId;
    }

    public void setRoleAuthorId(Long roleAuthorId) {
        this.roleAuthorId = roleAuthorId;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "STAFF_ID")
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public AigaAuthor() {
    }

    public AigaAuthor(Long roleAuthorId, Long roleId, Long staffId) {
        this.roleAuthorId = roleAuthorId;
        this.roleId = roleId;
        this.staffId = staffId;
    }
}
