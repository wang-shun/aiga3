package com.ai.am.domain;
// Generated 2017-7-6 15:24:46 by Hibernate Tools 3.2.2.GA


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
 * AmCoreIndex generated by hbm2java
 */
@Entity
@Table(name="AM_CORE_INDEX")
public class AmCoreIndex  implements java.io.Serializable {


     private long indexId;
     private String indexName;
     private String indexGroup;
     private String schId;
     private String key1;
     private String key2;
     private String key3;
     private char state;
     private Date createDate;
     private Long createOpId;

    public AmCoreIndex() {
    }

	
    public AmCoreIndex(long indexId, String indexName, String indexGroup, String schId, char state, Date createDate) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.indexGroup = indexGroup;
        this.schId = schId;
        this.state = state;
        this.createDate = createDate;
    }
    public AmCoreIndex(long indexId, String indexName, String indexGroup, String schId, String key1, String key2, String key3, char state, Date createDate, Long createOpId) {
       this.indexId = indexId;
       this.indexName = indexName;
       this.indexGroup = indexGroup;
       this.schId = schId;
       this.key1 = key1;
       this.key2 = key2;
       this.key3 = key3;
       this.state = state;
       this.createDate = createDate;
       this.createOpId = createOpId;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AM_CORE_INDEX$SEQ")
 	@SequenceGenerator(name="AM_CORE_INDEX$SEQ",sequenceName="AM_CORE_INDEX$SEQ",allocationSize=1)   
    @Column(name="INDEX_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getIndexId() {
        return this.indexId;
    }
    
    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }
    
    @Column(name="INDEX_NAME", nullable=false, length=100)
    public String getIndexName() {
        return this.indexName;
    }
    
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    
    @Column(name="INDEX_GROUP", nullable=false, length=50)
    public String getIndexGroup() {
        return this.indexGroup;
    }
    
    public void setIndexGroup(String indexGroup) {
        this.indexGroup = indexGroup;
    }
    
    @Column(name="SCH_ID", nullable=false, length=40)
    public String getSchId() {
        return this.schId;
    }
    
    public void setSchId(String schId) {
        this.schId = schId;
    }
    
    @Column(name="KEY_1", length=100)
    public String getKey1() {
        return this.key1;
    }
    
    public void setKey1(String key1) {
        this.key1 = key1;
    }
    
    @Column(name="KEY_2", length=100)
    public String getKey2() {
        return this.key2;
    }
    
    public void setKey2(String key2) {
        this.key2 = key2;
    }
    
    @Column(name="KEY_3", length=100)
    public String getKey3() {
        return this.key3;
    }
    
    public void setKey3(String key3) {
        this.key3 = key3;
    }
    
    @Column(name="STATE", nullable=false, length=1)
    public char getState() {
        return this.state;
    }
    
    public void setState(char state) {
        this.state = state;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", nullable=false, length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="CREATE_OP_ID", precision=12, scale=0)
    public Long getCreateOpId() {
        return this.createOpId;
    }
    
    public void setCreateOpId(Long createOpId) {
        this.createOpId = createOpId;
    }




}


