package com.ai.aiga.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaFunctionDao;
import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.FunctionRequest;

@Service
@Transactional
public class FunctionSv extends BaseService{
	
	@Autowired
	private AigaFunctionDao aigaFunctionDao;

	public Object findFunctions() {
		return null;
	}

	public void save(FunctionRequest funcRequest) {
		
		AigaFunction aigaFuction = new AigaFunction();
		aigaFuction.setCreateDate(new Date(System.currentTimeMillis()));
		
		aigaFunctionDao.save(aigaFuction);
	}
	
	

}
