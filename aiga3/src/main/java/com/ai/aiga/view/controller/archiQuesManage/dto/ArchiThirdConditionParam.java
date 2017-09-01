package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;
@Data
@SuppressWarnings("serial")
public class ArchiThirdConditionParam implements Serializable {
    private Long idThird;
    private String name;
    private Long onlysysId;
}
