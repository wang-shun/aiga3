package com.ai.aiga.service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.AmCoreIndex;
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
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatcormOperateBase;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.TaskDispatchReport;
@Service
@Transactional
public class ArchSrvManageSv extends BaseService {

	public static final String CSFSRV_INDEXIDS = "4002001,4002002,4002003,4002004,4002005,4002006,4002007";
	public static final String TASKDISPATCH_INDEXIDS = "4003001,4003002,4003003,4003004,4003005,4003006";
	public static final String FLOWDISPATCH_INDEXIDS = "4004001,4004002,4004003,4004004,4004005,4004006";
	public static final String CACHECLOUD_INDEXIDS = "4005001,4005002,4005003,4005004,4005005";
	public static final String CENTERMQ_INDEXIDS = "4006001,4006002,4006003,4006004,4006005";
	
	@Autowired
	private ArchSrvManageDao archSrvManageDao;
	
	@Autowired
	private AmCoreIndexDao amCoreIndexDao;
	
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
	
	public String getIndexIds(String indexGroup){
		String indexIds = null;
		List<AmCoreIndex> list = amCoreIndexDao.selectByIndexGroup(indexGroup);
		for(int i=0;i<list.size();i++){
			AmCoreIndex bean = list.get(i);
			indexIds += String.valueOf(bean.getIndexId()) + ",";
		}
		return indexIds.substring(0, indexIds.length()-1);
	}

