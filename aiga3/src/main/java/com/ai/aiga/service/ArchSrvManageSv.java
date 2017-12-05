package com.ai.aiga.service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.CacheCloudPlatformReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterCsfSrvReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterMessageQueueReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.FlowDispatchReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.TaskDispatchReport;
@Service
@Transactional
public class ArchSrvManageSv extends BaseService {

	public static final String CSFSRV_INDEXIDS = "3002001,3002002,3002003,3002004,3002005,3002006,3002007";
	public static final String TASKDISPATCH_INDEXIDS = "3003001,3003002,3003003,3003004,3003005,3003006";
	public static final String FLOWDISPATCH_INDEXIDS = "3004001,3004002,3004003,3004004,3004005,3004006";
	public static final String CACHECLOUD_INDEXIDS = "3005001,3005002,3005003,3005004,3005005";
	public static final String CENTERMQ_INDEXIDS = "3006001,3006002,3006003,3006004,3006005";
	
	@Autowired
	private ArchSrvManageDao archSrvManageDao;
	
	public List<ArchSrvManage>findArchSrvManages(){
		return archSrvManageDao.findAll();
	}
	
	//find
	public List<ArchSrvManage> findAll(){
		return archSrvManageDao.findAll();
	}
	
	//add
	public void save(ArchSrvManage request){
		archSrvManageDao.save(request);
	}
	
