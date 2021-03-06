package com.ai.am.view.controller.common.dto;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	
    private long funcId;
    private String funcCode;
    private String name;
    private String notes;
    private long parentId;
    private Byte funcLevel;
    private Short funSeq;
    private String viewname;
    private String dllPath;
    private String funcImg;
    private String funcArg;
    
    List<Menu> subMenus = new ArrayList<Menu>();

	public long getFuncId() {
		return funcId;
	}

	public void setFuncId(long funcId) {
		this.funcId = funcId;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public Byte getFuncLevel() {
		return funcLevel;
	}

	public void setFuncLevel(Byte funcLevel) {
		this.funcLevel = funcLevel;
	}

	public Short getFunSeq() {
		return funSeq;
	}

	public void setFunSeq(Short funSeq) {
		this.funSeq = funSeq;
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

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}
	
	public void addSubMenus(Menu subMenu) {
		this.subMenus.add(subMenu);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [funcId=").append(funcId).append(", funcCode=").append(funcCode).append(", name=")
				.append(name).append(", notes=").append(notes).append(", parentId=").append(parentId)
				.append(", funcLevel=").append(funcLevel).append(", funSeq=").append(funSeq).append(", viewname=")
				.append(viewname).append(", dllPath=").append(dllPath).append(", funcImg=").append(funcImg)
				.append(", funcArg=").append(funcArg).append(", subMenus=").append(subMenus).append("]");
		return builder.toString();
	}
	
	
	

}
