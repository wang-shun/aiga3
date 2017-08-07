package com.ai.am.domain;
// Generated 2017-8-3 16:14:57 by Hibernate Tools 3.2.2.GA


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
 * ArchitectureGrading generated by hbm2java
 */
@Entity
@Table(name="ARCHITECTURE_GRADING")
public class ArchitectureGrading  implements java.io.Serializable {


     private long applyId;
     private String identifiedInfo;
     private long sysId;
     private String name;
     private String systemFunction;
     private String description;
     private String code;
     private Long idBelong;
     private String belongLevel;
     private String department;
     private String projectInfo;
     private String designInfo;
     private String sysState;
     private String state;
     private String rankInfo;
     private String applyUser;
     private Date applyTime;
     private Date modifyDate;
     private Date createDate;
     private String ext1;
     private String ext2;
     private String ext3;
     private Long onlysysId;
     private String identifyUser;

    public ArchitectureGrading() {
    }

	
    public ArchitectureGrading(long applyId, long sysId, String name, String state, String applyUser) {
        this.applyId = applyId;
        this.sysId = sysId;
        this.name = name;
        this.state = state;
        this.applyUser = applyUser;
    }
    public ArchitectureGrading(long applyId, String identifiedInfo, long sysId, String name, String systemFunction, String description, String code, Long idBelong, String belongLevel, String department, String projectInfo, String designInfo, String sysState, String state, String rankInfo, String applyUser, Date applyTime, Date modifyDate, Date createDate, String ext1, String ext2, String ext3, Long onlysysId, String identifyUser) {
       this.applyId = applyId;
       this.identifiedInfo = identifiedInfo;
       this.sysId = sysId;
       this.name = name;
       this.systemFunction = systemFunction;
       this.description = description;
       this.code = code;
       this.idBelong = idBelong;
       this.belongLevel = belongLevel;
       this.department = department;
       this.projectInfo = projectInfo;
       this.designInfo = designInfo;
       this.sysState = sysState;
       this.state = state;
       this.rankInfo = rankInfo;
       this.applyUser = applyUser;
       this.applyTime = applyTime;
       this.modifyDate = modifyDate;
       this.createDate = createDate;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.onlysysId = onlysysId;
       this.identifyUser = identifyUser;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ARCHITECTURE_GRADING$SEQ")
     @SequenceGenerator(name="ARCHITECTURE_GRADING$SEQ",sequenceName="ARCHITECTURE_GRADING$SEQ",allocationSize=1)
    @Column(name="APPLY_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="IDENTIFIED_INFO")
    public String getIdentifiedInfo() {
        return this.identifiedInfo;
    }
    
    public void setIdentifiedInfo(String identifiedInfo) {
        this.identifiedInfo = identifiedInfo;
    }
    
    @Column(name="SYS_ID", nullable=false, precision=10, scale=0)
    public long getSysId() {
        return this.sysId;
    }
    
    public void setSysId(long sysId) {
        this.sysId = sysId;
    }
    
    @Column(name="NAME", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SYSTEM_FUNCTION", length=500)
    public String getSystemFunction() {
        return this.systemFunction;
    }
    
    public void setSystemFunction(String systemFunction) {
        this.systemFunction = systemFunction;
    }
    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="CODE", length=50)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="ID_BELONG", precision=10, scale=0)
    public Long getIdBelong() {
        return this.idBelong;
    }
    
    public void setIdBelong(Long idBelong) {
        this.idBelong = idBelong;
    }
    
    @Column(name="BELONG_LEVEL", length=20)
    public String getBelongLevel() {
        return this.belongLevel;
    }
    
    public void setBelongLevel(String belongLevel) {
        this.belongLevel = belongLevel;
    }
    
    @Column(name="DEPARTMENT")
    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Column(name="PROJECT_INFO")
    public String getProjectInfo() {
        return this.projectInfo;
    }
    
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }
    
    @Column(name="DESIGN_INFO")
    public String getDesignInfo() {
        return this.designInfo;
    }
    
    public void setDesignInfo(String designInfo) {
        this.designInfo = designInfo;
    }
    
    @Column(name="SYS_STATE", length=20)
    public String getSysState() {
        return this.sysState;
    }
    
    public void setSysState(String sysState) {
        this.sysState = sysState;
    }
    
    @Column(name="STATE", nullable=false, length=20)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="RANK_INFO")
    public String getRankInfo() {
        return this.rankInfo;
    }
    
    public void setRankInfo(String rankInfo) {
        this.rankInfo = rankInfo;
    }
    
    @Column(name="APPLY_USER", nullable=false, length=10)
    public String getApplyUser() {
        return this.applyUser;
    }
    
    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="APPLY_TIME", length=7)
    public Date getApplyTime() {
        return this.applyTime;
    }
    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFY_DATE", length=7)
    public Date getModifyDate() {
        return this.modifyDate;
    }
    
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
    
    @Column(name="ONLYSYS_ID", precision=10, scale=0)
    public Long getOnlysysId() {
        return this.onlysysId;
    }
    
    public void setOnlysysId(Long onlysysId) {
        this.onlysysId = onlysysId;
    }
    
    @Column(name="IDENTIFY_USER", length=10)
    public String getIdentifyUser() {
        return this.identifyUser;
    }
    
    public void setIdentifyUser(String identifyUser) {
        this.identifyUser = identifyUser;
    }




}


