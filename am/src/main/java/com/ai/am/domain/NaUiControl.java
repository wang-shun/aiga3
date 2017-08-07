package com.ai.am.domain;
// Generated 2017-3-1 11:28:50 by Hibernate Tools 3.2.2.GA


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

import org.springframework.format.annotation.DateTimeFormat;

/**
 * NaUiControl generated by hbm2java
 */
@Entity
@Table(name="NA_UI_CONTROL"
)
public class NaUiControl  implements java.io.Serializable {


     private Long ctrlId;
     private Long parentId;
     private String ifLeaf;
     private String ctrlName;
     private String ctrlType;
     private String ctrlXpath;
     private String ctrlDesc;
     private Long sysId;
     private Long sysSubId;
     private Long funId;
     private Long creatorId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date createTime;
     private Long updateId;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;

    public NaUiControl() {
    }

	
    public NaUiControl(Long ctrlId) {
        this.ctrlId = ctrlId;
    }
    public NaUiControl(Long ctrlId, Long parentId, String ifLeaf, String ctrlName, String ctrlType, String ctrlXpath, String ctrlDesc, Long sysId, Long sysSubId, Long funId, Long creatorId, Date createTime, Long updateId, Date updateTime) {
       this.ctrlId = ctrlId;
       this.parentId = parentId;
       this.ifLeaf = ifLeaf;
       this.ctrlName = ctrlName;
       this.ctrlType = ctrlType;
       this.ctrlXpath = ctrlXpath;
       this.ctrlDesc = ctrlDesc;
       this.sysId = sysId;
       this.sysSubId = sysSubId;
       this.funId = funId;
       this.creatorId = creatorId;
       this.createTime = createTime;
       this.updateId = updateId;
       this.updateTime = updateTime;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_UI_CONTROL$SEQ")
     @SequenceGenerator(name="NA_UI_CONTROL$SEQ",sequenceName="NA_UI_CONTROL$SEQ",allocationSize=1)
    @Column(name="CTRL_ID", unique=true, nullable=false, precision=12, scale=0)
    public Long getCtrlId() {
        return this.ctrlId;
    }
    
    public void setCtrlId(Long ctrlId) {
        this.ctrlId = ctrlId;
    }
    
    @Column(name="PARENT_ID", precision=12, scale=0)
    public Long getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    @Column(name="IF_LEAF", length=2)
    public String getIfLeaf() {
        return this.ifLeaf;
    }
    
    public void setIfLeaf(String ifLeaf) {
        this.ifLeaf = ifLeaf;
    }
    
    @Column(name="CTRL_NAME")
    public String getCtrlName() {
        return this.ctrlName;
    }
    
    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }
    
    @Column(name="CTRL_TYPE", length=20)
    public String getCtrlType() {
        return this.ctrlType;
    }
    
    public void setCtrlType(String ctrlType) {
        this.ctrlType = ctrlType;
    }
    
    @Column(name="CTRL_XPATH")
    public String getCtrlXpath() {
        return this.ctrlXpath;
    }
    
    public void setCtrlXpath(String ctrlXpath) {
        this.ctrlXpath = ctrlXpath;
    }
    
    @Column(name="CTRL_DESC", length=4000)
    public String getCtrlDesc() {
        return this.ctrlDesc;
    }
    
    public void setCtrlDesc(String ctrlDesc) {
        this.ctrlDesc = ctrlDesc;
    }
    
    @Column(name="SYS_ID", precision=12, scale=0)
    public Long getSysId() {
        return this.sysId;
    }
    
    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    
    @Column(name="SYS_SUB_ID", precision=12, scale=0)
    public Long getSysSubId() {
        return this.sysSubId;
    }
    
    public void setSysSubId(Long sysSubId) {
        this.sysSubId = sysSubId;
    }
    
    @Column(name="FUN_ID", precision=12, scale=0)
    public Long getFunId() {
        return this.funId;
    }
    
    public void setFunId(Long funId) {
        this.funId = funId;
    }
    
    @Column(name="CREATOR_ID", precision=12, scale=0)
    public Long getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="UPDATE_ID", precision=12, scale=0)
    public Long getUpdateId() {
        return this.updateId;
    }
    
    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TIME", length=7)
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }




}


