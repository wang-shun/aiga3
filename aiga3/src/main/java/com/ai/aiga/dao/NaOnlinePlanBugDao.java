package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaOnlinePlanBug;

/**
 * @ClassName: NaOnlinePlanBugDao
 * @author: dongch
 * @date: 2017年4月5日 上午10:25:54
 * @Description:
 * 
 */
public interface NaOnlinePlanBugDao extends JpaRepository<NaOnlinePlanBug, Long>, SearchAndPageRepository<NaOnlinePlanBug, Long>{

}

