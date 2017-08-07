package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaReleaseReport;
import com.ai.am.domain.NaTeamInfo;

/**
 * @ClassName: ReleaseReportDao
 * @author: liujinfang
 * @date: 2017年4月17日 下午1:05:18
 * @Description:
 * 
 */
public interface ReleaseReportDao extends JpaRepository<NaReleaseReport, Long>,
SearchAndPageRepository<NaReleaseReport, Long>{

}

