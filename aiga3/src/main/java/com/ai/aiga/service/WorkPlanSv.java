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
import com.ai.aiga.domain.WorkPlan;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.QuestionEventDao;
import com.ai.aiga.dao.WorkPlanDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionEventRequest;

@Service
@Transactional
public class WorkPlanSv extends BaseService {

	@Autowired
	private WorkPlanDao workPlanDao;
	
	//find
	public List<WorkPlan> findAll(){
		return workPlanDao.findAll();
	}
	
	//add
	public void save(WorkPlan request){
		workPlanDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		workPlanDao.delete(id);
	}

	//update
	public void update(WorkPlan request){
		workPlanDao.save(request);
	}

	public Page<WorkPlan> findAllByPage(WorkPlan request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return workPlanDao.search(cons, pageable);
	}

	public Page<WorkPlan> queryByCondition(WorkPlan condition, int pageNumber,
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
    	   	

    	if(StringUtils.isNoneBlank(condition.getMatters())){
    		cons.add(new Condition("matters", "%" + condition.getMatters() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getClassification())){
    		cons.add(new Condition("classification", "%" + condition.getClassification() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getJobcontent())){
    		cons.add(new Condition("jobcontent", "%" + condition.getJobcontent() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getCompletion())){
    		cons.add(new Condition("completion", condition.getCompletion(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getProjectcompletion())){
    		cons.add(new Condition("projectcompletion", "%" + condition.getProjectcompletion() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getSubmittimely())){
    		cons.add(new Condition("submittimely", "%" + condition.getSubmittimely() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getFillquality())){
    		cons.add(new Condition("fillquality", "%" + condition.getFillquality() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getQuality())){
    		cons.add(new Condition("quality", "%" + condition.getQuality() + "%", Condition.Type.LIKE));
    	}
    		
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return workPlanDao.search(cons, pageable);
	}
}
