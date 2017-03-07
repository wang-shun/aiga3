package com.ai.aiga.view.json;

import java.sql.Clob;

public class CtrlTreeResponse {
	private Long id;
	private Long pId;
	private String name;
	private Character ifLeaf;
	private Clob script;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Character getIfLeaf() {
		return ifLeaf;
	}
	public void setIfLeaf(Character ifLeaf) {
		this.ifLeaf = ifLeaf;
	}
	public Clob getScript() {
		return script;
	}
	public void setScript(Clob script) {
		this.script = script;
	}
	
	
}
