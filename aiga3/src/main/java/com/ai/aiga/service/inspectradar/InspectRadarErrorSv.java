package com.ai.aiga.service.inspectradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.inspectradar.InspectRadarErrorDao;
import com.ai.aiga.domain.inspectradar.InspectRadarError;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class InspectRadarErrorSv extends BaseService {
	@Autowired
	private InspectRadarErrorDao inspectRadarErrorDao;
	
	public void save(InspectRadarError inspectRadarError){
		inspectRadarErrorDao.save(inspectRadarError);
    }
    
	public InspectRadarError findOne(Long errId){
		if(errId==null||errId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return inspectRadarErrorDao.findOne(errId);
	}
	
	public void delete(Long errId){
		if(errId==null||errId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		inspectRadarErrorDao.delete(errId);
	}
	
	public void update(InspectRadarError inspectRadarError){
		inspectRadarErrorDao.save(inspectRadarError);
	}
}
