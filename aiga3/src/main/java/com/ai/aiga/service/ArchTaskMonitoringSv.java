package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.ai.aiga.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.AigaAuthorDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
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

	public List<ArchTaskMonitoringByTime> queryTaskCount(ArchTaskMonitoringByTime condition2)  throws ParseException{

		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
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

	public List<ArchTaskMonitoringByFrequencyAndTimes> queryTaskByFrequency(ArchTaskMonitoringByFrequencyAndTimes condition3) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition3.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
				"select sum(firstTimes) as first_times," +
				"       sum(secondTimes) as second_times," +
				"       sum(thirdTimes) as third_times," +
				"       sum(fourTimes) as four_times" +
				"  from (select count(*) firstTimes," +
				"               0 secondTimes," +
				"               0 thirdTimes," +
				"               0 fourTimes" +
				"          from (select cfg_task_id, count(cfg_task_id) firstTimes" +
				"                  from aiam.task_log" +
				"                 where results like '%uccess%'" +
				"                   and to_char(finish_date, 'YYYY-MM-DD') = :startDate" +
				"                 group by cfg_task_id" +
				"                 order by firstTimes)" +
				"         where firstTimes <= 5" +
				"        union all" +
				"        select 0 firstTimes，count(*) secondTimes," +
				"               0 thirdTimes," +
				"               0 fourTimes" +
				"          from (select cfg_task_id, count(cfg_task_id) secondTimes" +
				"                  from aiam.task_log" +
				"                 where results like '%uccess%'" +
				"                   and to_char(finish_date, 'YYYY-MM-DD') = :startDate" +
				"                 group by cfg_task_id" +
				"                 order by secondTimes)" +
				"         where secondTimes >= 6" +
				"           and secondTimes <= 10" +
				"        union all" +
				"        select 0 firstTimes," +
				"               0 secondTimes," +
				"               count(*) thirdTimes," +
				"               0 fourTimes" +
				"          from (select cfg_task_id, count(cfg_task_id) thirdTimes" +
				"                  from aiam.task_log" +
				"                 where results like '%uccess%'" +
				"                   and to_char(finish_date, 'YYYY-MM-DD') = :startDate" +
				"                 group by cfg_task_id" +
				"                 order by thirdTimes)" +
				"         where thirdTimes >= 11" +
				"           and thirdTimes <= 20" +
				"        union all" +
				"        select 0 firstTimes," +
				"               0 secondTimes," +
				"               0 thirdTimes," +
				"               count(*) fourTimes" +
				"          from (select cfg_task_id, count(cfg_task_id) fourTimes" +
				"                  from aiam.task_log" +
				"                 where results like '%uccess%'" +
				"                   and to_char(finish_date, 'YYYY-MM-DD') = :startDate" +
				"                 group by cfg_task_id" +
				"                 order by fourTimes)" +
				"         where fourTimes >= 21)"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByFrequencyAndTimes.class);

	}

	public List<ArchTaskMonitoringByFrequencyAndTimes> queryTaskByTimes(ArchTaskMonitoringByFrequencyAndTimes condition4) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition4.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
						"select sum(firstMinutes) as first_minutes,sum(secondMinutes) as second_minutes,sum(thirdMinutes) as third_minutes,sum(fourMinutes) as four_minutes\n" +
						"from(\n" +
						"       select count(*) as firstMinutes,0 secondMinutes,0 thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=5\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,count(*) as secondMinutes,0 thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=6\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=10\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,0 secondMinutes,count(*) as thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=11\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=15\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,0 secondMinutes,0 thirdMinutes,count(*) as fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=16\n" +
						")\n"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByFrequencyAndTimes.class);

	}

	public List<ArchTaskMonitoringTable> queryByConditionTable(ArchTaskMonitoringTable condition5 ) throws ParseException{
		System.out.println("condition5----------------------=-=-"+condition5);
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = condition5.getStartDate();
		StringBuilder nativeSql = new StringBuilder(
						"select start_date,sum(checkNum) check_total,sum(sessionNum) session_total,sum(reportNum) report_total,sum(collectNum) collect_total,sum(successRate)*100 success_rate\n" +
						" from(\n" +
						"       select to_char(b.start_date,'YYYY-MM-DD') start_date,count(*) checkNum,0 sessionNum,0 reportNum,0 collectNum,0 successRate\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id \n" +
						"       and a.cfg_task_type_code='TASK_CHECK'\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"       group by to_char(b.start_date,'YYYY-MM-DD')\n" +
						"       union all\n" +
						"       select to_char(b.start_date,'YYYY-MM-DD') start_date,0 checkNum,count(*) sessionNum,0 reportNum,0 collectNum,0 successRate\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id \n" +
						"       and a.cfg_task_type_code='TASK_SESSIONK'\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"       group by to_char(b.start_date,'YYYY-MM-DD')\n" +
						"       union all\n" +
						"       select to_char(b.start_date,'YYYY-MM-DD') start_date,0 checkNum,0 sessionNum，count(*) reportNum,0 collectNum,0 successRate\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id \n" +
						"       and a.cfg_task_type_code='TASK_REPORT'\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"       group by to_char(b.start_date,'YYYY-MM-DD')\n" +
						"       union all\n" +
						"       select to_char(b.start_date,'YYYY-MM-DD') start_date,0 checkNum,0 sessionNum，0 reportNum,count(*) collectNum,0 successRate\n" +
						"       from aiam.cfg_task a,aiam.task_log b\n" +
						"       where a.cfg_task_id=b.cfg_task_id \n" +
						"      and a.cfg_task_type_code='TASK_COLLECT'\n" +
						"     and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"       and to_date(to_char(b.start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"      group by to_char(b.start_date,'YYYY-MM-DD')\n" +
						"      union all\n" +
						"      select start_date,0 checkNum,0 sessionNum,0 reportNum,0 collectNum,round(sum(successTotal)/sum(Total),3) successRate\n" +
						"      from (\n" +
						"           select to_char(start_date, 'YYYY-MM-DD') start_date,0 Total,count(results) successTotal,0 successRate\n" +
						"           from aiam.task_log\n" +
						"           where results like '%uccess%'\n" +
						"            and to_date(to_char(start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"             and to_date(to_char(start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"           group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"           union all\n" +
						"           select to_char(start_date, 'YYYY-MM-DD') start_date,count(results) Total,0 successTotal,0 successRate\n" +
						"           from aiam.task_log\n" +
						"           where to_date(to_char(start_date,'YYYY-MM-DD'),'YYYY-MM-DD')<=to_date(:startDate,'YYYY-MM-DD')\n" +
						"           and to_date(to_char(start_date,'YYYY-MM-DD'),'YYYY-MM-DD')>=to_date(:startDate,'YYYY-MM-DD') - interval '6' day\n" +
						"           group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"       )group by start_date\n" +
						" )group by start_date"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTableSecond> queryByConditionTableSecond(ArchTaskMonitoringTableSecond condition6 ) throws ParseException{
		System.out.println("condition5----------------------=-=-"+condition6);
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//startDate是字符串类型
		String dateQueryStart = condition6.getStartDate();
		StringBuilder nativeSql = new StringBuilder("\n" +
				"select finishTime as finish_date,\n" +
				"       sum(checkTotal) as check_total,\n" +
				"       sum(sessionTotal) as session_total,\n" +
				"       sum(reportTotal) as report_total,\n" +
				"       sum(collectTotal) as collect_total,\n" +
				"       sum(checkTotal) + sum(sessionTotal) + sum(reportTotal) +\n" +
				"       sum(collectTotal) as task_total\n" +
				"  from (select to_char(b.finish_date, 'HH24') + 1 as finishTime,\n" +
				"               count(a.cfg_task_type_code) as checkTotal,\n" +
				"               0 sessionTotal,\n" +
				"               0 reportTotal,\n" +
				"               0 collectTotal\n" +
				"          from aiam.cfg_task a, aiam.task_log b\n" +
				"         where a.cfg_task_id = b.cfg_task_id\n" +
				"           and b.results like '%uccess%'\n" +
				"           and a.cfg_task_type_code = 'TASK_CHECK'\n" +
				"           and to_char(b.finish_date, 'YYYY-MM-DD') = :startDate\n" +
				"         group by to_char(b.finish_date, 'HH24')\n" +
				"        union all\n" +
				"        select to_char(b.finish_date, 'HH24') + 1 as finishTime,\n" +
				"               0 checkTotal,\n" +
				"               count(a.cfg_task_type_code) as sessionTotal,\n" +
				"               0 reportTotal,\n" +
				"               0 collectTotal\n" +
				"          from aiam.cfg_task a, aiam.task_log b\n" +
				"         where a.cfg_task_id = b.cfg_task_id\n" +
				"           and b.results like '%uccess%'\n" +
				"           and a.cfg_task_type_code = 'TASK_SESSION'\n" +
				"           and to_char(b.finish_date, 'YYYY-MM-DD') = :startDate\n" +
				"         group by to_char(b.finish_date, 'HH24')\n" +
				"        union all\n" +
				"        select to_char(b.finish_date, 'HH24') + 1 as finishTime1,\n" +
				"               0 checkTotal,\n" +
				"               0 sessionTotal,\n" +
				"               count(a.cfg_task_type_code) as reportTotal,\n" +
				"               0 collectTotal\n" +
				"          from aiam.cfg_task a, aiam.task_log b\n" +
				"         where a.cfg_task_id = b.cfg_task_id\n" +
				"           and b.results like '%uccess%'\n" +
				"           and a.cfg_task_type_code = 'TASK_REPORT'\n" +
				"           and to_char(b.finish_date, 'YYYY-MM-DD') = :startDate\n" +
				"         group by to_char(b.finish_date, 'HH24')\n" +
				"        union all\n" +
				"        select to_char(b.finish_date, 'HH24') + 1 as finishTime,\n" +
				"               0 checkTotal,\n" +
				"               0 sessionTotal,\n" +
				"               0 reportTotal,\n" +
				"               count(a.cfg_task_type_code) as collectTotal\n" +
				"          from aiam.cfg_task a, aiam.task_log b\n" +
				"         where a.cfg_task_id = b.cfg_task_id\n" +
				"           and b.results like '%uccess%'\n" +
				"           and a.cfg_task_type_code = 'TASK_COLLECT'\n" +
				"           and to_char(b.finish_date, 'YYYY-MM-DD') = :startDate\n" +
				"         group by to_char(b.finish_date, 'HH24') + 1)\n" +
				" group by finishTime"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTableSecond.class);

	}

	public List<ArchTaskMonitoringTableThird> queryByConditionTableThird(ArchTaskMonitoringTableThird condition7 ) throws ParseException{
		System.out.println("condition7----------------------=-=-"+condition7);
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//startDate是字符串类型
		String dateQueryStart = condition7.getStartDate();
		StringBuilder nativeSql = new StringBuilder("\n" +
				"select sum(firstTimes) first_times,sum(secondTimes) second_times,sum(thirdTimes) third_times,sum(fourTimes) four_times,\n" +
				"round(sum(firstTimes)/（sum(firstTimes)+sum(secondTimes)+sum(thirdTimes)+sum(fourTimes)）,3)*100 first_pro,\n" +
				"round(sum(secondTimes)/（sum(firstTimes)+sum(secondTimes)+sum(thirdTimes)+sum(fourTimes)),3)*100 second_pro,\n" +
				"round(sum(thirdTimes)/(sum(firstTimes)+sum(secondTimes)+sum(thirdTimes)+sum(fourTimes)),3)*100 third_pro,\n" +
				"round(sum(fourTimes)/(sum(firstTimes)+sum(secondTimes)+sum(thirdTimes)+sum(fourTimes)),3)*100 four_pro\n" +
				"from (\n" +
				"     select count(*) firstTimes,0 secondTimes,0 thirdTimes,0 fourTimes\n" +
				"     from (select cfg_task_id,count(cfg_task_id) firstTimes\n" +
				"          from aiam.task_log\n" +
				"          where results like '%uccess%'\n" +
				"          and to_char(finish_date,'YYYY-MM-DD')=:startDate\n" +
				"          group by cfg_task_id\n" +
				"          order by firstTimes\n" +
				"          )\n" +
				"      where firstTimes<=5\n" +
				"\n" +
				"      union all\n" +
				"      select 0 firstTimes,count(*) secondTimes,0 thirdTimes,0 fourTimes\n" +
				"      from (select cfg_task_id,count(cfg_task_id) secondTimes\n" +
				"           from aiam.task_log\n" +
				"           where results like '%uccess%'\n" +
				"           and to_char(finish_date,'YYYY-MM-DD')=:startDate\n" +
				"           group by cfg_task_id\n" +
				"           order by secondTimes)\n" +
				"      where secondTimes>=6 and secondTimes<=10\n" +
				"\n" +
				"      union all\n" +
				"      select 0 firstTimes,0 secondTimes,count(*) thirdTimes,0 fourTimes\n" +
				"      from (select cfg_task_id,count(cfg_task_id) thirdTimes\n" +
				"           from aiam.task_log\n" +
				"           where results like '%uccess%'\n" +
				"           and to_char(finish_date,'YYYY-MM-DD')=:startDate\n" +
				"           group by cfg_task_id\n" +
				"           order by thirdTimes)\n" +
				"      where thirdTimes>=11 and thirdTimes<=20\n" +
				"      \n" +
				"      union all\n" +
				"      select 0 firstTimes,0 secondTimes,0 thirdTimes,count(*) fourTimes\n" +
				"      from (select cfg_task_id,count(cfg_task_id)  fourTimes\n" +
				"            from aiam.task_log\n" +
				"            where results like '%uccess%'\n" +
				"            and to_char(finish_date,'YYYY-MM-DD')=:startDate\n" +
				"            group by cfg_task_id\n" +
				"            order by fourTimes)\n" +
				"      where fourTimes>=21\n" +
				")"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTableThird.class);

	}

	public List<ArchTaskMonitoringTableFour> queryByConditionTableFour(ArchTaskMonitoringTableFour condition8 ) throws ParseException{
		System.out.println("condition8----------------------=-=-"+condition8);
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是字符串类型
		String dateQueryStart = condition8.getStartDate();
		StringBuilder nativeSql = new StringBuilder(
				"\n" +
						"select sum(firstMinutes) as first_minutes,sum(secondMinutes) as second_minutes,sum(thirdMinutes) as third_minutes,sum(fourMinutes) as four_minutes,\n" +
						"round((sum(firstMinutes)/(sum(firstMinutes)+sum(secondMinutes)+sum(thirdMinutes)+sum(fourMinutes))),3)*100 first_pro,\n" +
						"round((sum(secondMinutes)/(sum(firstMinutes)+sum(secondMinutes)+sum(thirdMinutes)+sum(fourMinutes))),3)*100 second_pro,\n" +
						"round((sum(thirdMinutes)/(sum(firstMinutes)+sum(secondMinutes)+sum(thirdMinutes)+sum(fourMinutes))),3)*100 third_pro,\n" +
						"round((sum(fourMinutes)/(sum(firstMinutes)+sum(secondMinutes)+sum(thirdMinutes)+sum(fourMinutes))),3)*100 four_pro\n" +
						"from(\n" +
						"       select count(*) as firstMinutes,0 secondMinutes,0 thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=5\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,count(*) as secondMinutes,0 thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=6\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=10\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,0 secondMinutes,count(*) as thirdMinutes,0 fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=11\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))<=15\n" +
						"       \n" +
						"       union all\n" +
						"       select 0 firstMinutes,0 secondMinutes,0 thirdMinutes,count(*) as fourMinutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and (to_number(to_char(finish_date,'MI'))-to_number(to_char(start_date,'MI')))>=16\n" +
						")"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTableFour.class);

	}

}
