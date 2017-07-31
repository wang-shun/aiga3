package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaChangePlanOnile;


public interface NaChangePlanOnileDao extends JpaRepository<NaChangePlanOnile, Long>,SearchAndPageRepository<NaChangePlanOnile, Long> 
{
	
	@Modifying
    @Query(value="update na_change_plan_onile set sign=1 where online_plan=?1",nativeQuery = true)
	void abandonChangePlanOnile(Long onlinePlan);
	
	@Query(value="select online_plan,online_plan_name,plan_state,types,plan_date,timely,done_date "
			+ "from na_change_plan_onile where online_plan=?1",nativeQuery = true)
	NaChangePlanOnile findById(Long onlinePlan);
	
	@Modifying
	@Query(value="update na_change_plan_onile set plan_state = 4 where online_plan = ?1",nativeQuery = true)
	void delectChangePlanOnile(Long onlinePlan);
	
	@Modifying
	@Query("update NaChangePlanOnile set planState = 2 where onlinePlan = ?1")
	void updatePlanState(Long onlinePlan);

	@Query("from NaChangePlanOnile where sign = 0  order by planDate desc")
	List<NaChangePlanOnile> findBySign();
	
	
	@Query("from NaChangePlanOnile where sign = 0  and planState <> 3 and planState <> 4   order by planDate desc")
	List<NaChangePlanOnile> findBySignAndPlanSate();
	
	
	@Modifying
    @Query(value="update na_change_plan_onile set FILE_UPLOAD_LAST_TIME =?2 where online_plan=?1",nativeQuery = true)
	void updateFileUploadLastTime(Long onlinePlan , Date date);

	
	
	@Query(value ="select * from Na_Change_Plan_Onile where to_char(plan_date , 'yyyy-MM-dd') like ?1  and  types in (1,3) " ,nativeQuery=true)
	List<NaChangePlanOnile> findByPlanDate(String planDate );
	
	
	
	
	@Query(value="select  *  "
			+ "from na_change_plan_onile where to_char(plan_date,'yyyy-MM-dd')  like ?1 and  types in (1,3) ",nativeQuery = true)
	List<NaChangePlanOnile> findByplanDate(String  planDate);
	
	
	@Modifying
    @Query(value="update na_change_plan_onile set is_finished =?2  where online_plan=?1",nativeQuery = true)
	void updateIsFinished(Long onlinePlan ,Long isFinished);
	
	
	
	@Query("from NaChangePlanOnile where sign=0 and  onlinePlanName like  ?1")
	List<NaChangePlanOnile> findByOnlinePlanName(String onlinePlanName);
	
	
	@Modifying
    @Query(value="update na_change_plan_onile set auto_run_result =?2 where online_plan=?1",nativeQuery = true)
	void updateAutoResult(Long onlinePlan,Long autoRunResult);

	@Query(value="select * from na_change_plan_onile where sign=0 and plan_state in (1,2) or(plan_state=3 and done_date=sysdate)  order by online_plan desc ",nativeQuery = true)
	List<NaChangePlanOnile> findByPlanState();

	
	@Query("from NaChangePlanOnile where planState = 1 and sign = 0")
	List<NaChangePlanOnile> findByPlanStateAndSign();

	List<NaChangePlanOnile> findByOnlinePlanNameAndSign(String onlinePlanName, byte sign);
	

}
