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
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
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
			" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value,ar.key_2,ar.key_3 " +
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
				" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value,ar.key_2,ar.key_3 " +
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
		}
//		if(condition.getIndexId()!=null){
//			long[] ids = condition.getIndexId();
//			String idx = new String();
//			for(int j=0;j<ids.length;j++){
//				idx += ids[j] + ",";
//			}
//			idx = idx.substring(0,idx.length()-1);
//			System.out.println("ids======"+ids);
//			System.out.println("idx======"+idx);
//			nativeSql.append(" and am.index_id in ( :idx ) ");
//			params.add(new ParameterCondition( "idx" , idx));
//		}
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
			params.add(new ParameterCondition("key3", condition.getKey3().substring(0, condition.getKey3().length()-1)));
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
		}
//		if(condition.getIndexId()!=null){
//			long[] ids = condition.getIndexId();
//			String idx = new String();
//			for(int j=0;j<ids.length;j++){
//				idx += ids[j] + ",";
//			}
//			idx = idx.substring(0,idx.length()-3);
//			System.out.println("ids======"+ids);
//			System.out.println("idx======"+idx);
//			nativeSql.append(" and am.index_id in ( :idx ) ");
//			params.add(new ParameterCondition( "idx" , idx));
//		}
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
			" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value,ar.key_2,ar.key_3 " +
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
		}
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
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
		return archSrvManageDao.searchByNativeSQL(nativeSql.toString(), params, ArchSrvManage.class);
	}
	
	public Page<IndexConnect>listMonthIndex(int pageNumber, int pageSize, AmCoreIndexParams condition){
		StringBuilder nativeSql = new StringBuilder(
				" select am.index_id,ar.sett_month,am.index_group,am.index_name,ar.result_value,ar.key_2,ar.key_3 " +
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
		}
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
		long[] ids = null;
		long id0,id1,id2,id3,id4,id5,id6,id7,id8,id9,id10;
		long id11,id12,id13,id14,id15,id16,id17,id18,id19,id20;
		if (condition.getIndexId()!=null){
			ids = condition.getIndexId();
			if(ids.length>20){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "选中的指标项目超过20个");
			}
			for(int i=0;i<ids.length;i++){
				if(i==0){
					id0 = ids[0];
					nativeSql.append(" and ( am.index_id = :id0 ");
					params.add(new ParameterCondition("id0", id0));
				}else if(i==1){
					id1 = ids[i];
					nativeSql.append(" or am.index_id = :id1 ");
					params.add(new ParameterCondition("id1", id1));
				}else if(i==2){
					id2 = ids[i];
					nativeSql.append(" or am.index_id = :id2 ");
					params.add(new ParameterCondition("id2", id2));
				}else if(i==3){
					id3 = ids[i];
					nativeSql.append(" or am.index_id = :id3 ");
					params.add(new ParameterCondition("id3", id3));
				}else if(i==4){
					id4 = ids[i];
					nativeSql.append(" or am.index_id = :id4 ");
					params.add(new ParameterCondition("id4", id4));
				}else if(i==5){
					id5 = ids[i];
					nativeSql.append(" or am.index_id = :id5 ");
					params.add(new ParameterCondition("id5", id5));
				}else if(i==6){
					id6 = ids[i];
					nativeSql.append(" or am.index_id = :id6 ");
					params.add(new ParameterCondition("id6", id6));
				}else if(i==7){
					id7 = ids[i];
					nativeSql.append(" or am.index_id = :id7 ");
					params.add(new ParameterCondition("id7", id7));
				}else if(i==8){
					id8 = ids[i];
					nativeSql.append(" or am.index_id = :id8 ");
					params.add(new ParameterCondition("id8", id8));
				}else if(i==9){
					id9 = ids[i];
					nativeSql.append(" or am.index_id = :id9 ");
					params.add(new ParameterCondition("id9", id9));
				}else if(i==10){
					id10 = ids[i];
					nativeSql.append(" or am.index_id = :id10 ");
					params.add(new ParameterCondition("id10", id10));
				}else if(i==11){
					id11 = ids[i];
					nativeSql.append(" or am.index_id = :id11 ");
					params.add(new ParameterCondition("id11", id11));
				}else if(i==12){
					id12 = ids[i];
					nativeSql.append(" or am.index_id = :id12 ");
					params.add(new ParameterCondition("id12", id12));
				}else if(i==13){
					id13 = ids[i];
					nativeSql.append(" or am.index_id = :id13 ");
					params.add(new ParameterCondition("id13", id13));
				}else if(i==14){
					id14 = ids[i];
					nativeSql.append(" or am.index_id = :id14 ");
					params.add(new ParameterCondition("id14", id14));
				}else if(i==15){
					id15 = ids[i];
					nativeSql.append(" or am.index_id = :id15 ");
					params.add(new ParameterCondition("id15", id15));
				}else if(i==16){
					id16 = ids[i];
					nativeSql.append(" or am.index_id = :id16 ");
					params.add(new ParameterCondition("id16", id16));
				}else if(i==17){
					id17 = ids[i];
					nativeSql.append(" or am.index_id = :id17 ");
					params.add(new ParameterCondition("id17", id17));
				}else if(i==18){
					id18 = ids[i];
					nativeSql.append(" or am.index_id = :id18 ");
					params.add(new ParameterCondition("id18", id18));
				}else if(i==19){
					id19 = ids[i];
					nativeSql.append(" or am.index_id = :id19 ");
					params.add(new ParameterCondition("id19", id19));
				}else if(i==20){
					id20 = ids[i];
					nativeSql.append(" or am.index_id = :id20 ");
					params.add(new ParameterCondition("id20", id20));
				}
			}
			nativeSql.append(" ) ");
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
