package com.ai.am.domain;
// Generated 2017-5-12 14:45:57 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
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
 * NaStaffKpiRela generated by hbm2java
 */
@Entity
@Table(name="NA_STAFF_KPI_RELA")
public class NaStaffKpiRela  implements java.io.Serializable {


     private Long realId;
     private Long staffId;
     private Long kpiId;
     private String kpiName;
     private Long csType;
     private Long state;
     private String ext1;
     private String ext2;
     private String ext3;
     private BigDecimal ext4;
     private BigDecimal ext5;
     private Date createDate;
     private Date doneDate;
     private Long opId;

    public NaStaffKpiRela() {
    }

	
    public NaStaffKpiRela(Long realId) {
        this.realId = realId;
    }
    public NaStaffKpiRela(Long realId, Long staffId, Long kpiId, String kpiName, Long csType, Long state, String ext1, String ext2, String ext3, BigDecimal ext4, BigDecimal ext5, Date createDate, Date doneDate, Long opId) {
       this.realId = realId;
       this.staffId = staffId;
       this.kpiId = kpiId;
       this.kpiName = kpiName;
       this.csType = csType;
       this.state = state;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.ext4 = ext4;
       this.ext5 = ext5;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.opId = opId;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_STAFF_KPI_RELA$SEQ")
    @SequenceGenerator(name="NA_STAFF_KPI_RELA$SEQ",sequenceName="NA_STAFF_KPI_RELA$SEQ",allocationSize=1)
    @Column(name="REAL_ID", unique=true, nullable=false, precision=14, scale=0)
    public Long getRealId() {
        return this.realId;
    }
    
    public void setRealId(Long realId) {
        this.realId = realId;
    }
    
    @Column(name="STAFF_ID", precision=14, scale=0)
    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    
    @Column(name="KPI_ID", precision=14, scale=0)
    public Long getKpiId() {
        return this.kpiId;
    }
    
    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }
    
    @Column(name="KPI_NAME", length=4000)
    public String getKpiName() {
        return this.kpiName;
    }
    
    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }
    
    @Column(name="CS_TYPE", precision=14, scale=0)
    public Long getCsType() {
        return this.csType;
    }
    
    public void setCsType(Long csType) {
        this.csType = csType;
    }
    
    @Column(name="STATE", precision=2, scale=0)
    public Long getState() {
        return this.state;
    }
    
    public void setState(Long state) {
        this.state = state;
    }
    
    @Column(name="EXT_1", length=1000)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=1000)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=1000)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
    
    @Column(name="EXT_4", precision=20, scale=0)
    public BigDecimal getExt4() {
        return this.ext4;
    }
    
    public void setExt4(BigDecimal ext4) {
        this.ext4 = ext4;
    }
    
    @Column(name="EXT_5", precision=20, scale=0)
    public BigDecimal getExt5() {
        return this.ext5;
    }
    
    public void setExt5(BigDecimal ext5) {
        this.ext5 = ext5;
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
    
    @Column(name="OP_ID", precision=14, scale=0)
    public Long getOpId() {
        return this.opId;
    }
    
    public void setOpId(Long opId) {
        this.opId = opId;
    }




}


