package com.ai.aiga.view.json;

public class CompTreeResponse {

	private Long id;
	private Long pId;
	private String name;
	private Character ifLeaf;
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
	
}
