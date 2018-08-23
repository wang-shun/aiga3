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
import com.ai.aiga.service.inspectradar.dto.HistoryRecord;
import com.ai.aiga.service.inspectradar.dto.RecentRecord;

@Service
@Transactional
public class InspectRadarResultSv extends BaseService {
	@Autowired
	private InspectRadarResultDao inspectRadarResultDao;
	
	/*
	 * 查询最近的巡检结果
	 */
	public RecentRecord recentRecord(Long sysId) {
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		String sql  = "SELECT t.*, to_char(t.create_time,'yyyy-mm-dd') as create_date FROM INSPECT_RADAR_RESULT t WHERE  t.sys_id = :sysId ORDER BY t.create_time DESC";
		params.add(new ParameterCondition("sysId", sysId));
		List<RecentRecord> bean = inspectRadarResultDao.searchByNativeSQL(sql,params,RecentRecord.class);
		return bean.size()>0?bean.get(0):null;
	}
	
	public List<HistoryRecord> historyRecord(Long sysId){
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		String[] cloumns = {"total_mark","aq_mark","rl_mark","jk_mark","gky_mark","rxky_mark","pz_mark","rz_mark","fc_mark"};
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT to_char(CREATE_TIME,'yyyy-mm-dd') as create_date");
		for(String cloumn :cloumns) {
			sql.append(", ROUND(avg("+cloumn+"),1) as "+cloumn);
		}
		sql.append(" FROM INSPECT_RADAR_RESULT  where sys_id = :sysId  and sysdate-30 <= CREATE_TIME  group by to_char(CREATE_TIME,'yyyy-mm-dd')");
		params.add(new ParameterCondition("sysId", sysId));
		return inspectRadarResultDao.searchByNativeSQL(sql.toString(),params,HistoryRecord.class);
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
