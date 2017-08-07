package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.ArchDbConnectDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.dao.jpa.ParameterCondition;
import com.ai.am.domain.ArchDbConnect;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.am.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
@Service
@Transactional
public class ArchDbConnectSv extends BaseService {

	@Autowired
	private ArchDbConnectDao archDbConnectDao;
	
	public List<ArchDbConnect>findArchDbConnect(){
		return archDbConnectDao.findAll();
	}
	
/*	public List<ArchDbConnect>selectKey123(ArchDbConnectSelects condition){
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
		return archDbConnectDao.search(cons);
	}*/
	public List<ArchDbConnect>selectKey123(ArchDbConnectSelects condition){
		StringBuilder nativeSql = new StringBuilder(
				"select ar.*" +
						"from am_core_index am, arch_db_connect ar " +
				"where am.index_id = ar.index_id " );
//				"and am.index_group = :indexGroup" +
//				"and am.index_name = :indexName" +
//				"and to_date(ar.sett_month,'yyyymm') <= to_date(:endMonth, 'yyyymm')" +
//				"and to_date(ar.sett_month,'yyyymm') >= to_date(:startMonth, 'yyyymm') " +
//				"and ar.key_1 = :key1 " +
//				"and ar.key_2 = :key2 " +
//				"and ar.key_3 = :key3 ");
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
		return archDbConnectDao.searchByNativeSQL(nativeSql.toString(), params, ArchDbConnect.class);
	}
	
	public List<ArchDbConnect>justKey1(long indexId){
		return archDbConnectDao.findByIndexId(indexId);
	}
}
