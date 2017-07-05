package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class AmCoreIndexSv extends BaseService {
	
	@Autowired
	private AmCoreIndexDao amCoreIndexDao;
	
	public List<AmCoreIndex>findAmCoreIndex(){
		return amCoreIndexDao.findAll();
	}
	
}
