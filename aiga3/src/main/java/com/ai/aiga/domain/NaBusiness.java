package com.ai.aiga.domain;

import javax.persistence.*;
import java.sql.Time;

/**
 * @author defaultekey
 * @date 2017/3/14
 */
@Entity
@Table(name = "NA_BUSINESS")
public class NaBusiness {
    private long busiId;
    private String busiName;
    private Long busiType;
    private String busiDesc;
    private Short important;
    private Short invalid;
    private Long creatorId;
    private Time createTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_BUSINESS$SEQ")
    @SequenceGenerator(name="NA_BUSINESS$SEQ",sequenceName="NA_BUSINESS$SEQ",allocationSize=1)
    @Column(name = "BUSI_ID")
    public long getBusiId() {
        return busiId;
    }

    public void setBusiId(long busiId) {
        this.busiId = busiId;
    }

    @Basic
    @Column(name = "BUSI_NAME")
    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    @Basic
    @Column(name = "BUSI_TYPE")
    public Long getBusiType() {
        return busiType;
    }

    public void setBusiType(Long busiType) {
        this.busiType = busiType;
    }

    @Basic
    @Column(name = "BUSI_DESC")
    public String getBusiDesc() {
        return busiDesc;
    }

    public void setBusiDesc(String busiDesc) {
        this.busiDesc = busiDesc;
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
    @Column(name = "INVALID")
    public Short getInvalid() {
        return invalid;
    }

    public void setInvalid(Short invalid) {
        this.invalid = invalid;
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
    @Column(name = "CREATE_TIME")
    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }


}
