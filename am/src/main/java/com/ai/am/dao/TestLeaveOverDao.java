package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaTeamInfo;
import com.ai.am.domain.NaTestLeaveOver;

/**
 * @ClassName: TestLeaveOverDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午6:43:43
 * @Description:
 * 
 */
public interface TestLeaveOverDao extends JpaRepository<NaTestLeaveOver, Long>,
SearchAndPageRepository<NaTestLeaveOver, Long> {

}

