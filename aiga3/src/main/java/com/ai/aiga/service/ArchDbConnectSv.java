package com.ai.aiga.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchDbConnectDao;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchDbConnectSv extends BaseService {

	@Autowired
	private ArchDbConnectDao archDbConnectDao;
	
	public List<ArchDbConnect>findArchDbConnect(){
		return archDbConnectDao.findAll();
	}
	
}
