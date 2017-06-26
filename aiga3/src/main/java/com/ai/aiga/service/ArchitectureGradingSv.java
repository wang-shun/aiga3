package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

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

@Service
@Transactional
public class ArchitectureGradingSv extends BaseService {
	@Autowired
	private ArchitectureGradingDao architectureGradingDao;
	public List<ArchitectureGrading> findAll(){
		return architectureGradingDao.findAll();
	}
	public List<ArchitectureGrading> findAllCondition(long ext1, String state){
		List<Condition> cons = new ArrayList<Condition>();
//		if(StringUtils.isNoneBlank(testName)){
//		  cons.add(new Condition("testName", "%".concat(testName).concat("%"), Condition.Type.LIKE));
//		}
//
//		if(sysId > 0){
//		  cons.add(new Condition("sysId", sysId, Condition.Type.EQ));
//		}
//
//		if(sysSubId > 0){
//		  cons.add(new Condition("sysSubId", sysSubId, Condition.Type.EQ));
//		}
//
//		if(funId > 0){
//		  cons.add(new Condition("funId", funId, Condition.Type.EQ));
//		}
//
//		if(important > 0){
//		  cons.add(new Condition("important", important, Condition.Type.EQ));
//		}
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
