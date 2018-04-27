package com.ai.aiga.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="Db_Session_Count")
public class DbSessionCount  implements java.io.Serializable {


     private long id;
     private String systemName;
     private String systemSubdomain;
     private String name;
     private String businessInfo;
     private String createTime;

    public DbSessionCount() {
    }


    public DbSessionCount(long id, String systemName, String systemSubdomain, String name, String businessInfo, String createTime) {
       this.id = id;
       this.systemName = systemName;
       this.systemSubdomain = systemSubdomain;
       this.name = name;
       this.businessInfo = businessInfo;
       this.createTime = createTime;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Db_Session_Count$SEQ")
     @SequenceGenerator(name="Db_Session_Count$SEQ",sequenceName="Db_Session_Count$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="system_NAME", length=30)
    public String getSystemName() {
        return this.systemName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    @Column(name="system_Subdomain",  length=30)
    public String getSystemSubdomain() {
        return this.systemSubdomain;
    }
    
    public void setSystemSubdomain(String systemSubdomain) {
        this.systemSubdomain = systemSubdomain;
    }
    
    @Column(name="name",  length=30)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="business_Info",  length=30)
    public String getBusinessInfo() {
        return this.businessInfo;
    }
    
    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }
    
    @Column(name="create_time",  length=20)
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }    
    

}


