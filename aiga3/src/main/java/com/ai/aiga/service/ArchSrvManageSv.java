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
import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
@Service
@Transactional
public class ArchSrvManageSv extends BaseService {

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

}
