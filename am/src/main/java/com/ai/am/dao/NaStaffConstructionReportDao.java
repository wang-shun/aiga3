package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaStaffConstructionReport;

/**
 * @ClassName: NaStaffConstructionReportDao
 * @author: dongch
 * @date: 2017年4月26日 下午3:54:31
 * @Description:
 * 
 */
public interface NaStaffConstructionReportDao extends JpaRepository<NaStaffConstructionReport, Long>, SearchAndPageRepository<NaStaffConstructionReport, Long>{

	@Query(value = "SELECT STAFF_ID,CASE_NUM_QRELEASE,AUTO_CASE_NUM_QRELEASE,AUTO_CASE_COVER_QRELEASE,CASE_NUM_RELEASE,AUTO_CASE_NUM_RELEASE,AUTO_CASE_COVER_RELEASE FROM NA_STAFF_CONSTRUCTION_REPORT  WHERE 1=1 AND STATISTICAL_MONTH= ?1", nativeQuery = true)
	List<Object> findOld(String lastMonth);

	@Query(value = "SELECT CREATOR_ID, COUNT(1) FROM (SELECT NVL(T.CREATOR_ID, 0) CREATOR_ID, T.TEST_ID FROM NA_TEST_CASE T, NA_AUTO_CASE A, AIGA_FUN_FOLDER F, AIGA_SYSTEM_FOLDER S WHERE T.TEST_ID = A.TEST_ID(+) AND F.FUN_ID = T.FUN_ID AND F.SYS_ID = S.SYS_ID and S.important_class = 1 and A.important in (1, 2, 3) AND (T.CREATE_TIME IS NULL OR T.CREATE_TIME < ADD_MONTHS(TRUNC(TO_DATE(?1,'YYYYMM'),'MM'), 1)) GROUP BY T.CREATOR_ID, T.TEST_ID)GROUP BY CREATOR_ID", nativeQuery = true)
	List<Object> caseCount(String currentMonth);

	@Query(value = "SELECT CREATOR_ID, COUNT(1) FROM (SELECT NVL(T.CREATOR_ID, 0) CREATOR_ID, T.AUTO_ID FROM NA_AUTO_CASE T, NA_TEST_CASE A,  AIGA_FUN_FOLDER F, AIGA_SYSTEM_FOLDER S WHERE T.TEST_ID = A.TEST_ID AND F.FUN_ID = T.FUN_ID AND F.SYS_ID = S.SYS_ID and S.important_class = 1 and T.important in (1, 2, 3) AND (T.UPDATE_TIME IS NULL OR T.UPDATE_TIME < ADD_MONTHS(TRUNC(TO_DATE(?1,'YYYYMM'),'MM'), 1)) AND T.ENVIRONMENT_TYPE = ?2 AND T.STATUS = 1  GROUP BY T.CREATOR_ID, T.AUTO_ID) GROUP BY CREATOR_ID", nativeQuery = true)
	List<Object> autoCount(String currentMonth, Long type);

	@Query(value = "SELECT CREATOR_ID, COUNT(1) FROM (SELECT NVL(T.CREATOR_ID, 0) CREATOR_ID, T.AUTO_ID FROM NA_AUTO_CASE T, NA_TEST_CASE  A, AIGA_FUN_FOLDER  F, AIGA_SYSTEM_FOLDER S WHERE T.TEST_ID = A.TEST_ID AND F.FUN_ID = T.FUN_ID AND F.SYS_ID = S.SYS_ID AND T.STATUS = 1 and S.important_class = 1 and T.important in (1, 2, 3) AND EXISTS (SELECT 1 FROM NA_PLAN_CASE_RESULT R WHERE T.AUTO_ID = R.CASE_ID AND R.CASE_TYPE = 2) AND (T.UPDATE_TIME IS NULL OR T.UPDATE_TIME < ADD_MONTHS(TRUNC(TO_DATE(?1,'YYYYMM'),'MM'),1)) AND T.ENVIRONMENT_TYPE = ?2 GROUP BY T.CREATOR_ID, T.AUTO_ID) GROUP BY CREATOR_ID", nativeQuery = true)
	List<Object> autoRunCount(String currentMonth, Long type);

	@Query(value = "SELECT CREATOR_ID, COUNT(1) FROM (SELECT NVL(T.CREATOR_ID, 0) CREATOR_ID, T.AUTO_ID FROM NA_AUTO_CASE T, NA_TEST_CASE  A, AIGA_FUN_FOLDER  F, AIGA_SYSTEM_FOLDER S WHERE T.TEST_ID = A.TEST_ID AND F.FUN_ID = T.FUN_ID AND F.SYS_ID = S.SYS_ID AND T.STATUS = 1 and S.important_class = 1 and T.important in (1, 2, 3) AND EXISTS (SELECT 1 FROM NA_PLAN_CASE_RESULT R WHERE T.AUTO_ID = R.CASE_ID AND R.CASE_TYPE = 2 AND R.CASE_STATE = 3) AND (T.UPDATE_TIME IS NULL OR T.UPDATE_TIME < ADD_MONTHS(TRUNC(TO_DATE(?1,'YYYYMM'),'MM'),1)) AND T.ENVIRONMENT_TYPE = ?2 GROUP BY T.CREATOR_ID, T.AUTO_ID) GROUP BY CREATOR_ID", nativeQuery = true)
	List<Object> autoFinishCount(String currentMonth, long l);

	@Modifying
	@Query("delete NaStaffConstructionReport where statisticalMonth = ?1")
	void delete(String currentMonth);

}

