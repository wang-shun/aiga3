package com.ai.aiga.domain;
// Generated 2017-2-17 15:47:35 by Hibernate Tools 3.2.2.GA


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysRole generated by hbm2java
 */
@Entity
@Table(name="SYS_ROLE")
public class SysRole  implements java.io.Serializable {


     private Long roleId;
     private String code;
     private String name;
     private String notes;
     private Byte state;
     private Long doneCode;
     private Date createDate;
     private Date doneDate;
     private Date validDate;
     private Date expireDate;
     private Long opId;
     private Long orgId;

    public SysRole() {
    }

	
    public SysRole(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
    public SysRole(Long roleId, String code, String name, String notes, Byte state, Long doneCode, Date createDate, Date doneDate, Date validDate, Date expireDate, Long opId, Long orgId) {
       this.roleId = roleId;
       this.code = code;
       this.name = name;
       this.notes = notes;
       this.state = state;
       this.doneCode = doneCode;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.validDate = validDate;
       this.expireDate = expireDate;
       this.opId = opId;
       this.orgId = orgId;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SYS_ROLE$SEQ")
    @SequenceGenerator(name="SYS_ROLE$SEQ",sequenceName="SYS_ROLE$SEQ",allocationSize=1)
    @Column(name="ROLE_ID", unique=true, nullable=false, precision=12, scale=0)
    public Long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    @Column(name="CODE", length=40)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="NAME", nullable=false, length=60)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="NOTES", length=100)
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Column(name="STATE", precision=2, scale=0)
    public Byte getState() {
        return this.state;
    }
    
    public void setState(Byte state) {
        this.state = state;
    }
    
    @Column(name="DONE_CODE", precision=12, scale=0)
    public Long getDoneCode() {
        return this.doneCode;
    }
    
    public void setDoneCode(Long doneCode) {
        this.doneCode = doneCode;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=7)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_DATE", length=7)
    public Date getValidDate() {
        return this.validDate;
    }
    
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRE_DATE", length=7)
    public Date getExpireDate() {
        return this.expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    @Column(name="OP_ID", precision=12, scale=0)
    public Long getOpId() {
        return this.opId;
    }
    
    public void setOpId(Long opId) {
        this.opId = opId;
    }
    
    @Column(name="ORG_ID", precision=12, scale=0)
    public Long getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}


