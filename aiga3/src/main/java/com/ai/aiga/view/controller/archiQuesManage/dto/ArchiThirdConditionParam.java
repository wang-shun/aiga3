package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiThirdConditionParam implements Serializable {
    private long idThird;
    private String name;
	public long getIdThird() {
		return idThird;
	}
	public void setIdThird(long idThird) {
		this.idThird = idThird;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
