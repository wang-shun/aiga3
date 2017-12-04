package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CacheCloudPlatformReport extends PlatcormOperateBase implements Serializable {

    private String cacheBlockIsZero;
    private String cacheBlockGtTenM;
    private String increaseCacheBlockNum;
    private String totalCacheBlockNum;
    private String changeChainRatio;
    
}
