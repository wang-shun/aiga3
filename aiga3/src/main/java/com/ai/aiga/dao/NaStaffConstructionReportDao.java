package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

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

}

