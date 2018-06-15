package com.ai.aiga.view.controller.mail.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InspectMailData  implements Serializable{
		private String resultId;
		private String cjDate;
		private Long reportShowOrder;
		private String reportModuleCode;
		private Long moduleShowOrder;
		private String moduleType;
		private String reportContent1;
		private String reportContent2;
		private String reportContent3;
}