	public List<PlatcormOperateBase> report(PlatformOperateReportParams condition){
		String indexIds = getIndexIds(condition.getIndexGroup());
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in "
				+ "(" + indexIds + ")" );
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
		List<PlatcormOperateBase>listCsf = new ArrayList<PlatcormOperateBase>();
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
		List<String>xList = getX(outlist);
		int x = xList.size();
		int y = getY(indexIds);
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
			String xName = bean.getKey2();
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
	//数据转换(不带日期二维转换)
	public static double[][] getData(int x, int y, List<ArchSrvManage>list){
		double[][] data = new double[x][y];
		List<ArchSrvManage>outlist = list;
		List<ArchSrvManage>inlist = new ArrayList<ArchSrvManage>(outlist);
		List<String>key2List = new ArrayList<String>();
		int i=0;
		Iterator<ArchSrvManage>outiter = outlist.iterator();
		while(outiter.hasNext()){
			ArchSrvManage outbase = outiter.next();
			if(!key2List.contains(outbase.getKey2())){
				String key2 = outbase.getKey2();
				key2List.add(key2);
				Iterator<ArchSrvManage>initer=inlist.iterator();
				while(initer.hasNext()){
					ArchSrvManage inbase = initer.next();
					if(inbase.getKey2().equals(key2)){
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
	//数据转换(带日期三维转换)
	public static double[][][] getData(int x, int y, int z, List<ArchSrvManage>list){
		double[][][] data = new double[x][y][z];
		List<ArchSrvManage>outlist = list;
		List<ArchSrvManage>inlist = new ArrayList<ArchSrvManage>(outlist);
		List<ArchSrvManage>centerlist = new ArrayList<ArchSrvManage>(outlist);
		List<String>settMonthList = new ArrayList<String>();
		List<String>key1List = new ArrayList<String>();
		int i=0,j=0;
		Iterator<ArchSrvManage>iterator = outlist.iterator();
		while(iterator.hasNext()){
			ArchSrvManage outbase = iterator.next();
			String settMonth = outbase.getSettMonth();
			if(!settMonthList.contains(settMonth)){
				settMonthList.add(settMonth);
				Iterator<ArchSrvManage>iterator2 = inlist.iterator();
				while(iterator2.hasNext()){
					ArchSrvManage inbase = iterator2.next();
					String key1 = inbase.getKey1();
					if(!key1List.contains(key1)){
						key1List.add(key1);
						Iterator<ArchSrvManage>iterator3 = centerlist.iterator();
						while(iterator3.hasNext()){
							ArchSrvManage centerbase = iterator3.next();
							if(centerbase.getKey1().equals(key1)){
								for(int k=0;j<data[i][j].length;k++){
									if((centerbase.getIndexId().longValue())%10==(k+1)){
										data[i][j][k]=Double.valueOf(centerbase.getResultValue());
									}
								}	
							}
						}
						j++;
					}
				}
				i++;
			}
		}
		return data;
	}
	//获取时间settMonth维度数量
	public static List<String> getZ (List<ArchSrvManage>list){
		List<String>settMonthList = new ArrayList<String>();
		Iterator<ArchSrvManage>iterator = list.iterator();
		while(iterator.hasNext()){
			ArchSrvManage bean = iterator.next();
			String settMonth = bean.getSettMonth();
			if(!settMonthList.contains(settMonth)){
				settMonthList.add(settMonth);
			}
		}
		return settMonthList;
	}
	public Map<String,List> findPlatformOperate(PlatformOperateReportParams condition) {
		StringBuilder nativeSql = new StringBuilder(
				" select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_id in ( "+
						CSFSRV_INDEXIDS +","+ 
						TASKDISPATCH_INDEXIDS +","+ 
						FLOWDISPATCH_INDEXIDS +","+ 
						CACHECLOUD_INDEXIDS +","+ 
						CENTERMQ_INDEXIDS +")" );
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
		String nativeSql2 = nativeSql.toString()+" order by ar.index_id ";
		List<ArchSrvManage>outlist = archSrvManageDao.searchByNativeSQL(nativeSql2, params, ArchSrvManage.class);
		Iterator<ArchSrvManage>iterator = outlist.iterator();
		String key0 = CSFSRV_INDEXIDS.substring(0, 4);
		String key1 = TASKDISPATCH_INDEXIDS.substring(0, 4);
		String key2 = FLOWDISPATCH_INDEXIDS.substring(0, 4);
		String key3 = CACHECLOUD_INDEXIDS.substring(0, 4);
		String key4 = CENTERMQ_INDEXIDS.substring(0, 4);
		List<ArchSrvManage>list0=new ArrayList<ArchSrvManage>();
		List<ArchSrvManage>list1=new ArrayList<ArchSrvManage>();
		List<ArchSrvManage>list2=new ArrayList<ArchSrvManage>();
		List<ArchSrvManage>list3=new ArrayList<ArchSrvManage>();
		List<ArchSrvManage>list4=new ArrayList<ArchSrvManage>();
		while(iterator.hasNext()){
			ArchSrvManage archSrvManage = iterator.next();
			if(CSFSRV_INDEXIDS.contains(archSrvManage.getIndexId().toString())){
				list0.add(archSrvManage);
			}else if(TASKDISPATCH_INDEXIDS.contains(archSrvManage.getIndexId().toString())){
				list1.add(archSrvManage);
			}else if(FLOWDISPATCH_INDEXIDS.contains(archSrvManage.getIndexId().toString())){
				list2.add(archSrvManage);
			}else if(CACHECLOUD_INDEXIDS.contains(archSrvManage.getIndexId().toString())){
				list3.add(archSrvManage);
			}else if(CENTERMQ_INDEXIDS.contains(archSrvManage.getIndexId().toString())){
				list4.add(archSrvManage);
			}
		}
//		Collections.sort(list0, new ComparatorPlatformOperate());
//		Collections.sort(list1, new ComparatorPlatformOperate());
//		Collections.sort(list2, new ComparatorPlatformOperate());
//		Collections.sort(list3, new ComparatorPlatformOperate());
//		Collections.sort(list4, new ComparatorPlatformOperate());
		List<String>keyList0 = getX(list0);
		int x0 = keyList0.size();
		int y0 = getY(CSFSRV_INDEXIDS);
		double[][] data0 = getData(x0, y0, list0);
		List<String>keyList1 = getX(list1);
		int x1 = keyList1.size();
		int y1 = getY(TASKDISPATCH_INDEXIDS);
		double[][] data1 = getData(x1, y1, list1);
		List<String>keyList2 = getX(list2);
		int x2 = keyList2.size();
		int y2 = getY(FLOWDISPATCH_INDEXIDS);
		double[][] data2 = getData(x2, y2, list2);
		List<String>keyList3 = getX(list3);
		int x3 = keyList3.size();
		int y3 = getY(CACHECLOUD_INDEXIDS);
		double[][] data3 = getData(x3, y3, list3);
		List<String>keyList4 = getX(list4);
		int x4 = keyList4.size();
		int y4 = getY(CENTERMQ_INDEXIDS);
		double[][] data4 = getData(x4, y4, list4);
		
//		List<String>settMonthList0=getZ(list0);
//		int x0 = settMonthList0.size();
//		List<String>keyList0=getX(list0);
//		int y0 = keyList0.size();
//		int z0 = getY(CSFSRV_INDEXIDS);
//		double[][][] data0 = getData(x0, y0, z0, list0);
//		List<String>settMonthList1=getZ(list1);
//		int x1 = settMonthList1.size();		
//		List<String>keyList1=getX(list1);
//		int y1 = keyList1.size();
//		int z1 = getY(TASKDISPATCH_INDEXIDS);
//		double[][][] data1 = getData(x1, y1, z1, list1);
//		List<String>settMonthList2=getZ(list2);
//		int x2 = settMonthList2.size();		
//		List<String>keyList2=getX(list2);
//		int y2 = keyList2.size();
//		int z2 = getY(FLOWDISPATCH_INDEXIDS);
//		double[][][] data2 = getData(x2, y2, z2, list2);
//		List<String>settMonthList3=getZ(list3);
//		int x3 = settMonthList3.size();		
//		List<String>keyList3=getX(list3);
//		int y3 = keyList3.size();
//		int z3 = getY(CACHECLOUD_INDEXIDS);
//		double[][][] data3 = getData(x3, y3, z3, list3);
//		List<String>settMonthList4=getZ(list4);
//		int x4 = settMonthList4.size();		
//		List<String>keyList4=getX(list4);
//		int y4 = keyList4.size();
//		int z4 = getY(CENTERMQ_INDEXIDS);
//		double[][][] data4 = getData(x4, y4, z4, list4);
		//封装
		List<CenterCsfSrvReport>listCsf = new ArrayList<CenterCsfSrvReport>();
		List<TaskDispatchReport>listTask = new ArrayList<TaskDispatchReport>();
		List<FlowDispatchReport>listFlow = new ArrayList<FlowDispatchReport>();
		List<CacheCloudPlatformReport>listCache = new ArrayList<CacheCloudPlatformReport>();
		List<CenterMessageQueueReport>listMq = new ArrayList<CenterMessageQueueReport>();
		//封装
		for(int k=0;k<keyList0.size();k++){
			CenterCsfSrvReport bean = new CenterCsfSrvReport();
			bean.setKey1(keyList0.get(k));
			bean.setDayCsfSrvNum(String.valueOf(data0[k][0]));
			bean.setTotalCsfNum(String.valueOf(data0[k][1]));
			bean.setActiveCsfNum(String.valueOf(data0[k][2]));
			bean.setCenterCsfNum(String.valueOf(data0[k][3]));
			bean.setCsfSrvChainRatio(String.valueOf(data0[k][4]));
			bean.setPredayCsfSuccessRate(String.valueOf(data0[k][5]));
			bean.setCsfSuccessRateChainRatio(String.valueOf(data0[k][6]));
			bean.setSettMonth(condition.getSettMonth());
			listCsf.add(bean);
		}
		for(int k=0;k<keyList1.size();k++){
			TaskDispatchReport bean = new TaskDispatchReport();
			bean.setKey1(keyList1.get(k));
			bean.setPredayAddTaskNum(String.valueOf(data1[k][0]));
			bean.setResidentTaskNum(String.valueOf(data1[k][1]));
			bean.setNonresidentTaskNum(String.valueOf(data1[k][2]));
			bean.setBatchTaskNum(String.valueOf(data1[k][3]));
			bean.setPredayTaskTriggerNum(String.valueOf(data1[k][4]));
			bean.setChangeChainRatio(String.valueOf(data1[k][5]));
			bean.setSettMonth(condition.getSettMonth());
			listTask.add(bean);
		}
		for(int k=0;k<keyList2.size();k++){
			FlowDispatchReport bean = new FlowDispatchReport();
			bean.setKey1(keyList2.get(k));
		    bean.setAddFlowConnectNum(String.valueOf(data2[k][0]));
		    bean.setTotalFlowConnectNum(String.valueOf(data2[k][1]));
		    bean.setPredayDispatchNum(String.valueOf(data2[k][2]));
		    bean.setDispatchChainRatio(String.valueOf(data2[k][3]));
		    bean.setDealAverageTime(String.valueOf(data2[k][4]));
		    bean.setDealTimeChainRatio(String.valueOf(data2[k][5]));
			bean.setSettMonth(condition.getSettMonth());
			listFlow.add(bean);
		}
		for(int k=0;k<keyList3.size();k++){
			CacheCloudPlatformReport bean = new CacheCloudPlatformReport();
			bean.setKey1(keyList3.get(k));
		    bean.setCacheBlockIsZero(String.valueOf(data3[k][0]));
		    bean.setCacheBlockGtTenM(String.valueOf(data3[k][1]));
		    bean.setIncreaseCacheBlockNum(String.valueOf(data3[k][2]));
		    bean.setTotalCacheBlockNum(String.valueOf(data3[k][3]));
		    bean.setChangeChainRatio(String.valueOf(data3[k][4]));
			bean.setSettMonth(condition.getSettMonth());
			listCache.add(bean);
		}
		for(int k=0;k<keyList4.size();k++){
			CenterMessageQueueReport bean = new CenterMessageQueueReport();
			bean.setKey1(keyList4.get(k));
		    bean.setPredayMqConsumeNum(String.valueOf(data4[k][0]));
		    bean.setChangeNumChainRatio(String.valueOf(data4[k][1]));
		    bean.setMessageConsumeSuccessRate(String.valueOf(data4[k][2]));
		    bean.setSuccessRateChainRatio(String.valueOf(data4[k][3]));
		    bean.setMessageCheckSameRate(String.valueOf(data4[k][4]));
			bean.setSettMonth(condition.getSettMonth());
			listMq.add(bean);
		}
//		for(int a=0;a<x0;a++){
//			for(int b=0;b<y0;b++){
//				CenterCsfSrvReport bean = new CenterCsfSrvReport();
//				bean.setKey1(keyList0.get(b));
//				bean.setDayCsfSrvNum(String.valueOf(data0[a][b][0]));
//				bean.setTotalCsfNum(String.valueOf(data0[a][b][1]));
//				bean.setActiveCsfNum(String.valueOf(data0[a][b][2]));
//				bean.setCenterCsfNum(String.valueOf(data0[a][b][3]));
//				bean.setCsfSrvChainRatio(String.valueOf(data0[a][b][4]));
//				bean.setPredayCsfSuccessRate(String.valueOf(data0[a][b][5]));
//				bean.setCsfSuccessRateChainRatio(String.valueOf(data0[a][b][6]));
//				bean.setSettMonth(settMonthList0.get(a));
//				listCsf.add(bean);
//			}
//		}
//		for(int a=0;a<x1;a++){
//			for(int b=0;b<y1;b++){
//				TaskDispatchReport bean = new TaskDispatchReport();
//				bean.setKey1(keyList1.get(b));
//				bean.setPredayAddTaskNum(String.valueOf(data1[a][b][0]));
//				bean.setResidentTaskNum(String.valueOf(data1[a][b][1]));
//				bean.setNonresidentTaskNum(String.valueOf(data1[a][b][2]));
//				bean.setBatchTaskNum(String.valueOf(data1[a][b][3]));
//				bean.setPredayTaskTriggerNum(String.valueOf(data1[a][b][4]));
//				bean.setChangeChainRatio(String.valueOf(data1[a][b][5]));
//				bean.setSettMonth(settMonthList1.get(a));
//				listTask.add(bean);
//			}
//		}
//		for(int a=0;a<x2;a++){
//			for(int b=0;b<y2;b++){
//				FlowDispatchReport bean = new FlowDispatchReport();
//				bean.setKey1(keyList2.get(b));
//			    bean.setAddFlowConnectNum(String.valueOf(data2[a][b][0]));
//			    bean.setTotalFlowConnectNum(String.valueOf(data2[a][b][1]));
//			    bean.setPredayDispatchNum(String.valueOf(data2[a][b][2]));
//			    bean.setDispatchChainRatio(String.valueOf(data2[a][b][3]));
//			    bean.setDealAverageTime(String.valueOf(data2[a][b][4]));
//			    bean.setDealTimeChainRatio(String.valueOf(data2[a][b][5]));
//				bean.setSettMonth(settMonthList2.get(a));
//				listFlow.add(bean);
//			}
//		}
//		for(int a=0;a<x3;a++){
//			for(int b=0;b<y3;b++){
//				CacheCloudPlatformReport bean = new CacheCloudPlatformReport();
//				bean.setKey1(keyList3.get(b));
//			    bean.setCacheBlockIsZero(String.valueOf(data3[a][b][0]));
//			    bean.setCacheBlockGtTenM(String.valueOf(data3[a][b][1]));
//			    bean.setIncreaseCacheBlockNum(String.valueOf(data3[a][b][2]));
//			    bean.setTotalCacheBlockNum(String.valueOf(data3[a][b][3]));
//			    bean.setChangeChainRatio(String.valueOf(data3[a][b][4]));
//				bean.setSettMonth(settMonthList3.get(a));
//				listCache.add(bean);
//			}
//		}
//		for(int a=0;a<x4;a++){
//			for(int b=0;b<y4;b++){
//				CenterMessageQueueReport bean = new CenterMessageQueueReport();
//				bean.setKey1(keyList4.get(b));
//				bean.setPredayMqConsumeNum(String.valueOf(data4[a][b][0]));
//				bean.setChangeNumChainRatio(String.valueOf(data4[a][b][1]));
//				bean.setMessageConsumeSuccessRate(String.valueOf(data4[a][b][2]));
//				bean.setSuccessRateChainRatio(String.valueOf(data4[a][b][3]));
//				bean.setMessageCheckSameRate(String.valueOf(data4[a][b][4]));
//				bean.setSettMonth(settMonthList0.get(a));
//				listMq.add(bean);
//			}
//		}
		Map<String,List>map = new HashMap<String,List>();
		map.put(key0, listCsf);
		map.put(key1, listTask);
		map.put(key2, listFlow);
		map.put(key3, listCache);
		map.put(key4, listMq);
		return map;
	}
}

class ComparatorPlatformOperate implements Comparator<Object>{             
	 public int compare(Object arg0, Object arg1) {             
		 ArchSrvManage bean0=(ArchSrvManage)arg0;             
		 ArchSrvManage bean1=(ArchSrvManage)arg1;             
		 //首先比较indexId，如果indexId相同，则比较settMonth             
		 int flag=bean0.getIndexId().compareTo(bean1.getIndexId());             
		 if(flag==0){             
			 return bean0.getSettMonth().compareTo(bean1.getSettMonth());             
		 }else{             
			 return flag;             
		 }               
	 }             
} 