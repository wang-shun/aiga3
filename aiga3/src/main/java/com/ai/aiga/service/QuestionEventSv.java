package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.QuestionEventDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionEventRequest;

@Service
@Transactional
public class QuestionEventSv extends BaseService {

	@Autowired
	private QuestionEventDao questionEventDao;
	
	//find
	public List<QuestionEvent> findAll(){
		return questionEventDao.findAll();
	}
	
	//add
	public void save(QuestionEvent request){
		questionEventDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		questionEventDao.delete(id);
	}

	//update
	public void update(QuestionEvent request){
		questionEventDao.save(request);
	}

	public Page<QuestionEvent> findAllByPage(QuestionEvent request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return questionEventDao.search(cons, pageable);
	}

	public Page<QuestionEvent> queryByCondition(QuestionEvent condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(condition.getId()==0){
    		cons.add(new Condition("id", condition.getId(), Condition.Type.GT));
    	}
    	if(condition.getId()!=0){
    		cons.add(new Condition("id", condition.getId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getName())){
    		cons.add(new Condition("name", "%" + condition.getName() + "%", Condition.Type.LIKE));
    	}
/*    	if(StringUtils.isNoneBlank(String.valueOf(condition.getStartTime()))){
    		cons.add(new Condition("startTime", condition.getStartTime(), Condition.Type.GT));
    	}
    	if(StringUtils.isNoneBlank(String.valueOf(condition.getEndTime()))){
    		cons.add(new Condition("endTime", condition.getEndTime(), Condition.Type.LT));
    	}*/
/*		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(StringUtils.isNoneBlank(String.valueOf(condition.getStartTime()))){
		  String  dateFir = condition.getStartTime()+" 00:00:00";
		  Date beginDate = format.parse(dateFir);	
		  cons.add(new Condition("applyTime", beginDate, Condition.Type.GT));
		}
		if(StringUtils.isNoneBlank(String.valueOf(condition.getEndTime()))){
		  String dateSec = condition.getEndTime()+" 23:59:59";
		  Date endDate = format.parse(dateSec);	
		  cons.add(new Condition("applyTime", endDate, Condition.Type.LT));
		}*/
    	if(StringUtils.isNoneBlank(condition.getState())){
    		cons.add(new Condition("state", condition.getState(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getExt1())){
    		cons.add(new Condition("ext1", "%" + condition.getExt1() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getExt2())){
    		cons.add(new Condition("ext2", "%" + condition.getExt2() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getExt3())){
    		cons.add(new Condition("ext3", "%" + condition.getExt3() + "%", Condition.Type.LIKE));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return questionEventDao.search(cons, pageable);
	}
}
