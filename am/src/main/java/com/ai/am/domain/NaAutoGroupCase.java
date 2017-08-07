package com.ai.am.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_GROUP_CASE")
public class NaAutoGroupCase {
    private Long relaId;
    private Long groupId;
    private Long autoId;
    private Long groupOrder;
    private Long creatorId;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_GROUP_CASE$SEQ")
    @SequenceGenerator(name="NA_AUTO_GROUP_CASE$SEQ",sequenceName="NA_AUTO_GROUP_CASE$SEQ",allocationSize=1)
    @Column(name = "RELA_ID")
    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
    @Column(name = "GROUP_ORDER")
    public Long getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Long groupOrder) {
        this.groupOrder = groupOrder;
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
