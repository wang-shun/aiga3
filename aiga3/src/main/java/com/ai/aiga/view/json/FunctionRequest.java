package com.ai.aiga.view.json;

import java.util.Date;

public class FunctionRequest {
	
    private Long funcId;
    private Long parentId;
    private String funcCode;
    private String name;
    private String viewname;
    private String dllPath;
    private String funcImg;
    private String funcArg;
    private Character funcType;
    private Byte state;
    private String notes;
    
    
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getViewname() {
		return viewname;
	}
	public void setViewname(String viewname) {
		this.viewname = viewname;
	}
	public String getDllPath() {
		return dllPath;
	}
	public void setDllPath(String dllPath) {
		this.dllPath = dllPath;
	}
	public String getFuncImg() {
		return funcImg;
	}
	public void setFuncImg(String funcImg) {
		this.funcImg = funcImg;
	}
	public String getFuncArg() {
		return funcArg;
	}
	public void setFuncArg(String funcArg) {
		this.funcArg = funcArg;
	}
	public Character getFuncType() {
		return funcType;
	}
	public void setFuncType(Character funcType) {
		this.funcType = funcType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    
}
