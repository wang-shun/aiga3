package com.ai.aiga.service.inspectradar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.inspectradar.InspectRadarRuleDao;
import com.ai.aiga.domain.inspectradar.InspectRadarRule;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.inspectradar.dto.InspectSysList;

@Service
@Transactional
public class InspectRadarRuleSv extends BaseService {
	@Autowired
	private InspectRadarRuleDao inspectRadarRuleDao;
	
	public List<InspectSysList> sysList() {
		String sql  = "SELECT t.onlysys_id as sys_id,t.name  FROM (select sys_id from INSPECT_RADAR_RULE group by sys_id) r , architecture_third t where r.sys_id = t.onlysys_id";
		List<InspectSysList> bean = inspectRadarRuleDao.searchByNativeSQL(sql,InspectSysList.class);
		return bean;
	}
	
	public void save(InspectRadarRule inspectRadarRule){
		inspectRadarRuleDao.save(inspectRadarRule);
    }
    
	public InspectRadarRule findOne(Long ruleId){
		if(ruleId==null||ruleId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return inspectRadarRuleDao.findOne(ruleId);
	}
	
	public void delete(Long ruleId){
		if(ruleId==null||ruleId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		inspectRadarRuleDao.delete(ruleId);
	}
	
	public void update(InspectRadarRule inspectRadarRule){
		inspectRadarRuleDao.save(inspectRadarRule);
	}
}
