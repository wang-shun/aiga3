package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCaseConstructionReport;

/**
 * @ClassName: NaCaseContructionDao
 * @author: dongch
 * @date: 2017年4月24日 下午3:49:10
 * @Description:
 * 
 */
public interface NaCaseContructionReportDao extends JpaRepository<NaCaseConstructionReport, Long>, SearchAndPageRepository<NaCaseConstructionReport, Long>{


	@Query("select sysId, funNum, caseNumQrelease, autoCaseNumQrelease, autoCaseCoverQrelease, caseNumRelease, autoCaseNumRelease, autoCaseCoverRelease from NaCaseConstructionReport where reportType = 1 and statisticalMonth = ?1")
	List<Object> findOld(String lastMonth);

	@Query(value = "SELECT SYS_ID ,COUNT(1) FROM (SELECT  S.SYS_ID,F.FUN_ID FROM  AIGA_FUN_FOLDER F,  AIGA_SYSTEM_FOLDER S WHERE  F.SYS_ID = S.SYS_ID  AND S.IMPORTANT_CLASS=1 AND F.IMPORTANT_CLASS IN(1,2,3) GROUP BY S.SYS_ID,F.FUN_ID ) GROUP BY SYS_ID", nativeQuery = true)
	List<Object> findSysFunCount();

	@Query(value = "SELECT SYS_ID, COUNT(1) FROM (SELECT S.SYS_ID, F.FUN_IDFROM NA_AUTO_CASE A, AIGA_FUN_FOLDER F, AIGA_SYSTEM_FOLDER S WHERE A.FUN_ID = F.SYS_IDAND F.SYS_ID = S.SYS_ID AND S.IMPORTANT_CLASS = 1 AND F.IMPORTANT_CLASS IN (1, 2, 3) AND A.IMPORTANT IN (1, 2, 3) AND A.ENVIRONMENT_TYPE = 2 GROUP BY S.SYS_ID, F.FUN_ID) GROUP BY SYS_ID", nativeQuery = true)
	List<Object> findSysFunCover();

	
	List<Object> findSysCaseCount(String dayOrMonth);

}

