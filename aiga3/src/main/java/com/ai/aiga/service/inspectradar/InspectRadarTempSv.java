package com.ai.aiga.service.inspectradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.inspectradar.InspectRadarTempDao;
import com.ai.aiga.domain.inspectradar.InspectRadarTemp;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class InspectRadarTempSv extends BaseService {
	@Autowired
	private InspectRadarTempDao inspectRadarTempDao;
	
	public void save(InspectRadarTemp inspectRadarTemp){
		inspectRadarTempDao.save(inspectRadarTemp);
    }
    
	public InspectRadarTemp findOne(Long tempId){
		if(tempId==null||tempId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return inspectRadarTempDao.findOne(tempId);
	}
	
	public void delete(Long tempId){
		if(tempId==null||tempId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		inspectRadarTempDao.delete(tempId);
	}
	
	public void update(InspectRadarTemp inspectRadarTemp){
		inspectRadarTempDao.save(inspectRadarTemp);
	}
}
