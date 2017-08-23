package com.ai.aiga.view.controller.photoWall.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NaImageUploadRequest {

    private long id;
    private String fileName;
    private String imgSrc;
    private String title;
    private String description;
    private Long likeCount;
    private Long commentCount;
    private String isShared;
    private Date createTime;
    private Long planId;
    private Long fileType;
    private Long createId;
    private String ext1;
    private String ext2;
    private String ext3;
    
}
