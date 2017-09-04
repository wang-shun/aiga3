package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiSysHealthReportDao;
import com.ai.aiga.domain.ArchiSysHealthReport;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchiSysHealthReportSv extends BaseService {
	
	@Autowired
	private ArchiSysHealthReportDao archiSysHealthReportDao;
	
	public List<ArchiSysHealthReport> getSystemIndex(Long onlysysId) {
		if(onlysysId == null || onlysysId<=0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlysysId");
		}
		return archiSysHealthReportDao.findByOnlysysId(onlysysId);		
	}
}
