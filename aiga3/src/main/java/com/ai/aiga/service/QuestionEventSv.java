package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

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
}
