package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.domain.NaTestSituation;

/**
 * @ClassName: TestSituationDao
 * @author: liujinfang
 * @date: 2017年4月13日 下午2:25:12
 * @Description:
 * 
 */
public interface TestSituationDao extends JpaRepository<NaTestSituation, Long>,
SearchAndPageRepository<NaTestSituation, Long>{

}

