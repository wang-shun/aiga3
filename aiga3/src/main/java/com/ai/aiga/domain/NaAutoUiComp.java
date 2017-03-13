package com.ai.aiga.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_UI_COMP")
public class NaAutoUiComp {
    private Long relaId;
    private Long autoId;
    private Long compId;
    private Long compOrder;
    private Long creatorId;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_CASE$SEQ")
    @SequenceGenerator(name="NA_AUTO_CASE$SEQ",sequenceName="NA_AUTO_CASE$SEQ",allocationSize=1)
    @Column(name = "RELA_ID")
    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
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
    @Column(name = "COMP_ID")
    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
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
