package com.ai.aiga.service.ArchIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.dao.ArchDbConnectDao;
import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchMonthIndex;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
@Service
@Transactional
public class ArchitectureIndexSv extends BaseService {
	@Autowired
	private AmCoreIndexDao amCoreIndexDao;
	@Autowired
	private ArchDbConnectDao archDbConnectDao;
	@Autowired
	private ArchSrvManageDao archSrvManageDao;
	
	public List<ArchDbConnect>findArchDbConnect(){
		return archDbConnectDao.findAll();
	}
	
	public Page<IndexConnect>listDbConnects(int pageNumber, int pageSize, AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
			" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value " +
				" from am_core_index am, arch_db_connect ar " +
				" where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup" +
//				"and am.index_name = :indexName" +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm')" +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}   //to_date(substr(ar.sett_month,0,6),'yyyyMM')
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') >= to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') <= to_date(:endMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archDbConnectDao.searchByNativeSQL(nativeSql.toString(), params, IndexConnect.class, pageable);
	}
	
	public Page<IndexConnect>listDbConnects2(int pageNumber, int pageSize, AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value " +
						" from am_core_index am, arch_db_connect ar " +
				" where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup" +
//				"and am.index_name = :indexName" +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm')" +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		if (condition.getIndexId()!=null){
			nativeSql.append("and am.index_id = :indexId ");
			params.add(new ParameterCondition("indexId", condition.getIndexId()));
		}
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}   //to_date(substr(ar.sett_month,0,6),'yyyyMM')
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') >= to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') <= to_date(:endMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archDbConnectDao.searchByNativeSQL(nativeSql.toString(), params, IndexConnect.class, pageable);
	}
	
	public List<ArchDbConnect>listDbConnects2(AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				"select ar.*" +
						"from am_core_index am, arch_db_connect ar " +
				"where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup" +
//				"and am.index_name = :indexName" +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm')" +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		if (condition.getIndexId()!=null){
			nativeSql.append("and am.index_id = :indexId ");
			params.add(new ParameterCondition("indexId", condition.getIndexId()));
		}
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName and am.index_id = ar.index_id ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') >= to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') <= to_date(:endMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		return archDbConnectDao.searchByNativeSQL(nativeSql.toString(), params, ArchDbConnect.class);
	}
	
	public Page<IndexConnect>listSrvManage(int pageNumber, int pageSize, AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
			" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value " +
				" from am_core_index am, arch_srv_manage ar " +
				" where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup " +
//				"and am.index_name = :indexName " +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm') " +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') >= to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') <= to_date(:endMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, IndexConnect.class, pageable);
	}
	
	public List<ArchSrvManage>listSrvManage2(AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				"select ar.* " +
						"from am_core_index am, arch_srv_manage ar " +
				"where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup " +
//				"and am.index_name = :indexName " +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm') " +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName and am.index_id = ar.index_id ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') >= to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') <= to_date(:endMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
	
	public Page<IndexConnect>listMonthIndex(int pageNumber, int pageSize, AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value " +
						" from am_core_index am, arch_month_index ar " +
				" where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup " +
//				"and am.index_name = :indexName " +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm') " +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(substr(ar.sett_month, 0, 6),'yyyyMM') >= to_date(:startMonth, 'yyyy-MM') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(substr(ar.sett_month, 0, 6),'yyyyMM') <= to_date(:endMonth, 'yyyy-MM') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, IndexConnect.class, pageable);
	}
	
	public List<ArchMonthIndex>listMonthIndex2(AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				"select ar.* " +
						"from am_core_index am, arch_month_index ar " +
				"where am.group_id = ar.group_id and am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup " +
//				"and am.index_name = :indexName " +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm') " +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		
		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and am.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and am.index_name = :indexName and am.index_id = ar.index_id ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(substr(ar.sett_month, 0, 6),'yyyyMM') >= to_date(:startMonth, 'yyyy-MM') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
		}
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and to_date(substr(ar.sett_month, 0, 6),'yyyyMM') <= to_date(:endMonth, 'yyyy-MM') ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
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
		return archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchMonthIndex.class);
	}
	
}
