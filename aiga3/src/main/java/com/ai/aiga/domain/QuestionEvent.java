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
@Table(name="QUESTION_EVENT")
public class QuestionEvent implements Serializable {

	private long id;
	private String name;
	private Date startTime;
	private Date endTime;
	private String state;
	private String ext1;
	private String ext2;
	private String ext3;

	public QuestionEvent(){
	}
	
    public QuestionEvent(long id, String name, Date startTime, Date endTime, String state, String ext1, String ext2,
			String ext3) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
	}
	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="QUESTION_EVENT$SEQ")
    @SequenceGenerator(name="QUESTION_EVENT$SEQ",sequenceName="QUESTION_EVENT$SEQ",allocationSize=1)
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_TIME", length=7)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_TIME", length=7)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name="STATE", length=50)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name="EXT_1", length=100)
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	@Column(name="EXT_2", length=100)
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	@Column(name="EXT_3", length=100)
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
	
}