	//delete
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archSrvManageDao.delete(indexId);
	}

	//update
	public void update(ArchSrvManage request){
		archSrvManageDao.save(request);
	}

	public Page<ArchSrvManage> findAllByPage(ArchSrvManage request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return archSrvManageDao.search(cons, pageable);
	}

	public Page<ArchSrvManage> queryByCondition(ArchSrvManage condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(condition.getIndexId()==0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.GT));
    	}
    	if(condition.getIndexId()!=0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getSettMonth())){
    		cons.add(new Condition("settMonth", "%" + condition.getSettMonth() + "%", Condition.Type.EQ));
    	}
    	if(condition.getGroupId()!=null){
    		cons.add(new Condition("groupId", condition.getGroupId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey1())){
    		cons.add(new Condition("key1", "%" + condition.getKey1() + "%", Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey2())){
    		cons.add(new Condition("key2", "%" + condition.getKey2() + "%", Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey3())){
    		cons.add(new Condition("key3", "%" + condition.getKey3() + "%", Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archSrvManageDao.search(cons, pageable);
	}
	
    public List<ArchSrvManage>selectKey123(ArchSrvManageSelects condition){
        List<Condition>cons = new ArrayList<Condition>();
        if(condition.getIndexId() != null){
            cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
        }
        if(StringUtils.isNoneBlank(condition.getKey1())){
            cons.add(new Condition("key1", condition.getKey1(), Condition.Type.EQ));
        }
        if(StringUtils.isNoneBlank(condition.getKey2())){
            cons.add(new Condition("key2", condition.getKey2(), Condition.Type.EQ));
        }
        if(StringUtils.isNoneBlank(condition.getKey3())){
            cons.add(new Condition("key3", condition.getKey3(), Condition.Type.EQ));
        }
        return archSrvManageDao.search(cons);
    }
    
	public List<ArchSrvManage>selectKey321(ArchDbConnectSelects condition){
	    StringBuilder nativeSql = new StringBuilder(
	            "select ar.*" +
	                    "from am_core_index am, arch_srv_manage ar " +
	            "where am.index_id = ar.index_id " );
	//            "and am.index_group = :indexGroup" +
	//            "and am.index_name = :indexName" +
	//            "and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm')" +
	//            "and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
	//            "and ar.key_1 = :key1 " +
	//            "and ar.key_2 = :key2 " +
	//            "and ar.key_3 = :key3 ");
	    List<ParameterCondition>params = new ArrayList<ParameterCondition>();
	    
	    if (StringUtils.isNotBlank(condition.getIndexName())) {
	        nativeSql.append("and am.index_name = :indexName ");
	        params.add(new ParameterCondition("indexName", condition.getIndexName()));
	    }
	    if (condition.getIndexId()!=0) {
	    	nativeSql.append("and am.index_id = :indexId ");
	    	params.add(new ParameterCondition("indexId", condition.getIndexId()));
	    }
	    if (StringUtils.isNotBlank(condition.getKey1())) {
	        nativeSql.append("and ar.key_1 = :key1 ");
	        params.add(new ParameterCondition("key1", condition.getKey1()));
	    }
	    if (StringUtils.isNotBlank(condition.getKey2())) {
	        nativeSql.append("and ar.key_2 = :key2 ");
	        params.add(new ParameterCondition("key2", condition.getKey2()));
	    }
	    if (StringUtils.isNotBlank(condition.getKey3())) {
	        nativeSql.append("and ar.key_3 = :key3 ");
	        params.add(new ParameterCondition("key3", condition.getKey3()));
	    }
	    return archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
	}

	public List<CenterCsfSrvReport>csfsrv(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				"select * from (select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%日新增CSF服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%累计接入服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%活跃服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%中心日累计调用量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%CSF服务调用量环比变化%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%昨日CSF服务调用系统成功率%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%CSF服务调用成功率环比变化%' "+
						") " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" where sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" where sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<ArchSrvManage>inlist = new ArrayList<ArchSrvManage>(outlist);
		List<String>key1List = new ArrayList<String>();
		double[][] data =new double[10][7];
		int i=0;
		List<CenterCsfSrvReport>listcsf = new ArrayList<CenterCsfSrvReport>();
		Iterator<ArchSrvManage>outiter = outlist.iterator();
		while(outiter.hasNext()){
			ArchSrvManage outbase = outiter.next();
			if(!key1List.contains(outbase.getKey1())){
				String key1 = outbase.getKey1();
				key1List.add(key1);
				Iterator<ArchSrvManage>initer=inlist.iterator();
				while(initer.hasNext()){
					ArchSrvManage inbase = initer.next();
					if(inbase.getKey1().equals(key1)){
						for(int j=0;j<7;j++){
							if((inbase.getIndexId().longValue())%10==(j+1)){
								data[i][j]=Double.valueOf(inbase.getResultValue());
							}
						}	
					}
				}
				i++;
			}
		}
		for(int k=0;k<key1List.size();k++){
			CenterCsfSrvReport bean = new CenterCsfSrvReport();
			bean.setKey1(key1List.get(k));
			bean.setDayCsfSrvNum(String.valueOf(data[k][0]));
			bean.setTotalCsfNum(String.valueOf(data[k][1]));
			bean.setActiveCsfNum(String.valueOf(data[k][2]));
			bean.setCenterCsfNum(String.valueOf(data[k][3]));
			bean.setCsfSrvChainRatio(String.valueOf(data[k][4]));
			bean.setPredayCsfSuccessRate(String.valueOf(data[k][5]));
			bean.setCsfSuccessRateChainRatio(String.valueOf(data[k][6]));
			bean.setSettMonth(condition.getSettMonth());
			listcsf.add(bean);
		}
		return listcsf;
	}

	public List<CenterCsfSrvReport> newcsfsrv(PlatformOperateReportParams condition){
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						CSFSRV_INDEXIDS + " ) " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<CenterCsfSrvReport>listCsf = new ArrayList<CenterCsfSrvReport>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(CSFSRV_INDEXIDS);
		System.out.println("xName---------------"+x);
		System.out.println("yName---------------"+y);
		double[][] data = getData(x, y, outlist);
		//封装
		for(int k=0;k<xList.size();k++){
			CenterCsfSrvReport bean = new CenterCsfSrvReport();
			bean.setKey1(xList.get(k));
			bean.setDayCsfSrvNum(String.valueOf(data[k][0]));
			bean.setTotalCsfNum(String.valueOf(data[k][1]));
			bean.setActiveCsfNum(String.valueOf(data[k][2]));
			bean.setCenterCsfNum(String.valueOf(data[k][3]));
			bean.setCsfSrvChainRatio(String.valueOf(data[k][4]));
			bean.setPredayCsfSuccessRate(String.valueOf(data[k][5]));
			bean.setCsfSuccessRateChainRatio(String.valueOf(data[k][6]));
			bean.setSettMonth(condition.getSettMonth());
			listCsf.add(bean);
		}
		return listCsf;
	}
	
	public List<TaskDispatchReport> taskdispatch(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						TASKDISPATCH_INDEXIDS + " ) " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<TaskDispatchReport>listTask = new ArrayList<TaskDispatchReport>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(TASKDISPATCH_INDEXIDS);
		System.out.println("xName---------------"+x);
		System.out.println("yName---------------"+y);
		double[][] data = getData(x, y, outlist);
		
		for(int k=0;k<xList.size();k++){
			TaskDispatchReport bean = new TaskDispatchReport();
			bean.setKey1(xList.get(k));
			bean.setPredayAddTaskNum(String.valueOf(data[k][0]));
			bean.setResidentTaskNum(String.valueOf(data[k][1]));
			bean.setNonresidentTaskNum(String.valueOf(data[k][2]));
			bean.setBatchTaskNum(String.valueOf(data[k][3]));
			bean.setPredayTaskTriggerNum(String.valueOf(data[k][4]));
			bean.setChangeChainRatio(String.valueOf(data[k][5]));
			bean.setSettMonth(condition.getSettMonth());
			listTask.add(bean);
		}
		return listTask;
	}

	public List<FlowDispatchReport> flowdispatch(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						FLOWDISPATCH_INDEXIDS + " ) " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<FlowDispatchReport>listFlow = new ArrayList<FlowDispatchReport>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(FLOWDISPATCH_INDEXIDS);
		System.out.println("xName---------------"+x);
		System.out.println("yName---------------"+y);
		double[][] data = getData(x, y, outlist);
		//类型转换
		for(int k=0;k<xList.size();k++){
			FlowDispatchReport bean = new FlowDispatchReport();
			bean.setKey1(xList.get(k));
		    bean.setAddFlowConnectNum(String.valueOf(data[k][0]));
		    bean.setTotalFlowConnectNum(String.valueOf(data[k][1]));
		    bean.setPredayDispatchNum(String.valueOf(data[k][2]));
		    bean.setDispatchChainRatio(String.valueOf(data[k][3]));
		    bean.setDealAverageTime(String.valueOf(data[k][4]));
		    bean.setDealTimeChainRatio(String.valueOf(data[k][5]));
			bean.setSettMonth(condition.getSettMonth());
			listFlow.add(bean);
		}
		return listFlow;
	}

	public List<CacheCloudPlatformReport> cachecloud(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						CACHECLOUD_INDEXIDS + " ) " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<CacheCloudPlatformReport>listCache = new ArrayList<CacheCloudPlatformReport>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(CACHECLOUD_INDEXIDS);
		System.out.println("xName---------------"+x);
		System.out.println("yName---------------"+y);
		double[][] data = getData(x, y, outlist);
		//拼数据
		for(int k=0;k<xList.size();k++){
			CacheCloudPlatformReport bean = new CacheCloudPlatformReport();
			bean.setKey1(xList.get(k));
		    bean.setCacheBlockIsZero(String.valueOf(data[k][0]));
		    bean.setCacheBlockGtTenM(String.valueOf(data[k][1]));
		    bean.setIncreaseCacheBlockNum(String.valueOf(data[k][2]));
		    bean.setTotalCacheBlockNum(String.valueOf(data[k][3]));
		    bean.setChangeChainRatio(String.valueOf(data[k][4]));
			bean.setSettMonth(condition.getSettMonth());
			listCache.add(bean);
		}
		return listCache;
	}

	public List<CenterMessageQueueReport> centermq(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						CENTERMQ_INDEXIDS + " ) " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		//查询条件非空按settMonth查询
		if (StringUtils.isNotBlank(condition.getSettMonth())) {
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", condition.getSettMonth()));
		}else{//查询条件为空默认查询今天today
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd"); 
			String date = sDateFormat.format(new java.util.Date()); 
			nativeSql.append(" and sett_month = :settMonth ");
			params.add(new ParameterCondition("settMonth", date));
		}
		List<CenterMessageQueueReport>listMq = new ArrayList<CenterMessageQueueReport>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(CENTERMQ_INDEXIDS);
		System.out.println("xName---------------"+x);
		System.out.println("yName---------------"+y);
		double[][] data = getData(x, y, outlist);
		//封装
		for(int k=0;k<xList.size();k++){
			CenterMessageQueueReport bean = new CenterMessageQueueReport();
			bean.setKey1(xList.get(k));
		    bean.setPredayMqConsumeNum(String.valueOf(data[k][0]));
		    bean.setChangeNumChainRatio(String.valueOf(data[k][1]));
		    bean.setMessageConsumeSuccessRate(String.valueOf(data[k][2]));
		    bean.setSuccessRateChainRatio(String.valueOf(data[k][3]));
		    bean.setMessageCheckSameRate(String.valueOf(data[k][4]));
			bean.setSettMonth(condition.getSettMonth());
			listMq.add(bean);
		}
		return listMq;
	}
	
	//获取横轴维度数量
	public static List<String> getX (List<ArchSrvManage>list){
		List<String>keyList = new ArrayList<String>();
		Iterator<ArchSrvManage>iterator = list.iterator();
		while(iterator.hasNext()){
			ArchSrvManage bean = iterator.next();
			String xName = bean.getKey1();
			if(!keyList.contains(xName)){
				keyList.add(xName);
			}
		}
		return keyList;
	}
	//获取纵轴维度数量
	public static int getY(String string){
		String[] indexIds = string.split(",");
		return indexIds.length;
	}
	//数据转换
	public static double[][] getData(int x, int y, List<ArchSrvManage>list){
		double[][] data = new double[x][y];
		List<ArchSrvManage>outlist = list;
		List<ArchSrvManage>inlist = new ArrayList<ArchSrvManage>(outlist);
		List<String>key1List = new ArrayList<String>();
		int i=0;
		Iterator<ArchSrvManage>outiter = outlist.iterator();
		while(outiter.hasNext()){
			ArchSrvManage outbase = outiter.next();
			if(!key1List.contains(outbase.getKey1())){
				String key1 = outbase.getKey1();
				key1List.add(key1);
				Iterator<ArchSrvManage>initer=inlist.iterator();
				while(initer.hasNext()){
					ArchSrvManage inbase = initer.next();
					if(inbase.getKey1().equals(key1)){
						for(int j=0;j<data[i].length;j++){
							if((inbase.getIndexId().longValue())%10==(j+1)){
								data[i][j]=Double.valueOf(inbase.getResultValue());
							}
						}	
					}
				}
				i++;
			}
		}
		return data;
	}
}
