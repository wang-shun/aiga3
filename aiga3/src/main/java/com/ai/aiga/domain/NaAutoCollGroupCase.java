package com.ai.aiga.domain;

import java.util.Date;

import javax.persistence.*;


/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_COLL_GROUP_CASE")
public class NaAutoCollGroupCase {
    private Long relaId;  
    private Long collectId;
    private Long elementType;  //用例、用例组
    private Long elementId;
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
    @Column(name = "COLLECT_ID")
    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    @Basic
    @Column(name = "ELEMENT_TYPE")
    public Long getElementType() {
        return elementType;
    }

    public void setElementType(Long elementType) {
        this.elementType = elementType;
    }

    @Basic
    @Column(name = "ELEMENT_ID")
    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
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
