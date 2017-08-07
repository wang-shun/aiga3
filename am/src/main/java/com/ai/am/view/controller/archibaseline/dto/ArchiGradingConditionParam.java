package com.ai.am.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiGradingConditionParam  implements Serializable {
    private String name;
    private String state;
    private String applyUser;
    private String begainTime;
    private String endTime;
    private String ext1;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getBegainTime() {
		return begainTime;
	}
	public void setBegainTime(String begainTime) {
		this.begainTime = begainTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
