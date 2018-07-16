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
		System.out.println("111111111111111condition.getStartDate():------------------"+condition.getStartDate());
		String dateQueryStart = sdf.format(condition.getStartDate());
		System.out.println("dateQueryStart:-----------------"+dateQueryStart);
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
						"select sum(firstMinutes) as first_minutes,\n" +
								"       sum(secondMinutes) as second_minutes,\n" +
								"       sum(thirdMinutes) as third_minutes,\n" +
								"       sum(fourMinutes) as four_minutes\n" +
								"  from (select count(*) as firstMinutes,\n" +
								"               0 secondMinutes,\n" +
								"               0 thirdMinutes,\n" +
								"               0 fourMinutes\n" +
								"          from aiam.task_log a \n" +
								"          where a.results like '%uccess%'\n" +
								"           and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
								"           and (finish_date-start_date)*24*60<=5\n" +
								"        union all\n" +
								"        select 0 firstMinutes,\n" +
								"               count(*) as secondMinutes,\n" +
								"               0 thirdMinutes,\n" +
								"               0 fourMinutes\n" +
								"          from aiam.task_log a\n" +
								"         where a.results like '%uccess%'\n" +
								"           and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
								"           and (finish_date-start_date)*24*60>5\n" +
								"           and (finish_date-start_date)*24*60<=10\n" +
								"        union all\n" +
								"        select 0 firstMinutes,\n" +
								"               0 secondMinutes,\n" +
								"               count(*) as thirdMinutes,\n" +
								"               0 fourMinutes\n" +
								"          from aiam.task_log a\n" +
								"         where a.results like '%uccess%'\n" +
								"           and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
								"           and (finish_date-start_date)*24*60 > 10\n" +
								"           and (finish_date-start_date)*24*60 <= 15\n" +
								"        union all\n" +
								"        select 0 firstMinutes,\n" +
								"               0 secondMinutes,\n" +
								"               0 thirdMinutes,\n" +
								"               count(*) as fourMinutes\n" +
								"          from aiam.task_log a\n" +
								"         where a.results like '%uccess%'\n" +
								"           and to_char(start_date, 'YYYY-MM-DD') = :startDate\n" +
								"           and (finish_date-start_date)*24*60>15)"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringByFrequencyAndTimes.class);

	}

	//以下表格
	public List<ArchTaskMonitoringTable> queryByConditionTable(ArchTaskMonitoringTable condition1 ) throws ParseException{
		System.out.println("condition5----------------------=-=-"+condition1);
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println("condition5.getStartDate()---------"+condition1.getStartDate());
		String dateQueryStart = sdf.format(condition1.getStartDate());
		long endTime = sdf.parse(dateQueryStart).getTime()-(1000 * 60 * 60 * 24*6);

		String dateQueryEnd = sdf.format(endTime);
		StringBuilder nativeSql = new StringBuilder(
				"select to_char(a.start_date,'yyyy-MM-dd') start_time,b.cfg_task_id,b.task_name,b.business_class\n" +
                        " from aiam.task_log a,aiam.cfg_task b\n" +
                        " where a.cfg_task_id=b.cfg_task_id\n" +
                        " and a.results not like '%uccess%'\n" +
                        " and to_char(a.start_date,'YYYY-MM-DD')<=:startDate\n" +
                        " and to_char(a.start_date,'YYYY-MM-DD')>=:endDate\n" +
                        " group by b.cfg_task_id,b.task_name,b.business_class,to_char(a.start_date,'yyyy-MM-dd')\n" +
                        " union all\n" +
                        " select to_char(a.start_date,'yyyy-MM-dd') start_time,b.cfg_task_id,b.task_name,b.business_class\n" +
                        " from aiam.task_log a,aiam.cfg_task b\n" +
                        " where a.cfg_task_id=b.cfg_task_id\n" +
                        " and a.results is null\n" +
                        " and to_char(a.start_date,'YYYY-MM-DD')<=:startDate\n" +
                        " and to_char(a.start_date,'YYYY-MM-DD')>=:endDate\n" +
                        " group by b.cfg_task_id,b.task_name,b.business_class,to_char(a.start_date,'yyyy-MM-dd')\n" +
                        " order by start_time"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		params.add(new ParameterCondition("endDate", dateQueryEnd));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTable.class);

	}

	public List<ArchTaskMonitoringTableSecond> queryByConditionTableSecond(ArchTaskMonitoringTableSecond condition2 ) throws ParseException{
        List<ParameterCondition> params = new ArrayList<ParameterCondition>();
        //startDate是Date类型
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String dateQueryStart = sdf.format(condition2.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
                "select a.cfg_task_id,b.task_name,b.business_class,count(*) times\n" +
                "          from aiam.task_log a,aiam.cfg_task b\n" +
                "          where a.cfg_task_id=b.cfg_task_id\n" +
                "          and results like '%uccess%'\n" +
                "          and to_char(finish_date,'YYYY-MM-DD')=:startDate\n" +
                "          group by a.cfg_task_id,b.task_name,b.business_class\n" +
                "          order by times desc"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTableSecond.class);

	}

	public List<ArchTaskMonitoringTableThird> queryByConditionTableThird(ArchTaskMonitoringTableThird condition3 ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        System.out.println("表格3   condition.getStartDate():------------------"+condition3.getStartDate());
        String dateQueryStart = sdf.format(condition3.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
				"\n" +
						"       select a.cfg_task_id,b.task_name,round((finish_date-start_date)*24*60,3) minutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and round((finish_date-start_date)*24*60,3)<=5\n" +
						"       group by a.cfg_task_id,b.task_name, round((finish_date-start_date)*24*60,3)\n" +
						"       \n" +
						"       union all\n" +
						"       select a.cfg_task_id,b.task_name,round((finish_date-start_date)*24*60,3) minutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and round((finish_date-start_date)*24*60,3)>5\n" +
						"       and round((finish_date-start_date)*24*60,3)<=10\n" +
						"       group by a.cfg_task_id,b.task_name, round((finish_date-start_date)*24*60,3)\n" +
						"       \n" +
						"       union all\n" +
						"       select a.cfg_task_id,b.task_name,round((finish_date-start_date)*24*60,3) minutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and round((finish_date-start_date)*24*60,3)>10\n" +
						"       and round((finish_date-start_date)*24*60,3)<=15\n" +
						"       group by a.cfg_task_id,b.task_name, round((finish_date-start_date)*24*60,3)\n" +
						"     \n" +
						"       union all  \n" +
						"       select a.cfg_task_id,b.task_name,round((finish_date-start_date)*24*60,3) minutes\n" +
						"       from aiam.task_log a,aiam.cfg_task b\n" +
						"       where a.cfg_task_id=b.cfg_task_id\n" +
						"       and a.results like '%uccess%'\n" +
						"       and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"       and round((finish_date-start_date)*24*60,3)>15\n" +
						"       group by a.cfg_task_id,b.task_name, round((finish_date-start_date)*24*60,3)\n" +
						"       order by minutes desc"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTableThird.class);

	}

	public List<ArchTaskMonitoringTopFirst> queryByConditionTopFirst(ArchTaskMonitoringTopFirst condition1 ) throws ParseException{
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		//startDate是Date类型
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Top1   condition.getStartDate():------------------"+condition1.getStartDate());
		String dateQueryStart = sdf.format(condition1.getStartDate());
		StringBuilder nativeSql = new StringBuilder(
				"select cfg_task_id,count_num\n" +
						" from (\n" +
						"     select cfg_task_id,count(cfg_task_id) count_num\n" +
						"     from aiam.task_log\n" +
						"     where results not like '%uccess%'\n" +
						"     or results is null\n" +
						"     and to_char(start_date,'YYYY-MM-DD')=:startDate\n" +
						"     group by cfg_task_id\n" +
						"     order by count_num desc\n" +
						")\n" +
						"where rownum<=10"
		);
		params.add(new ParameterCondition("startDate", dateQueryStart));
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoringTopFirst.class);

	}

}
