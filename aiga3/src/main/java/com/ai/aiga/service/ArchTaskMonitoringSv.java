package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.AigaAuthorDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchTaskMonitoring;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchTaskMonitoringSv extends BaseService {

	@Autowired
	private AigaAuthorDao archTaskMonitoringDao;

	public List<ArchTaskMonitoring> queryByCondition(ArchTaskMonitoring condition) throws ParseException {		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();		
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);
		String dateQueryEnd = sdf.format(endTime);
			//nativeSql.append(" to_char(d.start_date, 'YYYY-MM-DD') <= :startDate and to_char(d.start_date, 'YYYY-MM-DD')>= :endDate group by d.start_date,a.index_name, c.task_name, d.results");			
		StringBuilder nativeSql = new StringBuilder("select start_date,sum(resultsum) as total,sum(success) as success_Total,sum(resultsum)-sum(success) as fail_Total"+
				" from (select to_char(d.start_date, 'YYYY-MM-DD') start_date,count(d.results)  resultsum,0 success"+
				" from aiam.am_core_index a,aiam.cfg_task_param_value b,aiam.cfg_task c,aiam.task_log d "+
				" where translate(b.param_value ,'x1234567890','x') is null and a.group_id = b.param_value "+
				" and b.cfg_task_id = c.cfg_task_id  and c.cfg_task_id = d.cfg_task_id  and to_char(d.start_date, 'YYYY-MM-DD') <= :startDate "+
				" and to_char(d.start_date, 'YYYY-MM-DD')>= :endDate  group by to_char(d.start_date, 'YYYY-MM-DD') union all"+
				" select to_char(d.start_date, 'YYYY-MM-DD'),0 resultsum,count(*) success "+
				" from aiam.am_core_index a,aiam.cfg_task_param_value b,aiam.cfg_task c,aiam.task_log d "+
				" where translate(b.param_value ,'x1234567890','x') is null  and a.group_id = b.param_value "+
				" and b.cfg_task_id = c.cfg_task_id and c.cfg_task_id = d.cfg_task_id  and to_char(d.start_date, 'YYYY-MM-DD') <= :startDate "+
				" and to_char(d.start_date, 'YYYY-MM-DD')>= :endDate  and d.results like '%uccess'  group by to_char(d.start_date, 'YYYY-MM-DD')   "+ 
				")group by start_date");
		params.add(new ParameterCondition("startDate", dateQueryStart));
		params.add(new ParameterCondition("endDate", dateQueryEnd));
	
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoring.class);
		
	}
}
