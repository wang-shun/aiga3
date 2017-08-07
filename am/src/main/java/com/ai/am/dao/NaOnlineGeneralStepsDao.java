package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlineGeneralSteps;
import com.ai.am.domain.NaTeamInfo;

/**
 * @ClassName: NaOnlineGeneralStepsDao
 * @author: liujinfang
 * @date: 2017年4月19日 下午6:19:48
 * @Description:
 * 
 */
public interface NaOnlineGeneralStepsDao extends JpaRepository<NaOnlineGeneralSteps, Long>,
SearchAndPageRepository<NaOnlineGeneralSteps, Long>{

}

