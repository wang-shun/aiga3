package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ai.aiga.domain.ArchTaskMonitoringByTime;
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
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);
		String dateQueryEnd = sdf.format(endTime);
		StringBuilder nativeSql = new StringBuilder("select start_date,sum(checkNum) as check_total,sum(sessionNum) as session_total,sum(reportNum) as report_total,sum(collectNum) as collect_total,sum(successRate)*100 as success_rate"+
				" from("+
						" select to_char(b.start_date,'YYYY-MM-DD') start_Date,count(*) checkNum,0 sessionNum,0 reportNum,0 collectNum,0 successRate"+
						" from aiam.cfg_task a,aiam.task_log b"+
						" where a.cfg_task_id=b.cfg_task_id"+
						" and a.cfg_task_type_code='TASK_CHECK'"+
						" and to_char(b.start_date, 'YYYY-MM-DD')>= :endTime"+
						" and to_char(b.start_date, 'YYYY-MM-DD')<= :startTime"+
						" group by to_char(b.start_date,'YYYY-MM-DD')"+
						" union all"+
						" select to_char(b.start_date,'YYYY-MM-DD') start_Date,0 checkNum,count(*) sessionNum,0 reportNum,0 collectNum,0 successRate"+
						" from aiam.cfg_task a,aiam.task_log b"+
						" where a.cfg_task_id=b.cfg_task_id"+
						" and a.cfg_task_type_code='TASK_SESSIONK'"+
						" and to_char(b.start_date, 'YYYY-MM-DD')>= :endTime"+
						" and to_char(b.start_date, 'YYYY-MM-DD')<= :startTime"+
						" group by to_char(b.start_date,'YYYY-MM-DD')"+
						" union all"+
						" select to_char(b.start_date,'YYYY-MM-DD') start_Date,0 checkNum,0 sessionNum，count(*) reportNum,0 collectNum,0 successRate"+
						" from aiam.cfg_task a,aiam.task_log b"+
						" where a.cfg_task_id=b.cfg_task_id"+
						" and a.cfg_task_type_code='TASK_REPORT'"+
						" and to_char(b.start_date, 'YYYY-MM-DD')>= :endTime"+
						" and to_char(b.start_date, 'YYYY-MM-DD')<= :startTime"+
						" group by to_char(b.start_date,'YYYY-MM-DD')"+
						" union all"+
						" select to_char(b.start_date,'YYYY-MM-DD') start_Date,0 checkNum,0 sessionNum，0 reportNum,count(*) collectNum,0 successRate"+
						" from aiam.cfg_task a,aiam.task_log b"+
						" where a.cfg_task_id=b.cfg_task_id"+
						" and a.cfg_task_type_code='TASK_COLLECT'"+
						" and to_char(b.start_date, 'YYYY-MM-DD')>= :endTime"+
						" and to_char(b.start_date, 'YYYY-MM-DD')<= :startTime"+
						" group by to_char(b.start_date,'YYYY-MM-DD')"+
						" union all"+
						" select start_Date,0 checkNum,0 sessionNum,0 reportNum,0 collectNum,round(sum(successTotal)/sum(Total),3) successRate"+
						" from ("+
							" select to_char(start_date, 'YYYY-MM-DD') start_Date,0 Total,count(results) successTotal,0 successRate"+
							" from aiam.task_log"+
							" where results like '%uccess%'"+
							" and to_char(start_date, 'YYYY-MM-DD')>= :endTime"+
							" and to_char(start_date, 'YYYY-MM-DD')<= :startTime"+
							" group by to_char(start_date, 'YYYY-MM-DD')"+
							" union all"+
							" select to_char(start_date, 'YYYY-MM-DD') start_Date,count(results) Total,0 successTotal,0 successRate"+
							" from aiam.task_log"+
							" where to_char(start_date, 'YYYY-MM-DD')>=:endTime"+
							" and to_char(start_date, 'YYYY-MM-DD')<= :startTime"+
							" group by to_char(start_date, 'YYYY-MM-DD')"+
							" )group by start_Date"+
						" )group by start_Date"
		);

		params.add(new ParameterCondition("startTime", dateQueryStart));
		params.add(new ParameterCondition("endTime", dateQueryEnd));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoring.class);
		
	}
	
	public List<ArchTaskMonitoringByTime> queryTaskCount(ArchTaskMonitoringByTime condition2) throws ParseException {
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();		
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition2.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
				"select finishTime as finish_date,sum(checkTotal) as check_total,sum(sessionTotal) as session_total,sum(reportTotal) as report_total,sum(collectTotal) as collect_total,sum(checkTotal)+sum(sessionTotal)+sum(reportTotal)+sum(collectTotal) as task_total\n" +
						"from("+
						"       select to_char(b.finish_date,'HH24')+1 as finishTime,count(a.cfg_task_type_code) as checkTotal,0 sessionTotal,0 reportTotal,0 collectTotal\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and b.results like '%uccess%'\n" +
						"       and a.cfg_task_type_code='TASK_CHECK'\n" +
						"       and to_char(b.finish_date,'YYYY-MM-DD')= :startDate\n" +
						"       group by to_char(b.finish_date,'HH24')\n" +
						"       union all\n" +
						"       select to_char(b.finish_date,'HH24')+1 as finishTime,0 checkTotal,count(a.cfg_task_type_code) as sessionTotal,0 reportTotal,0 collectTotal\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and b.results like '%uccess%'\n" +
						"       and a.cfg_task_type_code='TASK_SESSION'\n" +
						"       and to_char(b.finish_date,'YYYY-MM-DD')= :startDate\n" +
						"       group by to_char(b.finish_date,'HH24')\n" +
						"       union all\n" +
						"       select to_char(b.finish_date,'HH24')+1 as finishTime1,0 checkTotal,0 sessionTotal,count(a.cfg_task_type_code) as reportTotal,0 collectTotal\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and b.results like '%uccess%'\n" +
						"       and a.cfg_task_type_code='TASK_REPORT'\n" +
						"       and to_char(b.finish_date,'YYYY-MM-DD')= :startDate\n" +
						"       group by to_char(b.finish_date,'HH24')\n" +
						"       union all\n" +
						"       select to_char(b.finish_date,'HH24')+1 as finishTime,0 checkTotal,0 sessionTotal,0 reportTotal,count(a.cfg_task_type_code) as collectTotal\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and b.results like '%uccess%'\n" +
						"       and a.cfg_task_type_code='TASK_COLLECT'\n" +
						"       and to_char(b.finish_date,'YYYY-MM-DD')= :startDate\n" +
						"       group by to_char(b.finish_date,'HH24')+1\n" +
						"     )\n" +
						" group by finishTime"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
	
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByTime.class);
		
	}

}
