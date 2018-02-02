package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchSvnDbcpDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchSvnDbcp;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpSelects;
@Service
@Transactional
public class ArchSvnDbcpSv extends BaseService {
			
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
				" select * from arch_svn_dbcp ar where 1=1 "
				);
//					"and ar.center = :center" +
//					"and ar.module = :module" +
//					"and ar.db = :db " +
//					"and to_date(ar.insert_time,'yyyymm') == to_date(:insert_time, 'yyyymm')" 
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
			if (StringUtils.isNotBlank(condition.getInsertTime())) {
				nativeSql.append("and to_date(ar.insert_time,'yyyyMMdd') >= to_date(:insertTime, 'yyyy-MM-dd') ");
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
    
	//update
    
}
