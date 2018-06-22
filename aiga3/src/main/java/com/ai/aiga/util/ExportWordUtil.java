package com.ai.aiga.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuchao on 18-6-21.
 */
public class ExportWordUtil {
    private static Configuration configuration = null;
    private static Map<String,Template> allTemplate = null;
    static{
        configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(ExportWordUtil.class, "/com/zhihua/templates");
        allTemplate = new HashMap<String,Template>();
        try{
            allTemplate.put("evaluatedDb", configuration.getTemplate("evaluateddb.ftl"));
        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static File createWord(Map<?,?> dataMap,String fileName, String type){
        File f = new File(fileName);
        Template t = allTemplate.get(type);
        try{
            Writer w = new OutputStreamWriter(new FileOutputStream(f),"utf-8");
            t.process(dataMap,w);
            w.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return f;
    }
}
