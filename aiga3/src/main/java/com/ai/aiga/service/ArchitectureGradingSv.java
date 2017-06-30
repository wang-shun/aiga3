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

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureGradingDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;

@Service
@Transactional
public class ArchitectureGradingSv extends BaseService {
	@Autowired
	private ArchitectureGradingDao architectureGradingDao;
	public List<ArchitectureGrading> findAll(){
		return architectureGradingDao.findAll();
	}
	public List<ArchitectureGrading> findAllCondition(ArchiGradingConditionParam input) throws ParseException{
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNoneBlank(input.getName())){
		  cons.add(new Condition("name", "%".concat(input.getName()).concat("%"), Condition.Type.LIKE));
		}

		if(StringUtils.isNoneBlank(input.getExt1())){
		  cons.add(new Condition("ext1", input.getExt1(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getState())){
		  cons.add(new Condition("state", input.getState(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getApplyUser())){
		  cons.add(new Condition("applyUser", input.getApplyUser(), Condition.Type.EQ));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(StringUtils.isNoneBlank(input.getBegainTime())){
		  String  dateFir = input.getBegainTime()+" 00:00:00";
		  Date beginDate = format.parse(dateFir);	
		  cons.add(new Condition("applyTime", beginDate, Condition.Type.GT));
		}
		if(StringUtils.isNoneBlank(input.getEndTime())){
		  String dateSec = input.getEndTime()+" 23:59:59";
		  Date endDate = format.parse(dateSec);	
		  cons.add(new Condition("applyTime", endDate, Condition.Type.LT));
		}
		return architectureGradingDao.search(cons);		
	}
	
	public Page<ArchitectureGrading> findAllConditionPage(int pageNumber, int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureGradingDao.search(cons, pageable);
	}
	
	public ArchitectureGrading findOne(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureGradingDao.findOne(id);
	}
	
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureGradingDao.delete(id);
	}
	
	public void save(ArchitectureGrading architectureGrading){
			
		architectureGradingDao.save(architectureGrading);
	}
	
	public void update(ArchitectureGrading architectureGrading){
			
		architectureGradingDao.save(architectureGrading);
	}
}