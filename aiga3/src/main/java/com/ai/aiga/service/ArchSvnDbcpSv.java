package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.ai.aiga.dao.ArchSvnDbcpDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.ArchSvnDbcp;
import com.ai.aiga.domain.ArchSvnDbcpTwo;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpSelects;
@Service
@Transactional
public class ArchSvnDbcpSv extends BaseService {
	
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
			}//  substr(to_char(ar.insert_time,'yyyy/mm/dd'),0,10) = '2018/01/11'
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
    
    public List<ArchSvnDbcpTwo>queryByPageTwo(ArchSvnDbcpSelects condition){
    	String time="";
		if (condition.getInsertTime() != null) {
			time=condition.getInsertTime();
		}
		StringBuilder nativeSql = new StringBuilder(
				" select distinct(select count(distinct center) from aiam.arch_svn_dbcp t where  to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"') as collect,"+
				"(select count(center) from aiam.arch_svn_dbcp t where to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"')  as total," +
				"(select count(is_change) from aiam.arch_svn_dbcp t where is_change = 'Y' and to_char(t.insert_time,'yyyy-MM-dd') = '"+ time +"') as change " +
				"from dual,aiam.arch_svn_dbcp t where 1=1"
				);

			List<ParameterCondition>params = new ArrayList<ParameterCondition>();
			if (StringUtils.isNotBlank(condition.getCenter())) {
				nativeSql.append("and t.center = :center ");
				params.add(new ParameterCondition("center", condition.getCenter()));
			}
			if (StringUtils.isNotBlank(condition.getModule())) {
				nativeSql.append("and t.module = :module ");
				params.add(new ParameterCondition("module", condition.getModule()));
			}   
			if (StringUtils.isNotBlank(condition.getDb())) {
				nativeSql.append("and t.db = :db ");
				params.add(new ParameterCondition("db", condition.getDb()));
			}
			if (StringUtils.isNotBlank(condition.getDb())) {
				nativeSql.append("and t.isChange = :isChange ");
				params.add(new ParameterCondition("isChange", condition.getIsChange()));
			}
			return archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcpTwo.class);
    }
    
    
	//update
    
    //distinct center
    public List<ArchSvnDbcp> distinctCenter(){
    	List<ArchSvnDbcp>list = archSrvDbcpDao.findAll();
    	List<ArchSvnDbcp>newList = new ArrayList<ArchSvnDbcp>(); 
        List<String>indexGrouplist = new ArrayList<String>(); 
        Iterator iter= list.iterator();//List接口实现了Iterable接口  
        while(iter.hasNext()){  
        	ArchSvnDbcp am=(ArchSvnDbcp)iter.next();  
         	if(!indexGrouplist.contains(am.getCenter().trim())){
         		indexGrouplist.add(am.getCenter().trim());
         		newList.add(am);
         	}
        }  
        return newList;
    }
    //distinct db
    public List<ArchSvnDbcp>selectDb(ArchSvnDbcpSelects condition){
    	String center = condition.getCenter();
    	List<ArchSvnDbcp>list = archSrvDbcpDao.findByCenter(center);
    	List<ArchSvnDbcp> disinctDbList = new ArrayList<ArchSvnDbcp>();
    	List<String>dbStrings = new ArrayList<String>();
    	Iterator iterator = list.iterator();
    	while(iterator.hasNext()){
    		int i=1;
    		ArchSvnDbcp base = (ArchSvnDbcp)iterator.next();
    		String temp = base.getDb();
    		System.out.println(i+"----------"+temp);
    		if(!dbStrings.contains(temp)){
    			dbStrings.add(temp);
    			disinctDbList.add(base);
    		}
    		i++;
    	}
    	return disinctDbList;
    }
}
