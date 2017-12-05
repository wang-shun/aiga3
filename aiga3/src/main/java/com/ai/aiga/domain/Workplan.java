package com.ai.aiga.domain;
// Generated 2017-12-5 15:08:40 by Hibernate Tools 3.2.2.GA


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
 * Workplan generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name="WORKPLAN")
public class Workplan  implements java.io.Serializable {


     private long id;
     private String name;
     private String matters;
     private String classification;
     private String jobcontent;
     private String completion;
     private String projectcompletion;
     private String submittimely;
     private String fillquality;
     private String quality;
     private Date begaintime;
     private Date endtime;

    public Workplan() {
    }

	
    public Workplan(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Workplan(long id, String name, String matters, String classification, String jobcontent, String completion, String projectcompletion, String submittimely, String fillquality, String quality, Date begaintime, Date endtime) {
       this.id = id;
       this.name = name;
       this.matters = matters;
       this.classification = classification;
       this.jobcontent = jobcontent;
       this.completion = completion;
       this.projectcompletion = projectcompletion;
       this.submittimely = submittimely;
       this.fillquality = fillquality;
       this.quality = quality;
       this.begaintime = begaintime;
       this.endtime = endtime;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="work_plan$SEQ")    
     @SequenceGenerator(name="work_plan$SEQ",sequenceName="work_plan$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="NAME", nullable=false, length=20)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="MATTERS", length=30)
    public String getMatters() {
        return this.matters;
    }
    
    public void setMatters(String matters) {
        this.matters = matters;
    }
    
    @Column(name="CLASSIFICATION", length=10)
    public String getClassification() {
        return this.classification;
    }
    
    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    @Column(name="JOBCONTENT", length=500)
    public String getJobcontent() {
        return this.jobcontent;
    }
    
    public void setJobcontent(String jobcontent) {
        this.jobcontent = jobcontent;
    }
    
    @Column(name="COMPLETION", length=100)
    public String getCompletion() {
        return this.completion;
    }
    
    public void setCompletion(String completion) {
        this.completion = completion;
    }
    
    @Column(name="PROJECTCOMPLETION", length=10)
    public String getProjectcompletion() {
        return this.projectcompletion;
    }
    
    public void setProjectcompletion(String projectcompletion) {
        this.projectcompletion = projectcompletion;
    }
    
    @Column(name="SUBMITTIMELY", length=20)
    public String getSubmittimely() {
        return this.submittimely;
    }
    
    public void setSubmittimely(String submittimely) {
        this.submittimely = submittimely;
    }
    
    @Column(name="FILLQUALITY", length=20)
    public String getFillquality() {
        return this.fillquality;
    }
    
    public void setFillquality(String fillquality) {
        this.fillquality = fillquality;
    }
    
    @Column(name="QUALITY", length=100)
    public String getQuality() {
        return this.quality;
    }
    
    public void setQuality(String quality) {
        this.quality = quality;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="BEGAINTIME", length=7)
    public Date getBegaintime() {
        return this.begaintime;
    }
    
    public void setBegaintime(Date begaintime) {
        this.begaintime = begaintime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENDTIME", length=7)
    public Date getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }




}


