package com.ai.aiga.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_CASE_DEL")
public class NaAutoCaseDel {
    private Long autoId;
    private Long testId;
    private Long tempId;
    private String testType;
    private Long caseType;
    private String autoName;
    private Long sysId;
    private Long sysSubId;
    private Long busiId;
    private Long funId;
    private Long scId;
    private Long important;
    private Long environmentType;
    private Long status;
    private Long hasAuto;
    private String autoDesc;
    private Long paramLevel;
    private Long creatorId;
    private Long updateId;
    private Date updateTime;

    @Id
    @Column(name = "AUTO_ID")
    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    @Basic
    @Column(name = "TEST_ID")
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    @Basic
    @Column(name = "TEMP_ID")
    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
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
    @Column(name = "CASE_TYPE")
    public Long getCaseType() {
        return caseType;
    }

    public void setCaseType(Long caseType) {
        this.caseType = caseType;
    }

    @Basic
    @Column(name = "AUTO_NAME")
    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
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
    @Column(name = "FUN_ID")
    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
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
    @Column(name = "IMPORTANT")
    public Long getImportant() {
        return important;
    }

    public void setImportant(Long important) {
        this.important = important;
    }

    @Basic
    @Column(name = "ENVIRONMENT_TYPE")
    public Long getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(Long environmentType) {
        this.environmentType = environmentType;
    }

    @Basic
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "HAS_AUTO")
    public Long getHasAuto() {
        return hasAuto;
    }

    public void setHasAuto(Long hasAuto) {
        this.hasAuto = hasAuto;
    }

    @Basic
    @Column(name = "AUTO_DESC")
    public String getAutoDesc() {
        return autoDesc;
    }

    public void setAutoDesc(String autoDesc) {
        this.autoDesc = autoDesc;
    }

    @Basic
    @Column(name = "PARAM_LEVEL")
    public Long getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Long paramLevel) {
        this.paramLevel = paramLevel;
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
