package com.ai.aiga.view.controller.archiQuesManage;

import com.ai.aiga.service.ArchEvaluatedDbSv;
import com.ai.aiga.util.ExportWordUtil;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpConversionFactorIn;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpEvalutionIn;
import com.ai.aiga.view.controller.archiQuesManage.dto.Edb1In;
import com.ai.aiga.view.controller.archiQuesManage.dto.Edb2In;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuchao on 18-6-21.
 */
@Controller
@Api(value = "ArchEvaluatedDbController", description = "专项治理控制层")
public class ArchEvaluatedDbController {
    @Autowired
    private ArchEvaluatedDbSv archEvaluatedDbSv;
    @RequestMapping(path="/webservice/evaluateddb/evaluatedwordReport")
    public @ResponseBody void wordExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try{
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("tpsnumbers", "1000");
            dataMap.put("timetype", "分");
            dataMap.put("serviceCalledTime", "1000");
            dataMap.put("deployednumbers", "1000");
            dataMap.put("evaluatedDate", "2018-12-11 11:11:11");
            dataMap.put("person", "admin");
            dataMap.put("attention", "实例。");
            List<Edb1In> list = new ArrayList<Edb1In>();
            for (int i = 0; i < 10; i++) {
                Edb1In evaluateddb11In = new Edb1In();
                evaluateddb11In.setName("db1");
                evaluateddb11In.setChoose("是");
                evaluateddb11In.setCf("0.6");
                list.add(evaluateddb11In);
            }
            dataMap.put("edb1List", list);
            List<Edb2In> list2 = new ArrayList<Edb2In>();
            for (int i = 0; i < 10; i++) {
                Edb2In evaluateddb22In = new Edb2In();
                evaluateddb22In.setName("db1");
                evaluateddb22In.setConns("1000");
                evaluateddb22In.setMaxActive("10");
                evaluateddb22In.setMaxIdle("10");
                evaluateddb22In.setMinIdle("10");
                list2.add(evaluateddb22In);
            }
            dataMap.put("edb2List", list2);
            file = ExportWordUtil.createWord(dataMap, "resume.doc","evaluatedDb");
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename=resume.doc");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            if(fin != null){
                fin.close();
            }
            if(out != null){
                out.close();
            }
            if(file != null) {
                file.delete(); // 删除临时文件
            }
        }
    }
    @RequestMapping(path="webservice/evaluateddb/getConversionFactor")
    public @ResponseBody JsonBean getConversionFactor(ArchSvnDbcpConversionFactorIn condition)throws Exception{
        JsonBean bean = new JsonBean();
        bean.setData(archEvaluatedDbSv.getConversionFactor(condition));
        return bean;
    }
    @RequestMapping(path="webservice/evaluateddb/getEvalDb")
    public @ResponseBody
    JsonBean getEvalDb(){
        JsonBean bean = new JsonBean();
        bean.setData(archEvaluatedDbSv.getEvalDb());
        return bean;
    }
    @RequestMapping(path="/webservice/evaluateddb/getMarkedWord")
    public @ResponseBody JsonBean getMarkedWord(ArchSvnDbcpEvalutionIn condition)throws Exception{
        JsonBean bean = new JsonBean();
        bean.setData(archEvaluatedDbSv.getMarkedWord(condition));
        return bean;
    }
    @RequestMapping(path="/webservice/evaluateddb/getEvalution")
    public @ResponseBody JsonBean getEvalution(ArchSvnDbcpEvalutionIn condition)throws Exception{
        JsonBean bean = new JsonBean();
        bean.setData(archEvaluatedDbSv.getEvalution(condition));
        return bean;
    }
}
