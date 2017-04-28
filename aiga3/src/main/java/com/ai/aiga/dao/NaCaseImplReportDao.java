package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

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

}

