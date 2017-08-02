package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.dao.QuestionEventDao;
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
}
