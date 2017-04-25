package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

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

}

