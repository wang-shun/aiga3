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
 * InspectRadarRule generated by hbm2java
 */
@Entity
@Table(name="INSPECT_RADAR_RULE")
public class InspectRadarRule  implements java.io.Serializable {


     private long ruleId;
     private Long tempId;
     private Long sysId;
     private String indexValue;
     private Date indexTime;
     private String indexSql;
     private String indexMark;
     private Character state;
     private String ruleType;
     private String ruleFunction;
     private String ruleValue;
     private Date createTime;
     private String ext1;
     private String ext2;
     private String ext3;

    public InspectRadarRule() {
    }

	
    public InspectRadarRule(long ruleId) {
        this.ruleId = ruleId;
    }
    public InspectRadarRule(long ruleId, Long tempId, Long sysId, String indexValue, Date indexTime, String indexSql, String indexMark, Character state, String ruleType, String ruleFunction, String ruleValue, Date createTime, String ext1, String ext2, String ext3) {
       this.ruleId = ruleId;
       this.tempId = tempId;
       this.sysId = sysId;
       this.indexValue = indexValue;
       this.indexTime = indexTime;
       this.indexSql = indexSql;
       this.indexMark = indexMark;
       this.state = state;
       this.ruleType = ruleType;
       this.ruleFunction = ruleFunction;
       this.ruleValue = ruleValue;
       this.createTime = createTime;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="INSPECT_RADAR_RULE$SEQ")
     @SequenceGenerator(name="INSPECT_RADAR_RULE$SEQ",sequenceName="INSPECT_RADAR_RULE$SEQ",allocationSize=1)    
    @Column(name="RULE_ID", nullable=false, precision=10, scale=0)
    public long getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }
    
    @Column(name="TEMP_ID", precision=10, scale=0)
    public Long getTempId() {
        return this.tempId;
    }
    
    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }
    
    @Column(name="SYS_ID", precision=10, scale=0)
    public Long getSysId() {
        return this.sysId;
    }
    
    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    
    @Column(name="INDEX_VALUE")
    public String getIndexValue() {
        return this.indexValue;
    }
    
    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INDEX_TIME", length=7)
    public Date getIndexTime() {
        return this.indexTime;
    }
    
    public void setIndexTime(Date indexTime) {
        this.indexTime = indexTime;
    }
    
    @Column(name="INDEX_SQL", length=2000)
    public String getIndexSql() {
        return this.indexSql;
    }
    
    public void setIndexSql(String indexSql) {
        this.indexSql = indexSql;
    }
    
    @Column(name="INDEX_MARK")
    public String getIndexMark() {
        return this.indexMark;
    }
    
    public void setIndexMark(String indexMark) {
        this.indexMark = indexMark;
    }
    
    @Column(name="STATE", length=1)
    public Character getState() {
        return this.state;
    }
    
    public void setState(Character state) {
        this.state = state;
    }
    
    @Column(name="RULE_TYPE")
    public String getRuleType() {
        return this.ruleType;
    }
    
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
    
    @Column(name="RULE_FUNCTION")
    public String getRuleFunction() {
        return this.ruleFunction;
    }
    
    public void setRuleFunction(String ruleFunction) {
        this.ruleFunction = ruleFunction;
    }
    
    @Column(name="RULE_VALUE")
    public String getRuleValue() {
        return this.ruleValue;
    }
    
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


