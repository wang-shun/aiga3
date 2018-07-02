package test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuchao on 18-6-21.
 */
public class freemaker {
    @Test
    public void exportWord() throws Exception{
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("tpsnumbers", "1000");
        dataMap.put("timetype", "分");
        dataMap.put("serviceCalledTime","1000");
        dataMap.put("deployednumbers","1000");
        dataMap.put("evaluatedDate","2018-12-11 11:11:11");
        dataMap.put("person","admin");
        dataMap.put("attention","实例。");
        List<Edb1In> list = new ArrayList<Edb1In>();
        for(int i=0;i<10;i++){
            Edb1In evaluateddb11In = new Edb1In();
            evaluateddb11In.setName("db1");
            evaluateddb11In.setChoose("是");
            evaluateddb11In.setCf("0.6");
            list.add(evaluateddb11In);
        }
        dataMap.put("edb1List", list);
        List<Edb2In> list2 = new ArrayList<Edb2In>();
        for(int i=0;i<10;i++){
            Edb2In evaluateddb22In = new Edb2In();
            evaluateddb22In.setName("db1");
            evaluateddb22In.setConns("1000");
            evaluateddb22In.setMaxActive("10");
            evaluateddb22In.setMaxIdle("10");
            evaluateddb22In.setMinIdle("10");
            list2.add(evaluateddb22In);
        }
        dataMap.put("edb2List", list2);
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("/home/zhuchao/word"));
        File outFile = new File("/home/zhuchao/word/test.doc");

        Template t =  configuration.getTemplate("evaluateddbmanual1.ftl","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        t.process(dataMap, out);
        out.close();
    }
}
