package com.ai.aiga.service.ArchIndex.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class QuestionInfoListExcel implements Serializable {

    private long quesId;
    private String quesType;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String diffProblem;
    private String abstracts;
    private String occurEnvironment;
    private String belongProject;
    private long idFirst;
    private long idSecond;
    private long idThird;
    private String sysVersion;
    private String priority;
    private String defectLevel;
    private String state;
    private String requestInfo;
    private String identifiedInfo;
    private String solvedInfo;
    private Date createDate;
    private Date modifyDate;
    private String reportor;
    private String appointedPerson;
    private String ext1;
    private String ext2;
    private String ext3;
     
}
