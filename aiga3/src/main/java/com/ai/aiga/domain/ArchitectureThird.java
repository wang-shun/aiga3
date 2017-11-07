package com.ai.aiga.domain;
// Generated 2017-9-11 10:15:24 by Hibernate Tools 3.2.2.GA


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
 * ArchitectureThird generated by hbm2java
 */
@Entity
@Table(name="ARCHITECTURE_THIRD")
public class ArchitectureThird  implements java.io.Serializable {


     private long onlysysId;
     private long idThird;
     private String name;
     private String systemCode;
     private String systemFunction;
     private String description;
     private String code;
     private long idSecond;
     private String belongLevel;
     private String department;
     private String projectInfo;
     private String designInfo;
     private String rankInfo;
     private String sysState;
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
     private String developer;
     private String cloudOrderId;

    public ArchitectureThird() {
    }

	
    public ArchitectureThird(long onlysysId, long idThird, String name, long idSecond, String belongLevel, String sysState, Date createDate) {
        this.onlysysId = onlysysId;
        this.idThird = idThird;
        this.name = name;
        this.idSecond = idSecond;
        this.belongLevel = belongLevel;
        this.sysState = sysState;
        this.createDate = createDate;
    }
    public ArchitectureThird(long onlysysId, long idThird, String name, String systemCode, String systemFunction, String description, String code, long idSecond, String belongLevel, String department, String projectInfo, String designInfo, String rankInfo, String sysState, String state, Long applyId, String applyUser, Date createDate, Date modifyDate, String identifiedInfo, String fileInfo, String ext1, String ext2, String ext3, String developer, String cloudOrderId) {
       this.onlysysId = onlysysId;
       this.idThird = idThird;
       this.name = name;
       this.systemCode = systemCode;
       this.systemFunction = systemFunction;
       this.description = description;
       this.code = code;
       this.idSecond = idSecond;
       this.belongLevel = belongLevel;
       this.department = department;
       this.projectInfo = projectInfo;
       this.designInfo = designInfo;
       this.rankInfo = rankInfo;
       this.sysState = sysState;
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
       this.developer = developer;
       this.cloudOrderId = cloudOrderId;
    }
   
     @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ARCHITECTURE_THIRD$SEQ")
    @SequenceGenerator(name="ARCHITECTURE_THIRD$SEQ",sequenceName="ARCHITECTURE_THIRD$SEQ",allocationSize=1)    
    @Column(name="ONLYSYS_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getOnlysysId() {
        return this.onlysysId;
    }
    
    public void setOnlysysId(long onlysysId) {
        this.onlysysId = onlysysId;
    }
    
    @Column(name="ID_THIRD", nullable=false, precision=10, scale=0)
    public long getIdThird() {
        return this.idThird;
    }
    
    public void setIdThird(long idThird) {
        this.idThird = idThird;
    }
    
    @Column(name="NAME", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SYSTEM_CODE")
    public String getSystemCode() {
        return this.systemCode;
    }
    
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
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
    
    @Column(name="ID_SECOND", nullable=false, precision=10, scale=0)
    public long getIdSecond() {
        return this.idSecond;
    }
    
    public void setIdSecond(long idSecond) {
        this.idSecond = idSecond;
    }
    
    @Column(name="BELONG_LEVEL", nullable=false, length=20)
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
    
    @Column(name="RANK_INFO")
    public String getRankInfo() {
        return this.rankInfo;
    }
    
    public void setRankInfo(String rankInfo) {
        this.rankInfo = rankInfo;
    }
    
    @Column(name="SYS_STATE", nullable=false)
    public String getSysState() {
        return this.sysState;
    }
    
    public void setSysState(String sysState) {
        this.sysState = sysState;
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
    
    @Column(name="DEVELOPER")
    public String getDeveloper() {
        return this.developer;
    }
    
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @Column(name="CLOUD_ORDER_ID")
	public String getCloudOrderId() {
		return cloudOrderId;
	}

	public void setCloudOrderId(String cloudOrderId) {
		this.cloudOrderId = cloudOrderId;
	}

}


