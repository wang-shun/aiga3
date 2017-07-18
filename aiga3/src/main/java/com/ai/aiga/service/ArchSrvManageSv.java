package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchSrvManageSv extends BaseService {

	@Autowired
	private ArchSrvManageDao archSrvManageDao;
	
	public List<ArchSrvManage>findArchSrvManages(){
		return archSrvManageDao.findAll();
	}
	
}
