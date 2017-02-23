package com.ai.aiga.domain;
// Generated 2017-2-21 16:31:05 by Hibernate Tools 3.2.2.GA


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
 * AigaStaffOrgRelat generated by hbm2java
 */
@Entity
@Table(name="AIGA_STAFF_ORG_RELAT"
    ,schema="AIGA"
)
public class AigaStaffOrgRelat  implements java.io.Serializable {


     private long staffOrgRelatId;
     private Long organizeId;
     private Long staffId;
     private Character isAdminStaff;
     private Character isBaseOrg;
     private String notes;
     private Long doneCode;
     private Date createDate;
     private Date doneDate;
     private Date validDate;
     private Date expireDate;
     private Long opId;
     private Long orgId;

    public AigaStaffOrgRelat() {
    }

	
    public AigaStaffOrgRelat(long staffOrgRelatId) {
        this.staffOrgRelatId = staffOrgRelatId;
    }
    public AigaStaffOrgRelat(long staffOrgRelatId, Long organizeId, Long staffId, Character isAdminStaff, Character isBaseOrg, String notes, Long doneCode, Date createDate, Date doneDate, Date validDate, Date expireDate, Long opId, Long orgId) {
       this.staffOrgRelatId = staffOrgRelatId;
       this.organizeId = organizeId;
       this.staffId = staffId;
       this.isAdminStaff = isAdminStaff;
       this.isBaseOrg = isBaseOrg;
       this.notes = notes;
       this.doneCode = doneCode;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.validDate = validDate;
       this.expireDate = expireDate;
       this.opId = opId;
       this.orgId = orgId;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_STAFF_ORG_RELAT$SEQ")
    @SequenceGenerator(name="AIGA_STAFF_ORG_RELAT$SEQ",sequenceName="AIGA_STAFF_ORG_RELAT$SEQ",allocationSize=1)
    @Column(name="STAFF_ORG_RELAT_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getStaffOrgRelatId() {
        return this.staffOrgRelatId;
    }
    
    public void setStaffOrgRelatId(long staffOrgRelatId) {
        this.staffOrgRelatId = staffOrgRelatId;
    }
    
    @Column(name="ORGANIZE_ID", precision=12, scale=0)
    public Long getOrganizeId() {
        return this.organizeId;
    }
    
    public void setOrganizeId(Long organizeId) {
        this.organizeId = organizeId;
    }
    
    @Column(name="STAFF_ID", precision=12, scale=0)
    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    
    @Column(name="IS_ADMIN_STAFF", length=1)
    public Character getIsAdminStaff() {
        return this.isAdminStaff;
    }
    
    public void setIsAdminStaff(Character isAdminStaff) {
        this.isAdminStaff = isAdminStaff;
    }
    
    @Column(name="IS_BASE_ORG", length=1)
    public Character getIsBaseOrg() {
        return this.isBaseOrg;
    }
    
    public void setIsBaseOrg(Character isBaseOrg) {
        this.isBaseOrg = isBaseOrg;
    }
    
    @Column(name="NOTES")
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
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


