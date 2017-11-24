package com.ai.aiga.service.cloudmanage.dto;

import java.io.Serializable;

import lombok.Data;
@SuppressWarnings("serial")
@Data
public class CloudUserInfo implements Serializable {
	private String account; //账号
	private String name; //名称
	private String email; //邮箱
	private String mobile; //手机号码
}
