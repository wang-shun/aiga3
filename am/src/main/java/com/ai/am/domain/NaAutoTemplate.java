package com.ai.am.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_TEMPLATE")
public class NaAutoTemplate {
    private Long tempId;
    private Long caseId;
    private Long caseType;
    private String testType;
    private String tempName;
    private Long sysId;
    private Long sysSubId;
    private Long busiId;
    private Long scId;
    private Long funId;
    private Short important;
    private Long creatorId;
    private Long updateId;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_TEMPLATE$SEQ")
    @SequenceGenerator(name="NA_AUTO_TEMPLATE$SEQ",sequenceName="NA_AUTO_TEMPLATE$SEQ",allocationSize=1)
    @Column(name = "TEMP_ID")
    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    @Basic
    @Column(name = "CASE_ID")
    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "CASE_TYPE")
    public Long getCaseType() {
        return caseType;
    }

    public void setCaseType(Long caseType) {
        this.caseType = caseType;
    }

    @Basic
    @Column(name = "TEST_TYPE")
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    @Basic
    @Column(name = "TEMP_NAME")
    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    @Basic
    @Column(name = "SYS_ID")
    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    @Basic
    @Column(name = "SYS_SUB_ID")
    public Long getSysSubId() {
        return sysSubId;
    }

    public void setSysSubId(Long sysSubId) {
        this.sysSubId = sysSubId;
    }

    @Basic
    @Column(name = "BUSI_ID")
    public Long getBusiId() {
        return busiId;
    }

    public void setBusiId(Long busiId) {
        this.busiId = busiId;
    }

    @Basic
    @Column(name = "SC_ID")
    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    @Basic
    @Column(name = "FUN_ID")
    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
    }

    @Basic
    @Column(name = "IMPORTANT")
    public Short getImportant() {
        return important;
    }

    public void setImportant(Short important) {
        this.important = important;
    }

    @Basic
    @Column(name = "CREATOR_ID")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "UPDATE_ID")
    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
