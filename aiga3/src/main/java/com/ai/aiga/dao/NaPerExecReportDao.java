package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaPerExecReport;

/**
 * @ClassName: NaExecPerReportDao
 * @author: dongch
 * @date: 2017年4月28日 下午2:25:30
 * @Description:
 * 
 */
public interface NaPerExecReportDao extends JpaRepository<NaPerExecReport, Long>, SearchAndPageRepository<NaPerExecReport, Long>{

}

