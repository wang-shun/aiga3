package com.ai.aiga.util.net;

import java.util.HashMap;
import java.util.Map;

/**
 * http服务接口地址存储类
 *
 * @author defaultekey
 * @date 2017/3/24
 */
public abstract class UrlConfigTypes {

    /**
     * 格式 （IP，端口号，路径）
     */
    public static final String SENDTASK="localhost，9999，/srf/arrange";

    /**
     * 以逗号分割并塞入map集合
     * @param args
     * @return
     */
    public static Map convertUrl(String args) {
        String[]params=args.split(args);
        Map<String, String> map = new HashMap<String, String>();
        if(params.length==3) {
            map.put("ip", params[0]);
            map.put("port", params[1]);
            map.put("path", params[2]);
        }
        return map;
    }

}
