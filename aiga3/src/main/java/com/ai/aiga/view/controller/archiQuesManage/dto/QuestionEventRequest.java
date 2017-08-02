package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class QuestionEventRequest implements Serializable {

	private long id;
	private String name;
	private Date startTime;
	private Date endTime;
	private String state;
	private String ext1;
	private String ext2;
	private String ext3;

	public QuestionEventRequest(){
	}
	
    public QuestionEventRequest(long id, String name, Date startTime, Date endTime, String state, String ext1, String ext2,
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

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
}
