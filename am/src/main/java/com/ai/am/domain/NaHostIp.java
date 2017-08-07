package com.ai.am.domain;
// Generated 2017-4-19 10:56:03 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaHostIp generated by hbm2java
 */
@Entity
@Table(name="NA_HOST_IP"
    ,schema="AIGA"
)
public class NaHostIp  implements java.io.Serializable {


     private Long id;
     private String sysName;
     private String modelName;
     private String ip;
     private String hostName;
     private String remark;
     private Long planId;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaHostIp() {
    }

	
    public NaHostIp(Long id) {
        this.id = id;
    }
    public NaHostIp(Long id, String sysName, String modelName, String ip, String hostName, String remark, Long planId, String ext1, String ext2, String ext3) {
       this.id = id;
       this.sysName = sysName;
       this.modelName = modelName;
       this.ip = ip;
       this.hostName = hostName;
       this.remark = remark;
       this.planId = planId;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_HOST_IP$SEQ")
     @SequenceGenerator(name="NA_HOST_IP$SEQ",sequenceName="NA_HOST_IP$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="SYS_NAME", length=2000)
    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    @Column(name="MODEL_NAME", length=2000)
    public String getModelName() {
        return this.modelName;
    }
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    @Column(name="IP", length=2000)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    @Column(name="HOST_NAME", length=2000)
    public String getHostName() {
        return this.hostName;
    }
    
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    @Column(name="REMARK", length=2000)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="EXT_1", length=20)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=20)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=20)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


