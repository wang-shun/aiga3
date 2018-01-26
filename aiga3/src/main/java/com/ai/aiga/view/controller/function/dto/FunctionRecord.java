package com.ai.aiga.view.controller.function.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FunctionRecord {
	public String url;
	public String menuId;
	public String menuName;
	public String user;
	public Date time;
}
