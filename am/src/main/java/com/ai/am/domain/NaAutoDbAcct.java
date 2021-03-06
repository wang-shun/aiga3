package com.ai.am.domain;
// Generated 2017-3-28 11:20:41 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NaAutoDbAcct generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_DB_ACCT")
public class NaAutoDbAcct  implements java.io.Serializable {

     private String dbAcctCode;
     private String username;
     private String password;
     private String host;
     private Integer port;
     private String sid;
     private Short defaultConnMin;
     private Short defaultConnMax;
     private Character state;
     private String remarks;

    public NaAutoDbAcct() {
    }

	
    public NaAutoDbAcct(String dbAcctCode) {
        this.dbAcctCode = dbAcctCode;
    }
    public NaAutoDbAcct(String dbAcctCode, String username, String password, String host, Integer port, String sid, Short defaultConnMin, Short defaultConnMax, Character state, String remarks) {
       this.dbAcctCode = dbAcctCode;
       this.username = username;
       this.password = password;
       this.host = host;
       this.port = port;
       this.sid = sid;
       this.defaultConnMin = defaultConnMin;
       this.defaultConnMax = defaultConnMax;
       this.state = state;
       this.remarks = remarks;
    }
   
     @Id 
    @Column(name="DB_ACCT_CODE", unique=true, nullable=false)
    public String getDbAcctCode() {
        return this.dbAcctCode;
    }
    
    public void setDbAcctCode(String dbAcctCode) {
        this.dbAcctCode = dbAcctCode;
    }
    
    @Column(name="USERNAME")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="PASSWORD")
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="HOST")
    public String getHost() {
        return this.host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    @Column(name="PORT", precision=5, scale=0)
    public Integer getPort() {
        return this.port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    @Column(name="SID")
    public String getSid() {
        return this.sid;
    }
    
    public void setSid(String sid) {
        this.sid = sid;
    }
    
    @Column(name="DEFAULT_CONN_MIN", precision=3, scale=0)
    public Short getDefaultConnMin() {
        return this.defaultConnMin;
    }
    
    public void setDefaultConnMin(Short defaultConnMin) {
        this.defaultConnMin = defaultConnMin;
    }
    
    @Column(name="DEFAULT_CONN_MAX", precision=3, scale=0)
    public Short getDefaultConnMax() {
        return this.defaultConnMax;
    }
    
    public void setDefaultConnMax(Short defaultConnMax) {
        this.defaultConnMax = defaultConnMax;
    }
    
    @Column(name="STATE", length=1)
    public Character getState() {
        return this.state;
    }
    
    public void setState(Character state) {
        this.state = state;
    }
    
    @Column(name="REMARKS", length=1000)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }




}


