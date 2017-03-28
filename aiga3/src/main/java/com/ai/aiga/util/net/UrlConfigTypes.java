package com.ai.aiga.util.net;

import java.util.HashMap;
import java.util.Map;

/**
 * http服务接口地址存储类
 *
 * @author defaultekey
 * @date 2017/3/24
 */
public class UrlConfigTypes {

    private String ip;
    private String port;
    private String path;
    /**
     * 格式 （IP，端口号，路径）
     */
    public static final String SENDTASK="localhost，9999，/srf/arrange";

    private UrlConfigTypes(){}

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    /**
     * 以逗号分割并塞入属性中
     * @param args
     * @return
     */
    public static UrlConfigTypes getInstance(String args) {
        String[]params=args.split("，");
        Map<String, String> map = new HashMap<String, String>();
        UrlConfigTypes url=new UrlConfigTypes();
        if(params.length==3) {
            url.ip=params[0];
            url.port=params[1];
            url.path=params[2];
        }
        return url;
    }

}
