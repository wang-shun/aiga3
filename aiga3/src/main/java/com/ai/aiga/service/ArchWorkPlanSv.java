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
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchWorkPlanDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
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
		String name = condition.getName();
		if(name==null||name==""){
			name = "_";
		}
		StringBuilder nativeSql = new StringBuilder("select * from arch_work_plan am where 1=1 and am.name like '%"+name+"%' "); 
		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (condition.getId() != 0) {
			nativeSql.append(" am.id = :id ");
			params.add(new ParameterCondition("id", condition.getId()));
		}
		if (StringUtils.isNotBlank(condition.getPerson())) {
			nativeSql.append(" and am.person = :person ");
			params.add(new ParameterCondition("person", condition.getPerson()));
		}
		
		if (StringUtils.isNotBlank(condition.getMatters())) {
			nativeSql.append(" and am.matters = :matters ");
			params.add(new ParameterCondition("matters", condition.getMatters()));
		}
		if (StringUtils.isNotBlank(condition.getClassification())) {
			nativeSql.append(" and am.classification = :classification ");
			params.add(new ParameterCondition("classification", condition.getClassification()));
		}
		if (StringUtils.isNotBlank(condition.getJobcontent())) {
			nativeSql.append(" and am.jobcontent = :jobcontent ");
			params.add(new ParameterCondition("jobcontent", condition.getJobcontent()));
		}
		if (StringUtils.isNotBlank(condition.getCompletion())) {
			nativeSql.append(" and am.completion = :completion ");
			params.add(new ParameterCondition("completion", condition.getCompletion()));
		} 
		if (StringUtils.isNotBlank(condition.getWorkstate())) {
			nativeSql.append(" and am.workstate = :workstate ");
			params.add(new ParameterCondition("workstate", condition.getWorkstate()));
		} 
		if (StringUtils.isNotBlank(condition.getProjectcompletion())) {
			nativeSql.append(" and am.projectcompletion = :projectcompletion ");
			params.add(new ParameterCondition("projectcompletion", condition.getProjectcompletion()));
		} 
		if (StringUtils.isNotBlank(condition.getSubmittimely())) {
			nativeSql.append(" and am.submittimely = :submittimely ");
			params.add(new ParameterCondition("submittimely", condition.getSubmittimely()));
		}
		if (StringUtils.isNotBlank(condition.getFillquality())) {
			nativeSql.append(" and am.fillquality = :fillquality ");
			params.add(new ParameterCondition("fillquality", condition.getFillquality()));
		}
		if (StringUtils.isNotBlank(condition.getQuality())) {
			nativeSql.append(" and am.quality = :quality ");
			params.add(new ParameterCondition("quality", condition.getQuality()));
		}		
		
		if (condition.getBegaintime() != null) {
			nativeSql.append(" and am.begaintime >= :begaintime ");
			params.add(new ParameterCondition("begaintime", condition.getBegaintime()));
		}
		if (condition.getEndtime() != null) {
			nativeSql.append(" and am.endtime <= :endtime");
			params.add(new ParameterCondition("endtime", condition.getEndtime()));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archWorkPlanDao.searchByNativeSQL(nativeSql.toString(), params, ArchWorkPlan.class, pageable);
		
	}
}
