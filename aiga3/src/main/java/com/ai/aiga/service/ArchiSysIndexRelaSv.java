package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiSysIndexRelaDao;
import com.ai.aiga.domain.ArchiSysIndexRela;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchiSysIndexRelaSv extends BaseService {
	@Autowired
	private ArchiSysIndexRelaDao archiSysIndexRelaDao;
	
	public List<ArchiSysIndexRela> findSysIndex(Long onlysysId) {	
		return archiSysIndexRelaDao.findByOnlysysId(onlysysId);	
	}
}
