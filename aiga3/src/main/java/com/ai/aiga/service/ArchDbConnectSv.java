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
import com.ai.aiga.dao.ArchDbConnectDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
@Service
@Transactional
public class ArchDbConnectSv extends BaseService {

	@Autowired
	private ArchDbConnectDao archDbConnectDao;
	
	public List<ArchDbConnect>findArchDbConnect(){
		return archDbConnectDao.findAll();
	}
	
	//find
	public List<ArchDbConnect> findAll(){
		return archDbConnectDao.findAll();
	}
	
	//add
	public void save(ArchDbConnect request){
		archDbConnectDao.save(request);
	}
	
	//delete
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archDbConnectDao.delete(indexId);
	}

	//update
	public void update(ArchDbConnect request){
		archDbConnectDao.save(request);
	}

	public Page<ArchDbConnect> findAllByPage(ArchDbConnect request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return archDbConnectDao.search(cons, pageable);
	}

	public Page<ArchDbConnect> queryByCondition(ArchDbConnect condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
/*    	if(condition.getIndexId()==0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.GT));
    	}
    	if(condition.getIndexId()!=0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
    	}*/
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
		return archDbConnectDao.search(cons, pageable);
	}
}
