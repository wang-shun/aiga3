package com.ai.aiga.domain.inspectradar;
// Generated 2018-8-20 17:28:17 by Hibernate Tools 3.2.2.GA


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
 * InspectRadarResult generated by hbm2java
 */
@Entity
@Table(name="INSPECT_RADAR_RESULT")
public class InspectRadarResult  implements java.io.Serializable {


     private long resultId;
     private Long sysId;
     private String totalMark;
     private String aqMark;
     private String rlMark;
     private String jkMark;
     private String gkyMark;
     private String rxkyMark;
     private String pzMark;
     private String rzMark;
     private String fcMark;
     private Date createTime;
     private String sponsor;
     private String ext1;
     private String ext2;
     private String ext3;

    public InspectRadarResult() {
    }

	
    public InspectRadarResult(long resultId) {
        this.resultId = resultId;
    }
    public InspectRadarResult(long resultId, Long sysId, String totalMark, String aqMark, String rlMark, String jkMark, String gkyMark, String rxkyMark, String pzMark, String rzMark, String fcMark, Date createTime, String sponsor, String ext1, String ext2, String ext3) {
       this.resultId = resultId;
       this.sysId = sysId;
       this.totalMark = totalMark;
       this.aqMark = aqMark;
       this.rlMark = rlMark;
       this.jkMark = jkMark;
       this.gkyMark = gkyMark;
       this.rxkyMark = rxkyMark;
       this.pzMark = pzMark;
       this.rzMark = rzMark;
       this.fcMark = fcMark;
       this.createTime = createTime;
       this.sponsor = sponsor;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="INSPECT_RADAR_RESULT$SEQ")
     @SequenceGenerator(name="INSPECT_RADAR_RESULT$SEQ",sequenceName="INSPECT_RADAR_RESULT$SEQ",allocationSize=1)    
    @Column(name="RESULT_ID", nullable=false, precision=10, scale=0)
    public long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="SYS_ID", precision=10, scale=0)
    public Long getSysId() {
        return this.sysId;
    }
    
    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    
    @Column(name="TOTAL_MARK")
    public String getTotalMark() {
        return this.totalMark;
    }
    
    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }
    
    @Column(name="AQ_MARK")
    public String getAqMark() {
        return this.aqMark;
    }
    
    public void setAqMark(String aqMark) {
        this.aqMark = aqMark;
    }
    
    @Column(name="RL_MARK")
    public String getRlMark() {
        return this.rlMark;
    }
    
    public void setRlMark(String rlMark) {
        this.rlMark = rlMark;
    }
    
    @Column(name="JK_MARK")
    public String getJkMark() {
        return this.jkMark;
    }
    
    public void setJkMark(String jkMark) {
        this.jkMark = jkMark;
    }
    
    @Column(name="GKY_MARK")
    public String getGkyMark() {
        return this.gkyMark;
    }
    
    public void setGkyMark(String gkyMark) {
        this.gkyMark = gkyMark;
    }
    
    @Column(name="RXKY_MARK")
    public String getRxkyMark() {
        return this.rxkyMark;
    }
    
    public void setRxkyMark(String rxkyMark) {
        this.rxkyMark = rxkyMark;
    }
    
    @Column(name="PZ_MARK")
    public String getPzMark() {
        return this.pzMark;
    }
    
    public void setPzMark(String pzMark) {
        this.pzMark = pzMark;
    }
    
    @Column(name="RZ_MARK")
    public String getRzMark() {
        return this.rzMark;
    }
    
    public void setRzMark(String rzMark) {
        this.rzMark = rzMark;
    }
    
    @Column(name="FC_MARK")
    public String getFcMark() {
        return this.fcMark;
    }
    
    public void setFcMark(String fcMark) {
        this.fcMark = fcMark;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="SPONSOR")
    public String getSponsor() {
        return this.sponsor;
    }
    
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    
    @Column(name="EXT1")
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT2")
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT3")
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}


