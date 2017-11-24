package com.ai.aiga.view.controller.archibaseline.dto.thirdview;


import org.springframework.web.multipart.MultipartFile;
import com.ai.aiga.service.cloudmanage.dto.CloudUserInfo;
import lombok.Data;

@Data
public class ArchiThirdApplyParams  implements java.io.Serializable {

     private String cloudOrderId;
     private String name;
     private String systemFunction;
     private String description;
     private String code;
     private long idSecond;
     private String belongLevel;
     private String department;
     private String projectInfo;
     private String designInfo;
     private String rankInfo;
     private CloudUserInfo applyUser;
     private String media;
     private String sysState;
     private String sysStateTime;
     private String developer;
     private MultipartFile file;

}


