package com.ai.am.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaSubSysFolder;
import com.ai.am.domain.NaProcessNodeRecord;
import com.ai.am.domain.SysRole;
import com.ai.am.domain.Tasks;
import com.ai.am.view.controller.role.dto.Role;

public interface NaProcessNodeRecordDao extends JpaRepository<NaProcessNodeRecord, Long>
,SearchAndPageRepository<NaProcessNodeRecord, Long>{

	@Modifying
	@Query(value="update na_process_node_record a  set a.type=1 ,"
			+ "a.time = sysdate "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  update(Long onlinePlan ,Long node);
	
	
	@Modifying
	@Query(value="update na_process_node_record set type=( select case when count(*) = 0 then 2 else 1 end  from na_online_task_distribute t where t.parent_task_id = 0 and t.deal_state <> 3 and t.task_type in (1,2,3)  and online_plan = ?1) where plan_id=?1 and node=4",nativeQuery=true)
	void  updateTestNode(Long onlinePlan);
	
	@Modifying
	@Query(value="update na_process_node_record set type=( select case when count(*) = 0 then 2 else 1 end  from na_online_task_distribute t where t.parent_task_id = 0 and t.deal_state <> 3 and t.task_type in (4,5)  and online_plan = ?1) where plan_id=?1 and node=7",nativeQuery=true)
	void  updateSTestNode(Long onlinePlan);
	
	@Modifying
	@Query(value="update na_process_node_record a  set a.type=2 ,"
			+ "a.time=sysdate "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  commit(Long onlinePlan ,Long node);
	
	
	@Modifying
	@Query(value="update na_process_node_record a  set a.type=4 "
			+ "where a.plan_id =?1 "
			+ "and  node< ?2  and type =3 ",nativeQuery=true)
	void  updateUnDealNode(Long onlinePlan ,Long node);

	@Query(value = "select count(distinct SYS_NAME) from Plan_Detail_Manifest where plan_id = ?1", nativeQuery = true)
	Object system(Long onlinePlan);

	@Query(value = "select count(MANIFEST_ID) from Plan_Detail_Manifest where plan_id = ?1", nativeQuery = true)
	Object require(Long onlinePlan);

	
	NaProcessNodeRecord findByPlanIdAndNode(Long onlinePlan, Long node);

	@Query(value = "select count(distinct sys_name) from na_code_path  where to_char(plan_date,'yyyy-MM-dd')  in (select to_char(a.plan_date, 'yyyy-MM-dd') from na_change_plan_onile a where a.online_plan = ?1) and nvl(state,0) <> 3 ", nativeQuery = true)
	Object pack(Long onlinePlan);

	@Query(value = "select count(distinct file_name) from na_file_upload where plan_id = ?1", nativeQuery = true)
	Object file(Long onlinePlan);

	@Query(value = "select count(a.case_id) from na_plan_case_result a,na_auto_case b where a.case_id = b.auto_id and b.environment_type = ?1 and a.sub_task_id in(select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?2 and a.online_plan = ?3)", nativeQuery = true)
	Object auto(Long env, Long taskType, Long onlinePlan);
	
	
	@Query(value = "select count(a.case_id) from na_plan_case_result a where  a.sub_task_id in(select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 and a.online_plan = ?2)", nativeQuery = true)
	Object caseCount(Long taskType, Long onlinePlan);

	@Query(value = "select count(a.case_id) from na_plan_case_result a,na_test_case b where a.case_id = b.test_id and a.sub_task_id in (select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 and a.online_plan = ?2)", nativeQuery = true)
	Object test(Long taskType, Long onlinePlan);

	@Query(value = "select trunc((max(done_date)- min(done_date))*24,1) from na_plan_case_result where sub_task_id in (select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 and a.online_plan = ?2)", nativeQuery = true)
	Object time(Long taskType,Long onlinePlan);

	@Query(value = "select count(total_time_count) from aiga_boss_test_result where online_plan = ?1", nativeQuery = true)
	Object time2(Long onlinePlan);

	@Query(value = "select distinct plan_id from (select * from na_process_node_record where to_char(plan_date, 'yyyy-MM-dd') like  ?1 order by plan_date) ",nativeQuery = true)
	long findPlan(String planDate);


	@Query(value = "select count(online_plan) from na_change_plan_onile where plan_date >= to_date(?1,'yyyy-mm-dd') and plan_date < to_date(?2,'yyyy-mm-dd')  and  sign<>1 ", nativeQuery = true)
	long countPlan(String date1, String date2);


	@Query(value = "select count(bug_id) from na_online_plan_bug where bug_type = ?1 and create_date >= to_date(?2,'yyyy-mm-dd') and create_date < to_date(?3,'yyyy-mm-dd')  and ONLINE_PLANS  not in(select online_plan from na_change_plan_onile where sign = 1)", nativeQuery = true)
	long countAbnormal(Long bugType, String date1, String date2);


	@Query(value = "select distinct plan_id  from (select * from na_process_node_record where to_char(plan_date, 'yyyy-MM-dd') like ?1 order by plan_date , node )", nativeQuery = true)
	long[] plan(String planDate);


	@Query(value = "select * from na_process_node_record where  plan_id = ?1  order by  node ", nativeQuery = true)
	List<NaProcessNodeRecord> findByPlanId(long l);
	
	@Query(value = "select a.* from sys_role a,aiga_author b,aiga_staff c  where b.staff_id=c.staff_id and b.role_id=a.role_id and c.staff_id=?1", nativeQuery = true)
	List<Object[]> findRole (Long staffId);
	
	@Query("select a from SysRole a,AigaAuthor b,AigaStaff c  where b.staffId=c.staffId and b.roleId=a.roleId and c.staffId=?1")
	List<SysRole> role (Long staffId);


	@Query(value ="select distinct to_char(a.plan_date,'yyyy-mm-dd') as plan_date from NA_CHANGE_PLAN_ONILE  a where  to_char(a.plan_date,'yyyy')=?1  and to_char(a.plan_date,'MM')=?2",nativeQuery = true)
	String[] findPlanDate(String year,String month);


	@Query(value = "select count(a.case_id) from na_plan_case_result a where a.sub_task_id in(select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 ) and a.done_date > to_date(?2,'yyyy-mm-dd') and a.done_date < to_date(?3,'yyyy-mm-dd')", nativeQuery = true)
	long recase(long taskType, String date1, String date2);


	@Query(value = "select count(a.case_id) from na_plan_case_result a where a.result = 1 and a.sub_task_id in(select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 ) and a.done_date > to_date(?2,'yyyy-mm-dd') and a.done_date < to_date(?3,'yyyy-mm-dd')", nativeQuery = true)
	long reSucCase(long taskType, String date1, String date2);

	@Query(value ="select distinct to_char(a.plan_date,'yyyy-mm-dd') as plan_date from NA_CHANGE_PLAN_ONILE  a where   a.plan_date > sysdate - interval '1' year   and sign <> 1  ",nativeQuery = true)
	String[] findPlanDate();


	@Query(value = " select * from na_process_node_record where to_char(plan_date,'yyyy-MM-dd') like  ?1 order by node ", nativeQuery = true)
	List<Object> current(String planDate);


	@Query(value = "select to_char(plan_date,'yyyy-mm-dd') from na_process_node_record where to_char(plan_date,'yyyy-mm-dd') like ?1 and to_char(plan_date,'yyyy-MM-dd')  > ?2  order by plan_date ,node ", nativeQuery = true)
	String[] latest(String planDate1,String planDate2);


	@Query(value = "select to_char(plan_date,'yyyy-mm-dd') from na_process_node_record where to_char(plan_date,'yyyy-mm-dd') like ?1 order by plan_date desc", nativeQuery = true)
	String[] maxDate(String planDate);


	@Modifying
	@Query(value="delete from na_process_node_record where plan_id=?1", nativeQuery = true)
	void deleteByPlanId(Long onlinePlan);

	@Query(value = "select sum(cboss) from ("
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_auto_case b where a.case_id = b.auto_id and b.sys_id = 30 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd')"
			+ "union all "
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_test_case b where a.case_id = b.test_id and b.sys_id = 30 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd'))", nativeQuery = true)
	long cbossCase(String date1, String date2);

	@Query(value = "select sum(cboss) from ("
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_auto_case b where a.case_id = b.auto_id and b.sys_id = 30 and a.result = 1 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd')"
			+ "union all "
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_test_case b where a.case_id = b.test_id and b.sys_id = 30 and a.result = 1 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd'))", nativeQuery = true)
	long cbossSucCase(String date1, String date2);

	@Query(value = "select sum(cboss) from ("
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_auto_case b where a.case_id = b.auto_id and b.sys_id = 30 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd')"
			+ "union all "
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_test_case b where a.case_id = b.test_id and b.sys_id = 30 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd'))", nativeQuery = true)
	long esbCase(String date1, String date2);

	@Query(value = "select sum(cboss) from ("
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_auto_case b where a.case_id = b.auto_id and b.sys_id = 25 and a.result = 1 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd')"
			+ "union all "
			+ "select count(a.case_id) as cboss from na_plan_case_result a , na_test_case b where a.case_id = b.test_id and b.sys_id = 25 and a.result = 1 and a.done_date > to_date(?1,'yyyy-mm-dd') and a.done_date < to_date(?2,'yyyy-mm-dd'))", nativeQuery = true)
	long esbSucCase(String date1, String date2);


	@Query(value = "select count(*) from na_code_path_complie where plan_id = ?1 and ext_3 like 'Y' ", nativeQuery = true)
	Object sysSum(Long onlinePlan);


	@Query(value = "select count(*) from na_code_path_complie where plan_id = ?1 and result <> 1 and ext_3 like 'Y'   ", nativeQuery = true)
	Object sysNot(Long onlinePlan);


	@Query(value = "select count(*) from na_code_path_complie where plan_id = ?1 and result = 4 and ext_3 like 'Y'  ", nativeQuery = true)
	Object sysFail(Long onlinePlan);


	@Query(value = "select sum(time) from (select sum(time) as time from ( select sys_name, compile_num,trunc((max(stop_time)-min(start_time))*24,1) as time "
					  +" from na_code_path_compile_result t"
					   +" where t.plan_id =?1  and ext_2 like 'Y'  "
					   +"  group by sys_name, t.compile_num) a group by  sys_name ) ", nativeQuery = true)
	Object spendTime(Long onlinePlan);


	@Query(value = "select count(1) from Plan_Detail_Manifest where plan_id = ?1", nativeQuery = true)
	Object reqNo(Long onlinePlan);


	@Query(value = "select count(1) from Plan_Detail_Manifest where plan_id = ?1 and review_state = ?2", nativeQuery = true)
	Object reqNoTwo(Long onlinePlan, long state);


	@Query(value = "select count(*) from na_release_report where online_plan_id = ?1",nativeQuery = true)
	Object report(Long onlinePlan);

	@Query(value = "select  case when nvl(trunc((max(finish_time)-min(start_time))*24,1),0)=0 then 0 else trunc((max(finish_time)-min(start_time))*24,1) end  from na_release_report where online_plan_id = ?1", nativeQuery = true)
	Object reportTime(Long onlinePlan);

	@Query(value = "select max(r.finish_date) from na_online_task_distribute t,na_online_task_distribute b,na_online_task_result r where t.parent_task_id = b.task_id and t.task_id = r.task_id and b.task_type = ?1 and t.online_plan = ?2", nativeQuery = true)
	Date timeFinish(long taskType, Long onlinePlan);

	@Query("select assignDate from NaOnlineTaskDistribute where taskType = ?1 and onlinePlan = ?2 and parentTaskId = 0")
	Date timeCreate(long taskType, Long onlinePlan);

	@Query(value = "select count(distinct r.INFCODE) from na_online_task_distribute b, na_online_task_distribute t, na_plan_case_result_exp r where b.task_id = t.parent_task_id and t.task_id = r.sub_task_id and b.task_type = ?1 and b.online_plan = ?2",nativeQuery = true)
	Object interFace(long taskType, Long onlinePlan);

	@Query(value = "select max(t.done_date) from na_plan_case_result t, na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and t.sub_task_id = b.task_id and a.task_type = ?1 and a.parent_task_id = 0 and t.case_state = 1 and a.online_plan = ?2",nativeQuery = true)
	Date funTimeFinish(long taskType, Long onlinePlan);

	@Query(value = "select min(t.done_date) from na_plan_case_result t, na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and t.sub_task_id = b.task_id and a.task_type = ?1 and a.parent_task_id = 0 and t.case_state = 1 and a.online_plan = ?2",nativeQuery = true)
	Date funTimeCreate(long taskType, Long onlinePlan);


}
