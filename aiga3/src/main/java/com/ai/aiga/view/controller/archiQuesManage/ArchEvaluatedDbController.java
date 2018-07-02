package com.ai.aiga.view.controller.archiQuesManage;

import com.ai.aiga.service.ArchEvaluatedDbSv;
import com.ai.aiga.util.ExportWordUtil;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhuchao on 18-6-21.
 */
@Controller
@Api(value = "ArchEvaluatedDbController", description = "专项治理控制层")
public class ArchEvaluatedDbController {
    @Autowired
    private ArchEvaluatedDbSv archEvaluatedDbSv;
    @RequestMapping(path="/webservice/evaluateddb/manualDownload")
    public @ResponseBody void manualDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try{
            Map dataMap=new HashMap();
            file = ExportWordUtil.createWord(dataMap, "evaluateddbmanual.doc","evaluatedDbManualDownload");
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename="+new String("新接入业务数据库连接容量评估操作手册".getBytes(),"iso-8859-1")+".doc");
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
    @RequestMapping(path="/webservice/evaluateddb/evaluatedwordReport")
    public @ResponseBody void wordExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try{
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String tpsnumbers=request.getParameter("tpsnumbers");
            String timeType=request.getParameter("timetype");
            String dbs=request.getParameter("dbs");
            String type="";
            if("mintime".equals(timeType)){
                type="分";
            }else if("sectime".equals(timeType)){
                type="秒";
            }
            String deployednumbers=request.getParameter("deployednumbers");
            String serviceCalledTime=request.getParameter("serviceCalledTime");
            String personName= SessionMgrUtil.getUserInfo().getStaff().getName();
            Date date=new Date();
            //转换成时间格式24小时制
            SimpleDateFormat df_24=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate=df_24.format(date);
            ArchSvnDbcpEvalutionIn condition=new ArchSvnDbcpEvalutionIn();
            condition.setDbs(dbs);
            condition.setDeployednumbers(Long.valueOf(deployednumbers));
            condition.setServiceCalledTime(Long.valueOf(serviceCalledTime));
            condition.setTimetype(timeType);
            condition.setTpsnumbers(Long.valueOf(tpsnumbers));
            ArchSvnDbcpMarkedWordOut archSvnDbcpMarkedWordOut=archEvaluatedDbSv.getMarkedWord(condition);
            String attention="";
            if("".equals(archSvnDbcpMarkedWordOut.getMarkedWord().trim())){
                attention="无。";
            }else {
                attention=archSvnDbcpMarkedWordOut.getMarkedWord()+"。";
            }
            dataMap.put("tpsnumbers", tpsnumbers);
            dataMap.put("timetype", type);
            dataMap.put("serviceCalledTime", serviceCalledTime);
            dataMap.put("deployednumbers", deployednumbers);
            dataMap.put("evaluatedDate", currentDate);
            dataMap.put("person", personName);
            dataMap.put("attention", attention);
            List<Edb1In> list = new ArrayList<Edb1In>();
            List<Edb2In> list2 = new ArrayList<Edb2In>();
            List<ArchSvnDbcpEvalutionOut>  archSvnDbcpEvalutionOuts= archEvaluatedDbSv.getEvalution(condition);
            for (int i = 0; i < archSvnDbcpEvalutionOuts.size(); i++) {
                ArchSvnDbcpEvalutionOut archSvnDbcpEvalutionOut=archSvnDbcpEvalutionOuts.get(i);
                Edb1In evaluateddb11In = new Edb1In();
                String choose=archSvnDbcpEvalutionOut.getChoose();
                if(choose==null||"".equals(choose.trim())){
                    evaluateddb11In.setChoose("");
                }else if ("1".equals(choose.trim())){
                    evaluateddb11In.setChoose("是");
                }else if("0".equals(choose.trim())){
                    evaluateddb11In.setChoose("否");
                }
                evaluateddb11In.setName(archSvnDbcpEvalutionOut.getDatabase());
                evaluateddb11In.setCf(archSvnDbcpEvalutionOut.getConnectionFactor());
                list.add(evaluateddb11In);
                Edb2In evaluateddb22In = new Edb2In();
                evaluateddb22In.setMax(archSvnDbcpEvalutionOut.getMax());
                evaluateddb22In.setMin(archSvnDbcpEvalutionOut.getMin());
                evaluateddb22In.setFact(archSvnDbcpEvalutionOut.getFact());
                evaluateddb22In.setName(archSvnDbcpEvalutionOut.getDatabase());
                evaluateddb22In.setConns(archSvnDbcpEvalutionOut.getConnections());
                evaluateddb22In.setMaxActive(archSvnDbcpEvalutionOut.getMaxActive());
                evaluateddb22In.setMaxIdle(archSvnDbcpEvalutionOut.getMaxIdle());
                evaluateddb22In.setMinIdle(archSvnDbcpEvalutionOut.getMinIdle());
                evaluateddb22In.setAdvise(archSvnDbcpEvalutionOut.getAdvise());
                list2.add(evaluateddb22In);
            }
            dataMap.put("edb1List", list);
            dataMap.put("edb2List", list2);
            file = ExportWordUtil.createWord(dataMap, "evaluateddb.doc","evaluatedDb");
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename="+new String("新接入业务数据库连接容量评估报告".getBytes(),"iso-8859-1")+".doc");
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
