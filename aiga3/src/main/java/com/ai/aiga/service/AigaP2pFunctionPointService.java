package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaP2pFunctionPointDao;
import com.ai.aiga.domain.AigaP2pFunctionPoint;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class AigaP2pFunctionPointService extends BaseService{

	@Autowired
	private AigaP2pFunctionPointDao aigaP2pFunctionPointDao;
	
	public List<AigaP2pFunctionPoint> getAll(){
		
		
		
		
		log.info("info-taoyf");
		return aigaP2pFunctionPointDao.findAll();
	}
	
	
	
	
}
