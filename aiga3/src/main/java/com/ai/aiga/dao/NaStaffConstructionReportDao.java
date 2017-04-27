package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaStaffConstructionReport;

/**
 * @ClassName: NaStaffConstructionReportDao
 * @author: dongch
 * @date: 2017年4月26日 下午3:54:31
 * @Description:
 * 
 */
public interface NaStaffConstructionReportDao extends JpaRepository<NaStaffConstructionReport, Long>, SearchAndPageRepository<NaStaffConstructionReport, Long>{

	@Query(value = "SELECT STAFF_ID,CASE_NUM_QRELEASE,AUTO_CASE_NUM_QRELEASE,AUTO_CASE_COVER_QRELEASE,CASE_NUM_RELEASE,AUTO_CASE_NUM_RELEASE,AUTO_CASE_COVER_RELEASE FROM NA_STAFF_CONSTRUCTION_REPORT  WHERE 1=1 AND STATISTICAL_MONTH= ?1", nativeQuery = true)
	List<Object[]> findOld(String lastMonth);

}

