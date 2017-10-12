package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchiTopListDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchiTopList;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiTopListParams;

@Service
@Transactional
public class ArchiTopListSv extends BaseService {

	@Autowired
	private ArchiTopListDao archiTopListDao;
	
	//find
	public List<ArchiTopList> findAll(){
		return archiTopListDao.findAll();
	}
	
	//add
	public void save(ArchiTopList request){
		archiTopListDao.save(request);
	}
	
	//delete
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archiTopListDao.delete(indexId);
	}

	//update
	public void update(ArchiTopList request){
		archiTopListDao.save(request);
	}

	public Page<ArchiTopList> findAllByPage(ArchiTopList request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return archiTopListDao.search(cons, pageable);
	}
	
	public Page<ArchiTopList>queryByCondition(int pageNumber, int pageSize, ArchiTopListParams condition){
		StringBuilder nativeSql = new StringBuilder(
			" select ar.* " +
				" from archi_top_list ar " +
				" where ar.index_id is not null " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (StringUtils.isNotBlank(condition.getIndexGroup())) {
			nativeSql.append("and ar.index_group = :indexGroup ");
			params.add(new ParameterCondition("indexGroup", condition.getIndexGroup()));
		}
		if (StringUtils.isNotBlank(condition.getIndexName())) {
			nativeSql.append("and ar.index_name = :indexName ");
			params.add(new ParameterCondition("indexName", condition.getIndexName()));
		}
		if (StringUtils.isNotBlank(condition.getStartMonth())) {
			nativeSql.append("and to_date(ar.sett_month,'yyyyMMdd') = to_date(:startMonth, 'yyyy-MM-dd') ");
			params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
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
		return archiTopListDao.searchByNativeSQL(nativeSql.toString(), params, ArchiTopList.class, pageable);
	}
	
}
