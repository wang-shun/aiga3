package com.ai.aiga.service.inspectradar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.inspectradar.InspectRadarResultDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.inspectradar.InspectRadarResult;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class InspectRadarResultSv extends BaseService {
	@Autowired
	private InspectRadarResultDao inspectRadarResultDao;
	
	/*
	 * 查询最近的巡检结果
	 */
	public InspectRadarResult recentRecord(Long sysId) {
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		String sql  = "SELECT * FROM (SELECT * FROM INSPECT_RADAR_RESULT  WHERE sys_id = :sysId ORDER BY create_time DESC ) WHERE ROWNUM<2";
		params.add(new ParameterCondition("sysId", sysId));
		List<InspectRadarResult> bean = inspectRadarResultDao.searchByNativeSQL(sql,params,InspectRadarResult.class);
		return bean.size()>0?bean.get(0):null;
	}
	
	public List<InspectRadarResult> historyRecord(Long sysId){
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		String[] cloumns = {"TOTAL_MARK","AQ_MARK","RL_MARK","JK_MARK","GKY_MARK","RXKY_MARK","PZ_MARK","RZ_MARK","FC_MARK"};
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT sys_id ");
		for(String cloumn :cloumns) {
			sql.append(", avg("+cloumns+") as "+cloumns);
		}
		sql.append(",CREATE_TIME,SPONSOR where  FROM INSPECT_RADAR_RESULT t where sys_id = :sysId  group by to_char(CREATE_TIME,'yyyy-mm-dd')");
		params.add(new ParameterCondition("sysId", sysId));
		return inspectRadarResultDao.searchByNativeSQL(sql.toString(),params,InspectRadarResult.class);
	}
	public void save(InspectRadarResult inspectRadarResult){
		inspectRadarResultDao.save(inspectRadarResult);
    }
    
	public InspectRadarResult findOne(Long resultId){
		if(resultId==null||resultId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return inspectRadarResultDao.findOne(resultId);
	}
	
	public void delete(Long resultId){
		if(resultId==null||resultId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		inspectRadarResultDao.delete(resultId);
	}
	
	public void update(InspectRadarResult inspectRadarResult){
		inspectRadarResultDao.save(inspectRadarResult);
	}
}
