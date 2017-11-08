package com.ai.aiga.service.cloudmanage.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class CloudOutput implements Serializable {
	public Long success;
	public String message;
}
