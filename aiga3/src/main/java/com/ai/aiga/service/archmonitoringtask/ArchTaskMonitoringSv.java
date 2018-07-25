package com.ai.aiga.service.archmonitoringtask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.ai.aiga.service.archmonitoringtask.dto.*;
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

		StringBuilder nativeSql = new StringBuilder(
				"\n" +
						" select start_date,sum(check_total) check_total,sum(session_total) session_total,sum(report_total) report_total,sum(collect_total) collect_total," +
						"sum(check_rate)*100 check_rate,sum(session_rate)*100 session_rate,sum(report_rate)*100 report_rate,sum(collect_rate)*100 collect_rate\n" +
						" from (\n" +
						" select start_Date start_date,\n" +
						"       sum(checkNum) as check_total,\n" +
						"       sum(sessionNum) as session_total,\n" +
						"       sum(reportNum) as report_total,\n" +
						"       sum(collectNum) as collect_total,\n" +
						"       0 check_rate,\n" +
						"       0 session_rate,\n" +
						"       0 report_rate,\n" +
						"       0 collect_rate\n" +
						"  from (\n" +
						"          select to_char(b.start_date, 'YYYY-MM-DD') start_Date,\n" +
						"               count(1) checkNum,\n" +
						"               0 sessionNum,\n" +
						"               0 reportNum,\n" +
						"               0 collectNum,\n" +
						"               0 checkRate,0 sessionRate,0 reportRate,0 collectRate\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and a.cfg_task_type_code = 'TASK_CHECK'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"         group by to_char(b.start_date, 'YYYY-MM-DD')\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'YYYY-MM-DD') start_Date,\n" +
						"               0 checkNum,\n" +
						"               count(*) sessionNum,\n" +
						"               0 reportNum,\n" +
						"               0 collectNum,\n" +
						"               0 checkRate,0 sessionRate,0 reportRate,0 collectRate\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and a.cfg_task_type_code = 'TASK_SESSION'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"         group by to_char(b.start_date, 'YYYY-MM-DD')\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'YYYY-MM-DD') start_Date,\n" +
						"               0 checkNum,\n" +
						"               0 sessionNum,\n" +
						"               count(*) reportNum,\n" +
						"               0 collectNum,\n" +
						"               0 checkRate,0 sessionRate,0 reportRate,0 collectRate\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and a.cfg_task_type_code = 'TASK_REPORT'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"         group by to_char(b.start_date, 'YYYY-MM-DD')\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'YYYY-MM-DD') start_Date,\n" +
						"               0 checkNum,\n" +
						"               0 sessionNum，0 reportNum,\n" +
						"               count(*) collectNum,\n" +
						"               0 checkRate,0 sessionRate,0 reportRate,0 collectRate\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and a.cfg_task_type_code = 'TASK_COLLECT'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"         group by to_char(b.start_date, 'YYYY-MM-DD')\n" +
						"         )\n" +
						" group by start_Date\n" +
						" \n" +
						" union all\n" +
						" select start_Date start_date,\n" +
						"         0 checkNum,\n" +
						"         0 sessionNum,\n" +
						"         0 reportNum,\n" +
						"         0 collectNum,\n" +
						"         decode(checkTotal,0,0,round(checkRate / checkTotal,2)) check_rate,\n" +
						"         decode(sessionTotal,0,0,round(sessionRate / sessionTotal,2)) session_rate,\n" +
						"         decode(reportTotal,0,0,round(reportRate / reportTotal,2)) report_rate,\n" +
						"         decode(collectTotal,0,0,round(collectRate / collectTotal,2)) collect_rate\n" +
						"   from (select start_Date start_date,\n" +
						"                sum(checkRate) checkRate,\n" +
						"                sum(sessionRate) sessionRate,\n" +
						"                sum(reportRate) reportRate,\n" +
						"                sum(collectRate) collectRate,\n" +
						"                sum(checkTotal) checkTotal,\n" +
						"                sum(sessionTotal) sessionTotal,\n" +
						"                sum(reportTotal) reportTotal,\n" +
						"                sum(collectTotal) collectTotal\n" +
						"           from (select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        count(1) checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and results like '%uccess%'\n" +
						"                    and b.cfg_task_type_code = 'TASK_CHECK'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"           \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        count(1) sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and results like '%uccess%'\n" +
						"                    and cfg_task_type_code = 'TASK_SESSION'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        count(1) reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and results like '%uccess%'\n" +
						"                    and cfg_task_type_code = 'TASK_REPORT'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        count(1) collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and results like '%uccess%'\n" +
						"                    and cfg_task_type_code = 'TASK_COLLECT'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        count(1) checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and cfg_task_type_code = 'TASK_CHECK'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        count(1) sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and cfg_task_type_code = 'TASK_SESSION'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        count(1) reportTotal,\n" +
						"                        0 collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id\n" +
						"                    and cfg_task_type_code = 'TASK_REPORT'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select to_char(start_date, 'YYYY-MM-DD') start_Date,\n" +
						"                        0 checkRate,\n" +
						"                        0 sessionRate,\n" +
						"                        0 reportRate,\n" +
						"                        0 collectRate,\n" +
						"                        0 checkTotal,\n" +
						"                        0 sessionTotal,\n" +
						"                        0 reportTotal,\n" +
						"                        count(1) collectTotal\n" +
						"                   from aiam.task_log a, aiam.cfg_task b\n" +
						"                  where a.cfg_task_id=b.cfg_task_id \n" +
						"                    and cfg_task_type_code = 'TASK_COLLECT'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endTime\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startTime\n" +
						"                  group by to_char(start_date, 'YYYY-MM-DD')\n" +
						"                 \n" +
						"                 )\n" +
						"          group by start_Date\n" +
						"         \n" +
						"         )\n" +
						"  group by start_Date, decode(checkTotal,0,0,round(checkRate / checkTotal,2)),\n" +
						"         decode(sessionTotal,0,0,round(sessionRate / sessionTotal,2)),\n" +
						"         decode(reportTotal,0,0,round(reportRate / reportTotal,2)),\n" +
						"         decode(collectTotal,0,0,round(collectRate / collectTotal,2))\n" +
						" \n" +
						" )\n" +
						" group by start_date"
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
				"select startTime as start_time,\n" +
						"       sum(checkTotal) as check_total,\n" +
						"       sum(sessionTotal) as session_total,\n" +
						"       sum(reportTotal) as report_total,\n" +
						"       sum(collectTotal) as collect_total,\n" +
						"       sum(checkTotal) + sum(sessionTotal) + sum(reportTotal) +\n" +
						"       sum(collectTotal) as task_total\n" +
						"  from (select to_char(b.start_date, 'HH24')+0 as startTime,\n" +
						"               count(a.cfg_task_type_code) as checkTotal,\n" +
						"               0 sessionTotal,\n" +
						"               0 reportTotal,\n" +
						"               0 collectTotal\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and b.results like '%uccess%'\n" +
						"           and a.cfg_task_type_code = 'TASK_CHECK'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') = :startDate\n" +
						"         group by to_char(b.start_date, 'HH24')+0\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'HH24')+0 as startTime,\n" +
						"               0 checkTotal,\n" +
						"               count(a.cfg_task_type_code) as sessionTotal,\n" +
						"               0 reportTotal,\n" +
						"               0 collectTotal\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and b.results like '%uccess%'\n" +
						"           and a.cfg_task_type_code = 'TASK_SESSION'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') =:startDate\n" +
						"         group by to_char(b.start_date, 'HH24')+0\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'HH24')+0 as startTime,\n" +
						"               0 checkTotal,\n" +
						"               0 sessionTotal,\n" +
						"               count(a.cfg_task_type_code) as reportTotal,\n" +
						"               0 collectTotal\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and b.results like '%uccess%'\n" +
						"           and a.cfg_task_type_code = 'TASK_REPORT'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') = :startDate\n" +
						"         group by to_char(b.start_date, 'HH24')+0\n" +
						"        union all\n" +
						"        select to_char(b.start_date, 'HH24')+0 as startTime,\n" +
						"               0 checkTotal,\n" +
						"               0 sessionTotal,\n" +
						"               0 reportTotal,\n" +
						"               count(a.cfg_task_type_code) as collectTotal\n" +
						"          from aiam.cfg_task a, aiam.task_log b\n" +
						"         where a.cfg_task_id = b.cfg_task_id\n" +
						"           and b.results like '%uccess%'\n" +
						"           and a.cfg_task_type_code = 'TASK_COLLECT'\n" +
						"           and to_char(b.start_date, 'YYYY-MM-DD') = :startDate\n" +
						"         group by to_char(b.start_date, 'HH24')+0)\n" +
						" group by startTime"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByTime.class);
		

	}

	public List<ArchTaskMonitoringHintView> queryTaskCountHint(Date startDate)  throws ParseException{

		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(startDate);
		StringBuilder nativeSql = new StringBuilder(

				"select to_char(start_date,'HH24:MI') as start_time,to_char(finish_date,'HH24:MI') as finish_time " +
						" from aiam.task_log where to_char(start_date,'YYYY-MM-DD')=:startDate \n" +
						" and to_char(finish_date,'YYYY-MM-DD')=:startDate order by finish_date desc "
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringHintView.class);
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
				"\n" +
						"select sum(first_minutes) first_minutes,\n" +
						"       sum(second_minutes) second_minutes,\n" +
						"       sum(third_minutes) third_minutes,\n" +
						"       sum(four_minutes) four_minutes\n" +
						"  from (select count(1) first_minutes,\n" +
						"               0 second_minutes,\n" +
						"               0 third_minutes,\n" +
						"               0 four_minutes\n" +
						"          from (select cfg_task_id,\n" +
						"                       round(avg((finish_date - start_date) * 1440), 1) avg_time\n" +
						"                  from aiam.task_log a\n" +
						"                 where results like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
						"                 group by cfg_task_id)\n" +
						"         where avg_time <= 5\n" +
						"        union all\n" +
						"        select 0 first_minutes,\n" +
						"               count(1) second_minutes,\n" +
						"               0 third_minutes,\n" +
						"               0 four_minutes\n" +
						"          from (select cfg_task_id,\n" +
						"                       round(avg((finish_date - start_date) * 1440), 1) avg_time\n" +
						"                  from aiam.task_log\n" +
						"                 where results like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
						"                 group by cfg_task_id)\n" +
						"         where avg_time > 5\n" +
						"           and avg_time <= 10\n" +
						"        union all\n" +
						"        select 0 first_minutes,\n" +
						"               0 second_minutes,\n" +
						"               count(1) third_minutes,\n" +
						"               0 four_minutes\n" +
						"          from (select cfg_task_id,\n" +
						"                       round(avg((finish_date - start_date) * 1440), 1) avg_time\n" +
						"                  from aiam.task_log a\n" +
						"                 where results like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
						"                 group by cfg_task_id)\n" +
						"         where avg_time > 10\n" +
						"           and avg_time <= 15\n" +
						"        union all\n" +
						"        select 0 first_minutes,\n" +
						"               0 second_minutes,\n" +
						"               0 third_minutes,\n" +
						"               count(1) four_minutes\n" +
						"          from (select cfg_task_id,\n" +
						"                       round(avg((finish_date - start_date) * 1440), 1) avg_time\n" +
						"                  from aiam.task_log a\n" +
						"                 where results like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
						"                 group by cfg_task_id)\n" +
						"         where avg_time > 15)\n" +
						"         "
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByFrequencyAndTimes.class);

	}

	//以下表格
	public List<ArchTaskMonitoringTable> queryByConditionTable(ArchTaskMonitoringTable condition ) throws ParseException{
		System.out.println("condition.getCondition()----------------------------------------=-=-"+condition.getCondition());
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println("condition.getStartDate()---------"+condition.getStartDate());
		String dateQueryStart = sdf.format(condition.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);

		String dateQueryEnd = sdf.format(endTime);
		StringBuilder nativeSql = new StringBuilder(
				"select to_char(a.start_date, 'yyyy-MM-dd') start_time,\n" +
						"       b.cfg_task_id,\n" +
						"       b.task_name,\n" +
						"       b.business_class,\n" +
						"       a.results\n" +
						"  from aiam.task_log a, aiam.cfg_task b\n" +
						"  where a.cfg_task_id = b.cfg_task_id\n" +
						"   and a.results not like '%uccess%'\n" +
						"   and to_char(a.start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"   and to_char(a.start_date, 'YYYY-MM-DD') >= :endDate\n" +
						" group by b.cfg_task_id,\n" +
						"          b.task_name,\n" +
						"          b.business_class,\n" +
						"           a.results,\n" +
						"          to_char(a.start_date, 'yyyy-MM-dd')\n" +
						" union all\n" +
						" select to_char(a.start_date, 'yyyy-MM-dd') start_time,\n" +
						"       b.cfg_task_id,\n" +
						"       b.task_name,\n" +
						"       b.business_class,\n" +
						"       a.results\n" +
						"  from aiam.task_log a, aiam.cfg_task b\n" +
						" where a.cfg_task_id = b.cfg_task_id\n" +
						"   and a.results is null\n" +
						"   and to_char(a.start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"   and to_char(a.start_date, 'YYYY-MM-DD') >= :endDate\n" +
						" group by b.cfg_task_id,\n" +
						"          b.task_name,\n" +
						"          b.business_class,\n" +
						"           a.results,\n" +
						"          to_char(a.start_date, 'yyyy-MM-dd')\n" +
						" order by start_time"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		params.add(new ParameterCondition("endDate", dateQueryEnd));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTable> queryByConditionTableSecond(ArchTaskMonitoringTable condition ) throws ParseException{
        List<ParameterCondition> params = new ArrayList<ParameterCondition>();
        //startDate是Date类型
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String dateQueryStart = sdf.format(condition.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
               "select cfg_task_id,task_name,business_class,success_times,fail_times, round(success_times / (sum(success_times) + sum(fail_times)), 2)*100 success_rate\n" +
					   "  from (\n" +
					   "        \n" +
					   "        select a.cfg_task_id,\n" +
					   "                b.task_name,\n" +
					   "                b.business_class,\n" +
					   "                count(*) success_times,\n" +
					   "                0 fail_times\n" +
					   "          from aiam.task_log a, aiam.cfg_task b\n" +
					   "         where a.cfg_task_id = b.cfg_task_id\n" +
					   "           and results like '%uccess%'\n" +
					   "           and to_char(finish_date, 'YYYY-MM-DD') = :startDate\n" +
					   "         group by a.cfg_task_id, b.task_name, b.business_class\n" +
					   "        \n" +
					   "        union all\n" +
					   "        select cfg_task_id,\n" +
					   "               task_name,\n" +
					   "               business_class,\n" +
					   "               0 success_times,\n" +
					   "               sum(fail_times) + sum(null_times) fail_times\n" +
					   "          from (select a.cfg_task_id cfg_task_id,\n" +
					   "                       b.task_name task_name,\n" +
					   "                       b.business_class business_class,\n" +
					   "                       count(*) fail_times,\n" +
					   "                       0 null_times\n" +
					   "                  from aiam.task_log a, aiam.cfg_task b\n" +
					   "                 where a.cfg_task_id = b.cfg_task_id\n" +
					   "                   and results not like '%uccess%'\n" +
					   "                   and to_char(finish_date, 'YYYY-MM-DD') =:startDate\n" +
					   "                 group by a.cfg_task_id, b.task_name, b.business_class\n" +
					   "                union all\n" +
					   "                select a.cfg_task_id cfg_task_id,\n" +
					   "                       b.task_name task_name,\n" +
					   "                       b.business_class business_class,\n" +
					   "                       0 fail_times,\n" +
					   "                       count(*) null_times\n" +
					   "                  from aiam.task_log a, aiam.cfg_task b\n" +
					   "                 where a.cfg_task_id = b.cfg_task_id\n" +
					   "                   and results is null\n" +
					   "                   and to_char(finish_date, 'YYYY-MM-DD') = :startDate\n" +
					   "                 group by a.cfg_task_id, b.task_name, b.business_class)\n" +
					   "         group by cfg_task_id, task_name, business_class\n" +
					   "         )\n" +
					   " group by  cfg_task_id,task_name,business_class,success_times,fail_times\n" +
					   " order by success_times desc"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTable> queryByConditionTableThird(ArchTaskMonitoringTable condition ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        System.out.println("表格3   condition.getStartDate():------------------"+condition.getStartDate());
        String dateQueryStart = sdf.format(condition.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
				"select b.cfg_task_id,b.task_name,round(avg((a.finish_date-a.start_date)*1440),1) avg_time,b.task_expr " +
						" from aiam.task_log a,aiam.cfg_task b " +
						" where to_char(a.start_date,'YYYY-MM-DD')= :startDate " +
						" and a.cfg_task_id=b.cfg_task_id " +
						" group by b.cfg_task_id,b.task_name,b.task_expr " +
						" order by 3 desc\n"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTable> queryByConditionTableFour(ArchTaskMonitoringTable condition ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition.getStartDate());
		String str1 = " and times <=5)";
		String str2 = " and times>=6 and times <=10)";
		String str3 = " and times>=11 and times <=20)";
		String str4 = " and times>=21)";
		String str  = " order by cfg_task_id ";
		StringBuilder nativeSql = new StringBuilder(
				"select cfg_task_id,task_name,cfg_task_type_code,business_class from aiam.cfg_task\n" +
						" where cfg_task_id in\n" +
						"       (select cfg_task_id\n" +
						"          from (select b.cfg_task_id, count(1) times\n" +
						"                  from aiam.cfg_task a, aiam.task_log b\n" +
						"                 where a.cfg_task_id = b.cfg_task_id\n" +
						"                   and results like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
						"                 group by b.cfg_task_id\n" +
						"                 order by b.cfg_task_id)\n" +
						"         where 1 = 1"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		if("timesOne".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str1);
		}else if("timesTwo".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str2);
		}else if("timesThree".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str3);
		}else if("timesFour".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str4);
		}
		nativeSql.append(str);
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTable> queryByConditionTableFive(ArchTaskMonitoringTable condition ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println("表格5   condition.getStartDate():------------------"+condition.getStartDate());
		String dateQueryStart = sdf.format(condition.getStartDate());
		String str1 = " and avg_time>=0 and avg_time<=5";
		String str2 = " and avg_time>5 and avg_time<=10";
		String str3 = " and avg_time>10 and avg_time<=15";
		String str4 = " and avg_time>15";
		StringBuilder nativeSql = new StringBuilder(
				"select cfg_task_id,task_name,avg_time,task_expr\n" +
						" from(select b.cfg_task_id,b.task_name,round(avg((a.finish_date-a.start_date)*1440),1) avg_time,b.task_expr from aiam.task_log a,aiam.cfg_task b where to_char(a.start_date,'YYYY-MM-DD')=:startDate and a.cfg_task_id=b.cfg_task_id \n" +
						" group by b.cfg_task_id,b.task_name,b.task_expr order by 3 desc)\n" +
						" where 1=1"
		);
		if("minutesOne".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str1);
		}else if("minutesTwo".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str2);
		}else if("minutesThree".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str3);
		}else if("minutesFour".equals(condition.getSecondLevelCondition())){
			nativeSql.append(str4);
		}
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}


	//以下Top
	public List<ArchTaskMonitoringTop> queryByConditionTopFirst(ArchTaskMonitoringTop condition ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);
		String dateQueryEnd = sdf.format(endTime);
		StringBuilder nativeSql = new StringBuilder(
				"select cfg_task_id,count_sum\n" +
						"         from(\n" +
						"         select cfg_task_id, sum(countNum) count_sum\n" +
						"           from (select cfg_task_id, count(cfg_task_id) countNum\n" +
						"                   from aiam.task_log\n" +
						"                  where results not like '%uccess%'\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                  group by cfg_task_id\n" +
						"                 \n" +
						"                 union all\n" +
						"                 select cfg_task_id, count(cfg_task_id) countNum\n" +
						"                   from aiam.task_log\n" +
						"                  where results is null\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                    and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                  group by cfg_task_id\n" +
						"                 )\n" +
						"         group by cfg_task_id\n" +
						"         order by count_sum desc\n" +
						"         )\n" +
						"         where rownum <= 10"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		params.add(new ParameterCondition("endDate", dateQueryEnd));

		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTop.class);

	}

	public List<ArchTaskMonitoringTop> queryByConditionTopSecond(ArchTaskMonitoringTop condition ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQueryStart = sdf.format(condition.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);
		String dateQueryEnd = sdf.format(endTime);
		StringBuilder nativeSql = new StringBuilder(
				"select cfg_task_id, sum(fail_sum) fail_sum, sum(total_sum) total_sum,round(sum(fail_sum)/sum(total_sum),2)*100||'%' fail_rate\n" +
						"  from (\n" +
						"        select cfg_task_id, 0 fail_sum, count(1) total_sum\n" +
						"          from aiam.task_log\n" +
						"         where to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"           and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"           and cfg_task_id in\n" +
						"               (select cfg_task_id\n" +
						"                  from aiam.task_log\n" +
						"                 where results not like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                 group by cfg_task_id\n" +
						"                union\n" +
						"                select cfg_task_id\n" +
						"                  from aiam.task_log\n" +
						"                 where results is null\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                 group by cfg_task_id)\n" +
						"         group by cfg_task_id\n" +
						"        union all\n" +
						"        select cfg_task_id, sum(count_sum) fail_sum, 0 total_sum\n" +
						"          from (select cfg_task_id, count(1) count_sum\n" +
						"                  from aiam.task_log\n" +
						"                 where results not like '%uccess%'\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                 group by cfg_task_id\n" +
						"                union all\n" +
						"                select cfg_task_id, count(1) count_sum\n" +
						"                  from aiam.task_log\n" +
						"                 where results is null\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') >= :endDate\n" +
						"                   and to_char(start_date, 'YYYY-MM-DD') <= :startDate\n" +
						"                 group by cfg_task_id\n" +
						"                 order by cfg_task_id)\n" +
						"         group by cfg_task_id)\n" +
						" group by cfg_task_id\n" +
						" order by cfg_task_id"

		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		params.add(new ParameterCondition("endDate", dateQueryEnd));

		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTop.class);

	}


}
