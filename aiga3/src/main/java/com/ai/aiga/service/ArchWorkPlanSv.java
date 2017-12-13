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

import com.ai.aiga.domain.ArchWorkPlan;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchWorkPlanDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchWorkPlanSv extends BaseService {

	@Autowired
	private ArchWorkPlanDao archWorkPlanDao;
	
	//find
	public List<ArchWorkPlan> findAll(){
		return archWorkPlanDao.findAll();
	}
	
	//add
	public void save(ArchWorkPlan request){
		archWorkPlanDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archWorkPlanDao.delete(id);
	}

	//update
	public void update(ArchWorkPlan request){
		try {
			archWorkPlanDao.save(request);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}
	}

	public Page<ArchWorkPlan> findAllByPage(ArchWorkPlan request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return archWorkPlanDao.search(cons, pageable);
	}

	public Page<ArchWorkPlan> queryByCondition(ArchWorkPlan condition, int pageNumber,
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
    	
    	if(condition.getBegaintime() != null){
    		cons.add(new Condition("begaintime", condition.getBegaintime(), Condition.Type.GE));
    	}
    	if(condition.getEndtime() != null){
    		cons.add(new Condition("endtime",  condition.getEndtime() , Condition.Type.LE));
    	}
    		
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archWorkPlanDao.search(cons, pageable);
	}
}
