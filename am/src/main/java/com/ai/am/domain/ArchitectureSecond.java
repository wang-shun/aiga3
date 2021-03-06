package com.ai.am.domain;
// Generated 2017-6-30 16:00:00 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ArchitectureSecond generated by hbm2java
 */
@Entity
@Table(name="ARCHITECTURE_SECOND")
public class ArchitectureSecond  implements java.io.Serializable {


     private long idSecond;
     private String name;
     private String shortName;
     private String description;
     private String code;
     private long idFirst;
     private String belongLevel;
     private String state;
     private Long applyId;
     private String applyUser;
     private Date createDate;
     private Date modifyDate;
     private String identifiedInfo;
     private String fileInfo;
     private String ext1;
     private String ext2;
     private String ext3;

    public ArchitectureSecond() {
    }

	
    public ArchitectureSecond(long idSecond, String name, String code, long idFirst, String belongLevel, Date createDate) {
        this.idSecond = idSecond;
        this.name = name;
        this.code = code;
        this.idFirst = idFirst;
        this.belongLevel = belongLevel;
        this.createDate = createDate;
    }
    public ArchitectureSecond(long idSecond, String name, String shortName, String description, String code, long idFirst, String belongLevel, String state, Long applyId, String applyUser, Date createDate, Date modifyDate, String identifiedInfo, String fileInfo, String ext1, String ext2, String ext3) {
       this.idSecond = idSecond;
       this.name = name;
       this.shortName = shortName;
       this.description = description;
       this.code = code;
       this.idFirst = idFirst;
       this.belongLevel = belongLevel;
       this.state = state;
       this.applyId = applyId;
       this.applyUser = applyUser;
       this.createDate = createDate;
       this.modifyDate = modifyDate;
       this.identifiedInfo = identifiedInfo;
       this.fileInfo = fileInfo;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
    
    @Column(name="ID_SECOND", unique=true, nullable=false, precision=10, scale=0)
    public long getIdSecond() {
        return this.idSecond;
    }
    
    public void setIdSecond(long idSecond) {
        this.idSecond = idSecond;
    }
    
    @Column(name="NAME", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SHORT_NAME")
    public String getShortName() {
        return this.shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="CODE", nullable=false, length=50)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="ID_FIRST", nullable=false, precision=10, scale=0)
    public long getIdFirst() {
        return this.idFirst;
    }
    
    public void setIdFirst(long idFirst) {
        this.idFirst = idFirst;
    }
    
    @Column(name="BELONG_LEVEL", nullable=false, length=20)
    public String getBelongLevel() {
        return this.belongLevel;
    }
    
    public void setBelongLevel(String belongLevel) {
        this.belongLevel = belongLevel;
    }
    
    @Column(name="STATE", length=20)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="APPLY_ID", precision=10, scale=0)
    public Long getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="APPLY_USER", length=10)
    public String getApplyUser() {
        return this.applyUser;
    }
    
    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", nullable=false, length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFY_DATE", length=7)
    public Date getModifyDate() {
        return this.modifyDate;
    }
    
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    @Column(name="IDENTIFIED_INFO")
    public String getIdentifiedInfo() {
        return this.identifiedInfo;
    }
    
    public void setIdentifiedInfo(String identifiedInfo) {
        this.identifiedInfo = identifiedInfo;
    }
    
    @Column(name="FILE_INFO", length=500)
    public String getFileInfo() {
        return this.fileInfo;
    }
    
    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
    
    @Column(name="EXT_1")
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2")
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3")
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


