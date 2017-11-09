package com.ai.aiga.service.cloudmanage.dto;

import java.io.Serializable;
import lombok.Data;
@Data
@SuppressWarnings("serial")
public class CloudThirdParam implements Serializable {
    private Long onlysysId;
    private Long idThird;
    private String name;
    private String systemCode;
    private String systemFunction;
    private String description;
    private String code;
    private Long idSecond;
    private String belongLevel;
    private String department;
    private String projectInfo;
    private String designInfo;
    private String rankInfo;
    private String sysState;
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
    private String developer;  
    private String cloudOrderId;
}
