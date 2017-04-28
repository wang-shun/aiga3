package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCaseImplReport;

/**
 * @ClassName: NaCaseImplReportDao
 * @author: dongch
 * @date: 2017年4月27日 下午7:26:12
 * @Description:
 * 
 */
public interface NaCaseImplReportDao extends JpaRepository<NaCaseImplReport, Long>, SearchAndPageRepository<NaCaseImplReport, Long>{

	@Modifying
	@Query("delete from NaCaseImplReport")
	void delete();

	@Query(value = "SELECT COUNT(T1.CASE_ID), T6.SYS_NAME FROM NA_PLAN_CASE_RESULT T1 JOIN NA_ONLINE_TASK_DISTRIBUTE T2 ON T1.SUB_TASK_ID = T2.TASK_ID JOIN NA_ONLINE_TASK_DISTRIBUTE T3 ON T2.PARENT_TASK_ID = T3.TASK_ID JOIN NA_CHANGE_PLAN_ONILE T4 ON T3.ONLINE_PLAN = T4.ONLINE_PLAN JOIN NA_TEST_CASE T7 ON T1.CASE_ID = T7.TEST_ID JOIN AIGA_FUN_FOLDER T5 ON T7.FUN_ID = T5.FUN_ID JOIN AIGA_SYSTEM_FOLDER T6 ON T5.SYS_ID = T6.SYS_ID WHERE T4.ONLINE_PLAN = ?1 AND T1.CASE_TYPE = 1 AND (T1.RESULT IS NULL OR (T1.RESULT IS NOT NULL AND T1.RESULT <> 2)) GROUP BY T6.SYS_NAME", nativeQuery = true)
	List<Object> testCount(Long onlinePlan);

	@Query(value = "SELECT COUNT(T1.CASE_ID), T6.SYS_NAME FROM NA_PLAN_CASE_RESULT T1 JOIN NA_ONLINE_TASK_DISTRIBUTE T2 ON T1.SUB_TASK_ID = T2.TASK_ID JOIN NA_ONLINE_TASK_DISTRIBUTE T3 ON T2.PARENT_TASK_ID = T3.TASK_ID JOIN NA_CHANGE_PLAN_ONILE T4 ON T3.ONLINE_PLAN = T4.ONLINE_PLAN JOIN NA_AUTO_CASE T5 ON T1.CASE_ID = T5.AUTO_ID  JOIN AIGA_SYSTEM_FOLDER T6 ON T5.SYS_ID = T6.SYS_ID WHERE T4.ONLINE_PLAN = ?1 AND T5.ENVIROMENT_TYPE = ?2 AND T1.CASE_TYPE = 2 AND (T1.RESULT IS NULL OR (T1.RESULT IS NOT NULL AND T1.RESULT <> 2)) GROUP BY T6.SYS_NAME", nativeQuery = true)
	List<Object> autoCount(Long onlinePlan, Long env);

}

