package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ArchitectureFirstRequest implements Serializable {

    private Long idFirst;
    private String name;
    private String shortName;
    private String description;
    private String code;
    private String belongLevel;
    private String state;
    private Long applyId;
    private String applyUser;
    private Date createDate;
    private Date modifyDate;
    private String identifiedInfo;
    private String fileInfo;
    private String ext1;
    private String ext2;
    private String ext3;
    private String cloudOrderId;

	@Override
	public String toString() {
		return "ArchitectureFirstRequest [idFirst=" + idFirst + ", name="
				+ name + ", shortName=" + shortName + ", description="
				+ description + ", code=" + code + ", belongLevel="
				+ belongLevel + ", state=" + state + ", applyId=" + applyId
				+ ", applyUser=" + applyUser + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", identifiedInfo="
				+ identifiedInfo + ", fileInfo=" + fileInfo + ", ext1=" + ext1
				+ ", ext2=" + ext2 + ", ext3=" + ext3 + "]";
	}
    
    
}
