package com.ai.aiga.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
    public List<ArchSvnDbcp> distinctCenter(){
		StringBuilder nativeSql = new StringBuilder(
				" select distinct a.center as center from aiam.arch_svn_dbcp a ");
		List<Map>listMaps=new ArrayList<Map>();
		listMaps = archSrvDbcpDao.searchByNativeSQL(nativeSql.toString());
		List<String>listCenter = new ArrayList<String>(); 
		List<ArchSvnDbcp>newList = new ArrayList<ArchSvnDbcp>(); 
		for(int i=0;i<listMaps.size();i++){
			String centerString = String.valueOf(listMaps.get(i).get("center"));
			listCenter.add(centerString);
			ArchSvnDbcp base = new ArchSvnDbcp();
			base.setCenter(centerString);
			newList.add(base);
		}
//    	List<ArchSvnDbcp>list = archSrvDbcpDao.findAll();
//    	List<ArchSvnDbcp>newList = new ArrayList<ArchSvnDbcp>(); 
//        List<String>indexGrouplist = new ArrayList<String>(); 
//        Iterator iter= list.iterator();//List接口实现了Iterable接口  
//        while(iter.hasNext()){  
//        	ArchSvnDbcp am=(ArchSvnDbcp)iter.next();  
//         	if(!indexGrouplist.contains(am.getCenter().trim())){
//         		indexGrouplist.add(am.getCenter().trim());
//         		newList.add(am);
//         	}
//        }  
//        return newList;
        return newList;
    }
    //distinct db
    public List<ArchSvnDbcp>selectDb(ArchSvnDbcpSelects condition){
    	String center = condition.getCenter();
//    	List<ArchSvnDbcp>list = archSrvDbcpDao.findByCenter(center);
    	List<ArchSvnDbcp> disinctDbList = new ArrayList<ArchSvnDbcp>();
    	List<String>dbStrings = new ArrayList<String>();
    	StringBuilder nativeSql = new StringBuilder(
    			" select ar.* from aiam.arch_svn_dbcp ar where 1=1 "
    			);
    	
    	List<ParameterCondition>params = new ArrayList<ParameterCondition>();
    	if (StringUtils.isNotBlank(condition.getCenter())) {
    		nativeSql.append(" and ar.center = :center ");
    		params.add(new ParameterCondition("center", condition.getCenter()));
    	}
    	List<ArchSvnDbcp>list = archSrvDbcpDao.searchByNativeSQL(nativeSql.toString(), params, ArchSvnDbcp.class);
    	int i=1;
    	Iterator iterator = list.iterator();
    	while(iterator.hasNext()){
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
