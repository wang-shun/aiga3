package com.ai.aiga.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_UI_PARAM")
public class NaAutoUiParam {
    private Long paramId;
    private Long compId;
    private Long autoId;
    private Long templetId;
    private Long compOrder;
    private String paramName;
    private String paramValue;
    private String paramExpect;
    private String paramSql;
    private String paramDesc;
    private Long paramLevel;
    private Long creatorId;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_CASE$SEQ")
    @SequenceGenerator(name="NA_AUTO_CASE$SEQ",sequenceName="NA_AUTO_CASE$SEQ",allocationSize=1)
    @Column(name = "PARAM_ID")
    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    @Basic
    @Column(name = "COMP_ID")
    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    @Basic
    @Column(name = "AUTO_ID")
    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    @Basic
    @Column(name = "TEMPLET_ID")
    public Long getTempletId() {
        return templetId;
    }

    public void setTempletId(Long templetId) {
        this.templetId = templetId;
    }

    @Basic
    @Column(name = "COMP_ORDER")
    public Long getCompOrder() {
        return compOrder;
    }

    public void setCompOrder(Long compOrder) {
        this.compOrder = compOrder;
    }

    @Basic
    @Column(name = "PARAM_NAME")
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Basic
    @Column(name = "PARAM_VALUE")
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Basic
    @Column(name = "PARAM_EXPECT")
    public String getParamExpect() {
        return paramExpect;
    }

    public void setParamExpect(String paramExpect) {
        this.paramExpect = paramExpect;
    }

    @Basic
    @Column(name = "PARAM_SQL")
    public String getParamSql() {
        return paramSql;
    }

    public void setParamSql(String paramSql) {
        this.paramSql = paramSql;
    }

    @Basic
    @Column(name = "PARAM_DESC")
    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
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
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
