package com.ai.aiga.service.workFlowNew.dto;

import java.io.Serializable;

import lombok.Data;

/** * @author  lh 
    * @date 创建时间：2017年4月26日 上午11:31:38
    */
@Data
public class NaHostConfigListExcel implements Serializable{
     private String configName;
     private String bmConfig;
     private String onlineConfigList;
     private String adjustConfigRecord;
     private String testConfigRecord;
     private String ZConfigRecord;
     private String SConfigRecord;
     private String MConfigRecord;
     private String devMan;
     private String testMan;
     private String bm;
     private Long planId;
     

     
}
