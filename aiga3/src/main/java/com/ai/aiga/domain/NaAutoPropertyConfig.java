package com.ai.aiga.domain;
// Generated 2017-3-22 16:50:57 by Hibernate Tools 3.2.2.GA


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
 * NaAutoPropertyConfig generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_PROPERTY_CONFIG"
    ,schema="AIGA"
)
public class NaAutoPropertyConfig  implements java.io.Serializable {


     private long cfgId;
     private String propertyId;
     private String propertyName;
     private String propertyField;
     private String dependencyTable;
     private String dependencyField;
     private String alias;
     private String db;
     private Byte sortId;
     private Date createDate;
     private Date doneDate;
     private Long opId;
     private Long orgId;

    public NaAutoPropertyConfig() {
    }

	
    public NaAutoPropertyConfig(long cfgId) {
        this.cfgId = cfgId;
    }
    public NaAutoPropertyConfig(long cfgId, String propertyId, String propertyName, String propertyField, String dependencyTable, String dependencyField, String alias, String db, Byte sortId, Date createDate, Date doneDate, Long opId, Long orgId) {
       this.cfgId = cfgId;
       this.propertyId = propertyId;
       this.propertyName = propertyName;
       this.propertyField = propertyField;
       this.dependencyTable = dependencyTable;
       this.dependencyField = dependencyField;
       this.alias = alias;
       this.db = db;
       this.sortId = sortId;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.opId = opId;
       this.orgId = orgId;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_PROPERTY_CONFIG$SEQ")
     @SequenceGenerator(name="NA_AUTO_PROPERTY_CONFIG$SEQ",sequenceName="NA_AUTO_PROPERTY_CONFIG$SEQ",allocationSize=1)
    @Column(name="CFG_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getCfgId() {
        return this.cfgId;
    }
    
    public void setCfgId(long cfgId) {
        this.cfgId = cfgId;
    }
    
    @Column(name="PROPERTY_ID", length=30)
    public String getPropertyId() {
        return this.propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    @Column(name="PROPERTY_NAME", length=50)
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    @Column(name="PROPERTY_FIELD", length=30)
    public String getPropertyField() {
        return this.propertyField;
    }
    
    public void setPropertyField(String propertyField) {
        this.propertyField = propertyField;
    }
    
    @Column(name="DEPENDENCY_TABLE", length=500)
    public String getDependencyTable() {
        return this.dependencyTable;
    }
    
    public void setDependencyTable(String dependencyTable) {
        this.dependencyTable = dependencyTable;
    }
    
    @Column(name="DEPENDENCY_FIELD", length=30)
    public String getDependencyField() {
        return this.dependencyField;
    }
    
    public void setDependencyField(String dependencyField) {
        this.dependencyField = dependencyField;
    }
    
    @Column(name="ALIAS", length=30)
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    @Column(name="DB", length=30)
    public String getDb() {
        return this.db;
    }
    
    public void setDb(String db) {
        this.db = db;
    }
    
    @Column(name="SORT_ID", precision=2, scale=0)
    public Byte getSortId() {
        return this.sortId;
    }
    
    public void setSortId(Byte sortId) {
        this.sortId = sortId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=11)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=11)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    
    @Column(name="OP_ID", precision=12, scale=0)
    public Long getOpId() {
        return this.opId;
    }
    
    public void setOpId(Long opId) {
        this.opId = opId;
    }
    
    @Column(name="ORG_ID", precision=12, scale=0)
    public Long getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }




}


