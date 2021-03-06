package com.ai.am.domain;
// Generated 2017-3-22 17:27:46 by Hibernate Tools 3.2.2.GA


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
 * NaAutoPropertyCorrelation generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_PROPERTY_CORRELATION"
    ,schema="AIGA"
)
public class NaAutoPropertyCorrelation  implements java.io.Serializable {


     private long correlationId;
     private Long propertyCfgId;
     private String propertyId;
     private String correlationTable;
     private String correlationField;
     private String db;
     private Date createDate;
     private Date doneDate;
     private Long opId;
     private Long orgId;

    public NaAutoPropertyCorrelation() {
    }

	
    public NaAutoPropertyCorrelation(long correlationId) {
        this.correlationId = correlationId;
    }
    public NaAutoPropertyCorrelation(long correlationId, Long propertyCfgId, String propertyId, String correlationTable, String correlationField, String db, Date createDate, Date doneDate, Long opId, Long orgId) {
       this.correlationId = correlationId;
       this.propertyCfgId = propertyCfgId;
       this.propertyId = propertyId;
       this.correlationTable = correlationTable;
       this.correlationField = correlationField;
       this.db = db;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.opId = opId;
       this.orgId = orgId;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_PROPERTY_CORRELA$SEQ")
     @SequenceGenerator(name="NA_AUTO_PROPERTY_CORRELA$SEQ",sequenceName="NA_AUTO_PROPERTY_CORRELA$SEQ",allocationSize=1)
    @Column(name="CORRELATION_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getCorrelationId() {
        return this.correlationId;
    }
    
    public void setCorrelationId(long correlationId) {
        this.correlationId = correlationId;
    }
    
    @Column(name="PROPERTY_CFG_ID", precision=10, scale=0)
    public Long getPropertyCfgId() {
        return this.propertyCfgId;
    }
    
    public void setPropertyCfgId(Long propertyCfgId) {
        this.propertyCfgId = propertyCfgId;
    }
    
    @Column(name="PROPERTY_ID", length=30)
    public String getPropertyId() {
        return this.propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    @Column(name="CORRELATION_TABLE", length=50)
    public String getCorrelationTable() {
        return this.correlationTable;
    }
    
    public void setCorrelationTable(String correlationTable) {
        this.correlationTable = correlationTable;
    }
    
    @Column(name="CORRELATION_FIELD", length=30)
    public String getCorrelationField() {
        return this.correlationField;
    }
    
    public void setCorrelationField(String correlationField) {
        this.correlationField = correlationField;
    }
    
    @Column(name="DB", length=30)
    public String getDb() {
        return this.db;
    }
    
    public void setDb(String db) {
        this.db = db;
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


