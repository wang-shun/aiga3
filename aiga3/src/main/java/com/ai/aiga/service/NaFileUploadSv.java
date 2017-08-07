package com.ai.aiga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaFileUploadDao;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class NaFileUploadSv extends BaseService {
	
	@Autowired
	private NaFileUploadDao naFileUploadDao;
	
	public NaFileUpload findFileName(Long quesId){
		return naFileUploadDao.selectFileName(quesId);
	}
}
