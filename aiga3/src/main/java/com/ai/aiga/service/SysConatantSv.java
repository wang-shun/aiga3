package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.SysConstantDao;
import com.ai.aiga.domain.SysConstant;
import com.ai.aiga.service.base.BaseService;


@Service
@Transactional
public class SysConatantSv extends BaseService{
	
	@Autowired
	private SysConstantDao sysConstantDao;
	
	//查询组织类型/证件类型
	public List<SysConstant> findConstant(String category){
		return  sysConstantDao.findByCategory(category);
	}
	
	
	
}
