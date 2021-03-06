package com.ai.am.domain;
// Generated 2017-6-9 10:25:44 by Hibernate Tools 3.2.2.GA


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
 * NaHomewInfo generated by hbm2java
 */
@Entity
@Table(name="NA_HOMEW_INFO"
)
public class NaHomewInfo  implements java.io.Serializable {


     private Long id;
     private Long month;
     private Long onlineplancount;
     private Long abnormalcount;
     private Long faultcount;
     private String resucrate;
     private String esbsucrate;
     private String cbosssucrate;
     private Date createDate;
     private Date updetTime;
     private String ext1;
     private String ext2;
     private String ext3;
     private String ext4;

    public NaHomewInfo() {
    }

	
    public NaHomewInfo(Long id) {
        this.id = id;
    }
    public NaHomewInfo(Long id, Long month, Long onlineplancount, Long abnormalcount, Long faultcount, String resucrate, String esbsucrate, String cbosssucrate, Date createDate, Date updetTime, String ext1, String ext2, String ext3, String ext4) {
       this.id = id;
       this.month = month;
       this.onlineplancount = onlineplancount;
       this.abnormalcount = abnormalcount;
       this.faultcount = faultcount;
       this.resucrate = resucrate;
       this.esbsucrate = esbsucrate;
       this.cbosssucrate = cbosssucrate;
       this.createDate = createDate;
       this.updetTime = updetTime;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.ext4 = ext4;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_HOMEW_INFO$SEQ")
     @SequenceGenerator(name="NA_HOMEW_INFO$SEQ",sequenceName="NA_HOMEW_INFO$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=20, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="MONTH", precision=20, scale=0)
    public Long getMonth() {
        return this.month;
    }
    
    public void setMonth(Long month) {
        this.month = month;
    }
    
    @Column(name="ONLINEPLANCOUNT", precision=20, scale=0)
    public Long getOnlineplancount() {
        return this.onlineplancount;
    }
    
    public void setOnlineplancount(Long onlineplancount) {
        this.onlineplancount = onlineplancount;
    }
    
    @Column(name="ABNORMALCOUNT", precision=20, scale=0)
    public Long getAbnormalcount() {
        return this.abnormalcount;
    }
    
    public void setAbnormalcount(Long abnormalcount) {
        this.abnormalcount = abnormalcount;
    }
    
    @Column(name="FAULTCOUNT", precision=20, scale=0)
    public Long getFaultcount() {
        return this.faultcount;
    }
    
    public void setFaultcount(Long faultcount) {
        this.faultcount = faultcount;
    }
    
    @Column(name="RESUCRATE", length=20)
    public String getResucrate() {
        return this.resucrate;
    }
    
    public void setResucrate(String resucrate) {
        this.resucrate = resucrate;
    }
    
    @Column(name="ESBSUCRATE", length=20)
    public String getEsbsucrate() {
        return this.esbsucrate;
    }
    
    public void setEsbsucrate(String esbsucrate) {
        this.esbsucrate = esbsucrate;
    }
    
    @Column(name="CBOSSSUCRATE", length=20)
    public String getCbosssucrate() {
        return this.cbosssucrate;
    }
    
    public void setCbosssucrate(String cbosssucrate) {
        this.cbosssucrate = cbosssucrate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDET_TIME", length=7)
    public Date getUpdetTime() {
        return this.updetTime;
    }
    
    public void setUpdetTime(Date updetTime) {
        this.updetTime = updetTime;
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
    
    @Column(name="EXT_4", length=20)
    public String getExt4() {
        return this.ext4;
    }
    
    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }




}


