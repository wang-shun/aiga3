package com.ai.am.domain;
// Generated 2017-4-26 11:08:56 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaRiskRatingScale generated by hbm2java
 */
@Entity
@Table(name="NA_RISK_RATING_SCALE"
)
public class NaRiskRatingScale  implements java.io.Serializable {


     private Long id;
     private String measureFactor;
     private String condition;
     private String selfEvaluation;
     private String score;
     private Long planId;
     private String fileName;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaRiskRatingScale() {
    }

	
    public NaRiskRatingScale(Long id) {
        this.id = id;
    }
    public NaRiskRatingScale(Long id, String measureFactor, String condition, String selfEvaluation, String score, Long planId, String fileName, String ext1, String ext2, String ext3) {
       this.id = id;
       this.measureFactor = measureFactor;
       this.condition = condition;
       this.selfEvaluation = selfEvaluation;
       this.score = score;
       this.planId = planId;
       this.fileName = fileName;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_RISK_RATING_SCALE$SEQ")
     @SequenceGenerator(name="NA_RISK_RATING_SCALE$SEQ",sequenceName="NA_RISK_RATING_SCALE$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="MEASURE_FACTOR", length=2000)
    public String getMeasureFactor() {
        return this.measureFactor;
    }
    
    public void setMeasureFactor(String measureFactor) {
        this.measureFactor = measureFactor;
    }
    
    @Column(name="CONDITION", length=2000)
    public String getCondition() {
        return this.condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    @Column(name="SELF_EVALUATION", length=2000)
    public String getSelfEvaluation() {
        return this.selfEvaluation;
    }
    
    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }
    
    @Column(name="SCORE", length=2000)
    public String getScore() {
        return this.score;
    }
    
    public void setScore(String score) {
        this.score = score;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="FILE_NAME", length=2000)
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="EXT_1", length=2000)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=2000)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=2000)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}

