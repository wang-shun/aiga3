package com.ai.aiga.service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchSvnDbcpDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchSvnDbcp;
import com.ai.aiga.domain.ArchSvnDbcpTwo;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
@Transactional
public class ArchSvnDbcpSv extends BaseService {
    @Autowired
    private ArchitectureStaticDataSv architectureStaticDataSv;

	@Autowired		
	private ArchSvnDbcpDao archSrvDbcpDao;
	//find all
	public List<ArchSvnDbcp> findAll(){
		return archSrvDbcpDao.findAll();
	}
	
	//add
    public void add(ArchSvnDbcp archSvnDbcp){
    	archSrvDbcpDao.save(archSvnDbcp);
    }
    
	//delete
	
	//query
    public Page<ArchSvnDbcp>queryByPage(ArchSvnDbcpSelects condition, int pageNumber,
			int pageSize){
		StringBuilder nativeSql = new StringBuilder(
				" select ar.center, ar.module, ar.db, ar.initial_size, ar.max_active, ar.max_idle, ar.min_idle, ar.max_wait, ar.insert_time, ar.is_change from arch_svn_dbcp ar where 1=1 "
				);

			List<ParameterCondition>params = new ArrayList<ParameterCondition>();
			if (StringUtils.isNotBlank(condition.getCenter())) {
				nativeSql.append("and ar.center = :center ");
				params.add(new ParameterCondition("center", condition.getCenter()));
			}
			if (StringUtils.isNotBlank(condition.getModule())) {
				nativeSql.append("and ar.module = :module ");
				params.add(new ParameterCondition("module", condition.getModule()));
			}   
			if (StringUtils.isNotBlank(condition.getDb())) {
				nativeSql.append("and ar.db = :db ");
				params.add(new ParameterCondition("db", condition.getDb()));
			}
			if (StringUtils.isNotBlank(condition.getIsChange())) {
				nativeSql.append("and ar.is_change = :isChange ");
				params.add(new ParameterCondition("isChange", condition.getIsChange()));
			}
			if (StringUtils.isNotBlank(condition.getInsertTime())) {
				nativeSql.append(" and substr(to_char(ar.insert_time,'yyyy-mm-dd'),0,10) = :insertTime ");
				params.add(new ParameterCondition("insertTime", condition.getInsertTime()));
			}
			if (pageNumber < 0) {
				pageNumber = 0;
			}
			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			return archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcp.class, pageable);
    }
    //QUERY BEFORE 7 DAY DATA 
    public Page<ArchSvnDbcp>queryBefore7DayByPage(ArchSvnDbcpSelects condition, int pageNumber,
    		int pageSize) throws Exception{
    	String nowday = condition.getInsertTime();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = format.parse(nowday);
		//get last month first day
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);  
        Date before7Day = calendar.getTime();
        String before7DayString = simpleDateFormat.format(before7Day);
        
    	StringBuilder nativeSql = new StringBuilder(
    			" select ar.center, ar.module, ar.db, ar.initial_size, ar.max_active, ar.max_idle, ar.min_idle, ar.max_wait, ar.insert_time, ar.is_change from arch_svn_dbcp ar where 1=1 "
    			);
    	
    	List<ParameterCondition>params = new ArrayList<ParameterCondition>();
    	if (StringUtils.isNotBlank(condition.getCenter())) {
    		nativeSql.append("and ar.center = :center ");
    		params.add(new ParameterCondition("center", condition.getCenter()));
    	}
    	if (StringUtils.isNotBlank(condition.getModule())) {
    		nativeSql.append("and ar.module = :module ");
    		params.add(new ParameterCondition("module", condition.getModule()));
    	}   
    	if (StringUtils.isNotBlank(condition.getDb())) {
    		nativeSql.append("and ar.db = :db ");
    		params.add(new ParameterCondition("db", condition.getDb()));
    	}
    	if (StringUtils.isNotBlank(condition.getIsChange())) {
    		nativeSql.append("and ar.is_change = :isChange ");
    		params.add(new ParameterCondition("isChange", condition.getIsChange()));
    	}
    	if (StringUtils.isNotBlank(condition.getInsertTime())) {
    		nativeSql.append(" and substr(to_char(ar.insert_time,'yyyy-mm-dd'),0,10) >= :before7DayString ");
    		params.add(new ParameterCondition("before7DayString", before7DayString));
    		nativeSql.append(" and substr(to_char(ar.insert_time,'yyyy-mm-dd'),0,10) <= :nowday ");
    		params.add(new ParameterCondition("nowday", nowday));
    	}
    	if (pageNumber < 0) {
    		pageNumber = 0;
    	}
    	if (pageSize <= 0) {
    		pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
    	}
    	nativeSql.append(" order by ar.insert_time ");
    	Pageable pageable = new PageRequest(pageNumber, pageSize);
    	return archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcp.class, pageable);
    }
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
               ArchSvnDbcpEvalutionOut archSvnDbcpEvalutionOut=new ArchSvnDbcpEvalutionOut();
               archSvnDbcpEvalutionOut.setDatabase(databaseName);
               archSvnDbcpEvalutionOut.setConnections(String.valueOf(connections));
               archSvnDbcpEvalutionOut.setMinIdle(String.valueOf(minIdle));
               archSvnDbcpEvalutionOut.setMaxIdle(String.valueOf(maxIdle));
               archSvnDbcpEvalutionOut.setMaxActive(String.valueOf(maxActive));
               archSvnDbcpEvalutionOuts.add(archSvnDbcpEvalutionOut);
           }
           return archSvnDbcpEvalutionOuts;
    }
	/**
	 *系统模块下拉框 distinctModule
	 *@param
	 *@return
	 *@author zhuchao
	 *@version v1.0.0
	 *@date 18-6-4 上午10:09
	 */
	public List<ArchSvnDbcp> systemModule(ArchSvnDbcpSelects condition){
		String center = condition.getCenter();
//    	List<ArchSvnDbcp>list = archSrvDbcpDao.findByCenter(center);
		List<ArchSvnDbcp> disinctDbList = new ArrayList<ArchSvnDbcp>();
		StringBuilder nativeSql = new StringBuilder(
				" select distinct ar.module from aiam.arch_svn_dbcp ar where 1=1 "
		);

		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getCenter())) {
			nativeSql.append(" and ar.center = :center ");
			params.add(new ParameterCondition("center", condition.getCenter()));
		}
		List<ArchSvnDbcp>list = archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcp.class);
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			ArchSvnDbcp base = (ArchSvnDbcp)iterator.next();
			disinctDbList.add(base);
		}
		return disinctDbList;
	}
    public List<ArchSvnDbcpTwo>queryByPageTwo(ArchSvnDbcpSelects condition){
    	String time="";
		if (condition.getInsertTime() != null) {
			time=condition.getInsertTime();
		}
		StringBuilder nativeSql = new StringBuilder(
				" select distinct(select count(distinct center) from aiam.arch_svn_dbcp t where  to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"') as collect,"+
				"(select count(center) from aiam.arch_svn_dbcp t where to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"')  as total," +
				"(select count(is_change) from aiam.arch_svn_dbcp t where is_change = 'Y' and to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"') as change from dual"
				);

			List<ParameterCondition>params = new ArrayList<ParameterCondition>();

			return archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcpTwo.class);
    }
    
    
	//update

    //distinct center
    public List<ArchSvnDbcpParams> distinctCenter(){
		StringBuilder nativeSql = new StringBuilder(
				" select distinct a.center as center from aiam.arch_svn_dbcp a ");
		List<Map>listMaps=new ArrayList<Map>();
		listMaps = archSrvDbcpDao.searchByNativeSQL(nativeSql.toString());
		List<ArchSvnDbcpParams>newList = new ArrayList<ArchSvnDbcpParams>();
		String codeType = "POOLCONFIGURATION_PRO_BUSINESS";
		List<ArchitectureStaticData> architectureStaticDatas=architectureStaticDataSv.findByCodeType(codeType);
		Map<String,String> architectureCenters=new HashMap<String,String>();
		if(null!=architectureStaticDatas){
			for(ArchitectureStaticData architectureStaticData:architectureStaticDatas){
				String codeValue=architectureStaticData.getCodeValue();
				String codeName=architectureStaticData.getCodeName();
				architectureCenters.put(codeValue,codeName);
			}
		}
		for(int i=0;i<listMaps.size();i++) {
			String centerString = String.valueOf(listMaps.get(i).get("center"));
			ArchSvnDbcpParams base = new ArchSvnDbcpParams();
			base.setCenter(centerString);
			String centerName = architectureCenters.get(centerString);
			if (null != centerName) {
				centerString = centerName;
			}
			base.setCenterName(centerString);
			newList.add(base);
		}
		return newList;
    }
    //distinct db
    public List<ArchSvnDbcp>selectDb(ArchSvnDbcpSelects condition){
//    	List<ArchSvnDbcp>list = archSrvDbcpDao.findByCenter(center);
    	List<ArchSvnDbcp> disinctDbList = new ArrayList<ArchSvnDbcp>();
    	StringBuilder nativeSql = new StringBuilder(
    			" select distinct ar.db from aiam.arch_svn_dbcp ar where 1=1 "
    			);
    	
    	List<ParameterCondition>params = new ArrayList<ParameterCondition>();
    	if (StringUtils.isNotBlank(condition.getCenter())) {
    		nativeSql.append(" and ar.center = :center ");
    		params.add(new ParameterCondition("center", condition.getCenter()));
    	}
        if(StringUtils.isNotBlank(condition.getModule())) {
            nativeSql.append(" and ar.module = :module");
            params.add(new ParameterCondition("module", condition.getModule()));
        }
    	List<ArchSvnDbcp>list = archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcp.class);
    	Iterator iterator = list.iterator();
    	while(iterator.hasNext()){
    		ArchSvnDbcp base = (ArchSvnDbcp)iterator.next();
            disinctDbList.add(base);
    	}
    	return disinctDbList;
    }
}
