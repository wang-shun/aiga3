package com.ai.aiga.service.cloudmanage.dto;

import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class CloudFirstParam implements Serializable {
    private Long idFirst;
    private String name;
    private String shortName;
    private String description;
    private String code;
    private String belongLevel;
    private String state;
    private Long applyId;
    private String applyUser;
    private String createDate;
    private String modifyDate;
    private String identifiedInfo;
    private String fileInfo;
    private String ext1;
    private String ext2;
    private String ext3;
    private String cloudOrderId;
}
