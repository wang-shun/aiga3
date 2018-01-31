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
import com.ai.aiga.domain.ArchSvnDbcp;
import com.ai.aiga.service.base.BaseService;
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
    	List<Condition>cons = new ArrayList<Condition>();
    	if(StringUtils.isNoneBlank(condition.getCenter())){
    		cons.add(new Condition("center", "%".concat(condition.getCenter()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getModule())){
    		cons.add(new Condition("module", "%".concat(condition.getModule()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getDb())){
    		cons.add(new Condition("db", "%".concat(condition.getDb()).concat("%"), Condition.Type.LIKE));
    	}
    	if(condition.getInsertTime()!=null){
    		cons.add(new Condition("insertTime", condition.getInsertTime(), Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archSrvDbcpDao.search(cons, pageable);
    }
    
	//update
    
}
