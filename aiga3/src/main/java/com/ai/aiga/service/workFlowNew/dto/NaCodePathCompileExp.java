package com.ai.aiga.service.workFlowNew.dto;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class NaCodePathCompileExp implements java.io.Serializable {

	
	private String status;
	private String haderrors;
	private String hadwarnings;
	private String isabort;
	private String iscancelled;
	private String targeturi;
	private String value;
	private Date planDate;
	private Long compileNum;
	private String ext1;
	private String ext2;
	private Date startTime;
	private Date stopTime;
	private Long total;
	private String result;


}
