package com.ai.aiga.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GrandingTranslateOutput implements Serializable {
	private String idBelongName;
	private String sysStateName;
	public String getIdBelongName() {
		return idBelongName;
	}
	public void setIdBelongName(String idBelongName) {
		this.idBelongName = idBelongName;
	}
	public String getSysStateName() {
		return sysStateName;
	}
	public void setSysStateName(String sysStateName) {
		this.sysStateName = sysStateName;
	}
}
