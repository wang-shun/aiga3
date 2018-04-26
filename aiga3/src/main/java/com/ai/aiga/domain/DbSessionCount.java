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
     private String systemNAME;
     private String systemSubdomain;
     private String createTime;

    public DbSessionCount() {
    }


    public DbSessionCount(long id, String systemNAME, String systemSubdomain, String createTime) {
       this.id = id;
       this.systemNAME = systemNAME;
       this.systemSubdomain = systemSubdomain;
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
    public String getSystemNAME() {
        return this.systemNAME;
    }
    
    public void setSystemNAME(String systemNAME) {
        this.systemNAME = systemNAME;
    }
    
    @Column(name="system_Subdomain",  length=30)
    public String getSystemSubdomain() {
        return this.systemSubdomain;
    }
    
    public void setSystemSubdomain(String systemSubdomain) {
        this.systemSubdomain = systemSubdomain;
    }
    
    @Column(name="create_time",  length=20)
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }    
    

}


