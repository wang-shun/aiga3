package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CacheCloudPlatformReport implements Serializable {

    private String key1;
    private String cacheBlockIsZero;
    private String cacheBlockGtTenM;
    private String increaseCacheBlockNum;
    private String totalCacheBlockNum;
    private String changeChainRatio;
    private String settMonth;
    
}
