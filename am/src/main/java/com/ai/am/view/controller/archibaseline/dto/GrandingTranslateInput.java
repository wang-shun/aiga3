package com.ai.am.view.controller.archibaseline.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GrandingTranslateInput implements Serializable {
	private String sysState; 
	private String ext1; 
	private long idBelong;
	public String getSysState() {
		return sysState;
	}
	public void setSysState(String sysState) {
		this.sysState = sysState;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public long getIdBelong() {
		return idBelong;
	}
	public void setIdBelong(long idBelong) {
		this.idBelong = idBelong;
	} 
}
