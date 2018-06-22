package com.ai.aiga.service;

import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhuchao on 18-6-21.
 */
@Service
@Transactional
public class ArchEvaluatedDbSv extends BaseService {
    @Autowired
    private ArchitectureStaticDataSv architectureStaticDataSv;
    /**
     *
     *@param
     *@return
     *@author zhuchao
     *@version v1.0.0
     *@date 18-6-20 下午3:38
     */
    public ArchSvnDbcpEvalutionDbOut getConversionFactor(ArchSvnDbcpConversionFactorIn condition)throws Exception{
        String codeType = "POOLCONFIGURATION_DB_EVALUTION";
        String center=condition.getCenter();
        String radioValue=condition.getRadiovalue();
        if(center==null||center.trim().equals("")||radioValue==null||radioValue.trim().equals("")){
            throw new Exception("传入参出错");
        }
        ArchSvnDbcpEvalutionDbOut archSvnDbcpEvalutionOut=new ArchSvnDbcpEvalutionDbOut();
        List<ArchitectureStaticData> architectureStaticDatas=architectureStaticDataSv.findByCodeTypeAndCodeValue(codeType,center);
        if(architectureStaticDatas!=null&&architectureStaticDatas.size()>=1){
            if("1".equals(radioValue)){
                archSvnDbcpEvalutionOut.setConversionFactor(String.valueOf(new BigDecimal(solveException(architectureStaticDatas.get(0).getExt1(),1.0)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            }else {
                archSvnDbcpEvalutionOut.setConversionFactor(String.valueOf(new BigDecimal(solveException(architectureStaticDatas.get(0).getExt2(),1.0)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
        }
        return archSvnDbcpEvalutionOut;
    }
    /**
     *
     *@param
     *@return
     *@author zhuchao
     *@version v1.0.0
     *@date 18-6-14 上午11:36
     */
    public List<ArchSvnDbcpEvalutionDbOut> getEvalDb(){
        String codeType = "POOLCONFIGURATION_DB_EVALUTION";
        List<ArchitectureStaticData> architectureStaticDatas=architectureStaticDataSv.findByCodeType(codeType);
        Map<String,Map<String,String>> architectureDbs=new HashMap<String,Map<String,String>>();
        if(null!=architectureStaticDatas&&architectureStaticDatas.size()>0){
            for(ArchitectureStaticData architectureStaticData:architectureStaticDatas){
                String codeValue=architectureStaticData.getCodeValue();
                String codeName=architectureStaticData.getCodeName();
                String ext1=architectureStaticData.getExt1();
                String ext2=architectureStaticData.getExt2();
                Map<String,String> map=new HashMap<String, String>();
                // map.put("ext1",ext1);
                // map.put("ext2",ext2);
                map.put("codeName",codeName);
                architectureDbs.put(codeValue,map);
            }
        }
        List<ArchSvnDbcpEvalutionDbOut> archSvnDbcpEvalutionOuts=new ArrayList<ArchSvnDbcpEvalutionDbOut>();
        Iterator<Map.Entry<String, Map<String,String>>> it = architectureDbs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String,String>> entry = it.next();
            String dbName=entry.getKey();
            Map<String,String> map=entry.getValue();
            String dbValue=map.get("codeName");
            // double ext2=solveException(map.get("ext2"),1.0);
            ArchSvnDbcpEvalutionDbOut archSvnDbcpEvalutionOut=new ArchSvnDbcpEvalutionDbOut();
            archSvnDbcpEvalutionOut.setDbName(dbName);
            archSvnDbcpEvalutionOut.setDbValue(dbValue);
            archSvnDbcpEvalutionOut.setConversionFactor("1.0");
            archSvnDbcpEvalutionOuts.add(archSvnDbcpEvalutionOut);
        }
        return archSvnDbcpEvalutionOuts;
    }
    /**
     *
     *@param
     *@return
     *@author zhuchao
     *@version v1.0.0
     *@date 18-6-15 上午9:27
     */
    public double solveException(String s1,double defalut){
        double value=0;
        try{
            value=Double.parseDouble(s1);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            value=defalut;
        }
        return value;
    }
    /**
     *
     *@param
     *@return
     *@author zhuchao
     *@version v1.0.0
     *@date 18-6-19 下午1:00
     */
    public ArchSvnDbcpMarkedWordOut getMarkedWord(ArchSvnDbcpEvalutionIn condition)throws Exception{
        Long  tpsnumbers=condition.getTpsnumbers();
        String timetype=condition.getTimetype();
        Long serviceCalledTime=condition.getServiceCalledTime();
        Long deployednumbers=condition.getDeployednumbers();
        String databases=condition.getDbs();
        ArchSvnDbcpMarkedWordOut archSvnDbcpMarkedWordOut=new ArchSvnDbcpMarkedWordOut();
        if(tpsnumbers==null||timetype==null||serviceCalledTime==null||deployednumbers==null){
            throw new Exception("传入参数出错");
        }
        if(databases==null||databases.length()==0){
            archSvnDbcpMarkedWordOut.setMarkedWord("");
            return archSvnDbcpMarkedWordOut;
        }
        double theoreticalSystemConcurrency=0;
        if("mintime".equals(timetype)){
            theoreticalSystemConcurrency=tpsnumbers/(60.0*1000.0)*serviceCalledTime;
        }else if("sectime".equals(timetype)){
            theoreticalSystemConcurrency=tpsnumbers/1000.0*serviceCalledTime;
        }else {
            throw  new Exception("系统错误!");
        }
        double sitcNumber=theoreticalSystemConcurrency/deployednumbers;
        double sitcMAX,sitcMin =0;
        sitcMAX=solveException(architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","SITCNMAX").get(0).getCodeName(),3.0);
        sitcMin=solveException(architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","SITCNMIN").get(0).getCodeName(),3.0);
        if(sitcNumber<sitcMin){
            String markedWord="根据输入TPS测算，单实例业务峰值并发数为"+new BigDecimal(sitcNumber).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+",低于"+sitcMin+"的建议并发下限，建议考虑缩减应用集群实例数";
            archSvnDbcpMarkedWordOut.setMarkedWord(markedWord);
        }else if(sitcNumber>sitcMAX){
            String markedWord="根据输入TPS测算，单实例业务峰值并发数为"+new BigDecimal(sitcNumber).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+",高于"+sitcMAX+"的建议并发上限，建议考虑增加应用集群实例数";
            archSvnDbcpMarkedWordOut.setMarkedWord(markedWord);
        }else {
            archSvnDbcpMarkedWordOut.setMarkedWord("");
        }
        return archSvnDbcpMarkedWordOut;
    }
    /**
     *
     *@param
     *@return
     *@author zhuchao
     *@version v1.0.0
     *@date 18-6-14 上午10:29
     */
    public List<ArchSvnDbcpEvalutionOut> getEvalution(ArchSvnDbcpEvalutionIn condition)throws Exception{
        Long  tpsnumbers=condition.getTpsnumbers();
        String timetype=condition.getTimetype();
        Long serviceCalledTime=condition.getServiceCalledTime();
        Long deployednumbers=condition.getDeployednumbers();
        String databases=condition.getDbs();
        if(tpsnumbers==null||timetype==null||serviceCalledTime==null||deployednumbers==null){
            throw new Exception("传入参数出错");
        }
        List<ArchSvnDbcpEvalutionOut> archSvnDbcpEvalutionOuts=new ArrayList<ArchSvnDbcpEvalutionOut>();
        if(databases==null||databases.length()==0){
            return archSvnDbcpEvalutionOuts;
        }
        double theoreticalSystemConcurrency=0;
        if("mintime".equals(timetype)){
            theoreticalSystemConcurrency=tpsnumbers/(60.0*1000.0)*serviceCalledTime;
        }else if("sectime".equals(timetype)){
            theoreticalSystemConcurrency=tpsnumbers/1000.0*serviceCalledTime;
        }else {
            throw  new Exception("系统错误!");
        }
        double sitcNumber=theoreticalSystemConcurrency/deployednumbers;
        double minIdelSEC,maxIdleSEC,maxActiveSEC,instanceSEC=0;
        String minIdleName=architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","minIdleSEC").get(0).getCodeName();
        minIdelSEC=solveException(minIdleName,1.0);
        String maxIdleName=architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","maxIdleSEC").get(0).getCodeName();
        maxIdleSEC=solveException(maxIdleName,1.5);
        String maxActiveName=architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","maxActiveSEC").get(0).getCodeName();
        maxActiveSEC=solveException(maxActiveName,2.0);
        String instanceSECNAME=architectureStaticDataSv.findByCodeTypeAndCodeValue("POOLCONFIGURATION_DBCP_SEC","maxActiveSEC").get(0).getCodeName();
        instanceSEC=solveException(instanceSECNAME,2.0);
        Map<String,Map<String,String>> databaseMap=new HashMap<String,Map<String,String>>();
        String[] databaseArray=databases.split(",");
        for(int i=0;i<databaseArray.length;i++,i++,i++){
            Map<String,String> map=new HashMap<String,String>();
            map.put("choose",databaseArray[i+1]);
            map.put("ext",databaseArray[i+2]);
            databaseMap.put(databaseArray[i],map);
        }
        String codeType = "POOLCONFIGURATION_DB_EVALUTION";
        List<ArchitectureStaticData> architectureStaticDatas=architectureStaticDataSv.findByCodeType(codeType);
        Map<String,Map<String,String>> architectureDbs=new HashMap<String,Map<String,String>>();
        if(null!=architectureStaticDatas&&architectureStaticDatas.size()>0){
            for(ArchitectureStaticData architectureStaticData:architectureStaticDatas){
                String codeValue=architectureStaticData.getCodeValue();
                String codeName=architectureStaticData.getCodeName();
                // String ext1=architectureStaticData.getExt1();
                // String ext2=architectureStaticData.getExt2();
                Map<String,String> map=new HashMap<String, String>();
                // map.put("ext1",ext1);
                //  map.put("ext2",ext2);
                map.put("codeName",codeName);
                architectureDbs.put(codeValue,map);
            }
        }
        Iterator<Map.Entry<String, Map<String,String>>> it = databaseMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String,String>> entry = it.next();
            String databaseValue=entry.getKey();
            Map<String,String> mapExt=entry.getValue();
            // int choose=Integer.parseInt(entry.getValue());
            Map<String,String> map=architectureDbs.get(databaseValue);
            String databaseName=map.get("codeName");
            //  double ext1=solveException(map.get("ext1"),1.0);
            // double ext2=solveException(map.get("ext2"),1.0);
            //double sec=(choose==1)?ext1:ext2;
            double sec=solveException(mapExt.get("ext"),1.0);
            int minIdle=(int)(sitcNumber*sec*minIdelSEC)+1;
            int maxIdle=(int)(sitcNumber*sec*maxIdleSEC)+1;
            int maxActive=(int)(sitcNumber*sec*maxActiveSEC)+1;
            int connections=(int)(minIdle*deployednumbers*instanceSEC);
            String choose=map.get("choose");
            ArchSvnDbcpEvalutionOut archSvnDbcpEvalutionOut=new ArchSvnDbcpEvalutionOut();
            archSvnDbcpEvalutionOut.setConnectionFactor(String.valueOf(new BigDecimal(sec).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            archSvnDbcpEvalutionOut.setDatabase(databaseName);
            archSvnDbcpEvalutionOut.setConnections(String.valueOf(connections));
            archSvnDbcpEvalutionOut.setMinIdle(String.valueOf(minIdle));
            archSvnDbcpEvalutionOut.setMaxIdle(String.valueOf(maxIdle));
            archSvnDbcpEvalutionOut.setMaxActive(String.valueOf(maxActive));
            archSvnDbcpEvalutionOut.setChoose(choose);
            archSvnDbcpEvalutionOuts.add(archSvnDbcpEvalutionOut);
        }
        return archSvnDbcpEvalutionOuts;
    }
}
