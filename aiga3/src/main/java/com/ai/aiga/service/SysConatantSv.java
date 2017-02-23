package com.ai.aiga.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaOrganizeDao;
import com.ai.aiga.dao.SysConstantDao;
import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.domain.SysConstant;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.OrginazeRequest;


@Service
@Transactional
public class SysConatantSv extends BaseService{
	
	@Autowired
	private SysConstantDao sysConstantDao;
	
	//查询组织类型/证件类型
	public List<SysConstant> findConstant(String category){
		return  sysConstantDao.findByCategoryLike(category);
	}
	
	
	
}
