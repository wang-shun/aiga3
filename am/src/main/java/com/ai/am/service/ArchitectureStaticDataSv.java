package com.ai.am.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.ArchitectureStaticDataDao;
import com.ai.am.domain.ArchitectureStaticData;
import com.ai.am.service.base.BaseService;

@Service
@Transactional
public class ArchitectureStaticDataSv extends BaseService {
	@Autowired
	private ArchitectureStaticDataDao architectureStaticDataDao;
	
	public List<ArchitectureStaticData>findAll(){
		return architectureStaticDataDao.findAll();
	}
	
	public List<ArchitectureStaticData>findByCodeType(String codeType){
		return architectureStaticDataDao.findByCodeType(codeType);
	}
	
	public List<ArchitectureStaticData>findByCodeTypeAndCodeValue(String codeType,String codeValue){
		return architectureStaticDataDao.findByCodeTypeAndCodeValue(codeType,codeValue);
	}
}
