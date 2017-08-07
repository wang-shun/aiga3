package com.ai.am.view.controller.archibaseline.dto.login;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class SimpleUserInfo implements Serializable {
	private String userId;
	private String userName;
	private String token;
}
