package com.ai.aiga.domain;

import java.io.Serializable;
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

@Entity
@Table(name="workplan")
public class WorkPlan implements Serializable {

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
	
	public WorkPlan(){
	}
	
    public WorkPlan(long id, String name,String matters,String classification,String jobcontent,String completion,String projectcompletion,
    		String submittimely,String fillquality,String quality, Date begaintime, Date endtime) {
		super();
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
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    @Column(name="NAME", length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(name="matters", length=30)
	public String getMatters() {
		return matters;
	}
	public void setMatters(String matters) {
		this.matters = matters;
	}
	
	@Column(name="classification", length=10)
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	@Column(name="jobcontent", length=500)
	public String getJobcontent() {
		return jobcontent;
	}
	public void setJobcontent(String jobcontent) {
		this.jobcontent = jobcontent;
	}
	
	@Column(name="completion", length=100)
	public String getCompletion() {
		return completion;
	}
	public void setCompletion(String completion) {
		this.completion = completion;
	}
	
	@Column(name="projectcompletion", length=10)
	public String getProjectcompletion() {
		return projectcompletion;
	}
	public void setProjectcompletion(String projectcompletion) {
		this.projectcompletion = projectcompletion;
	}
	@Column(name="submittimely", length=20)
	public String getSubmittimely() {
		return submittimely;
	}
	public void setSubmittimely(String submittimely) {
		this.submittimely = submittimely;
	}
	@Column(name="fillquality", length=20)
	public String getFillquality() {
		return fillquality;
	}
	public void setFillquality(String fillquality) {
		this.fillquality = fillquality;
	}
	@Column(name="Quality", length=100)
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}

	
	
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="begaintime")
	public Date getBegaintime() {
		return begaintime;
	}
	public void setBegaintime(Date begaintime) {
		this.begaintime = begaintime;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="endtime")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	
	
	
	
}
