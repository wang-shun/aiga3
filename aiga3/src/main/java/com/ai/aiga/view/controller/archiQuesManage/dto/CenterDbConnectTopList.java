package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CenterDbConnectTopList implements Serializable {

    private String id;
    private String system;
    private long lastmonth;
    private long thismonth;
    private long increase;
    private double percentage;
  
}
