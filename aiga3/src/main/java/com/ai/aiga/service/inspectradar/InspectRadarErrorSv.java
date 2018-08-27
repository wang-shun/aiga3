package com.ai.aiga.service.inspectradar;

import java.util.List;

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
	
	public List<InspectRadarError> sysRecentError(Long sysId) {
		String sql  = "SELECT T.* FROM inspect_radar_error t,(select max(create_time) as create_time from Inspect_radar_error) a where t.sys_id = "+sysId+" and t.create_time = a.create_time;";
		List<InspectRadarError> bean = inspectRadarErrorDao.searchByNativeSQL(sql,InspectRadarError.class);
		return bean;
	}
	
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
