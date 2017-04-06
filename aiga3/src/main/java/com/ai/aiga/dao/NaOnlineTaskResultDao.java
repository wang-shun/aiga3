package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.NaOnlineTaskResult;

/**
 * @ClassName: NaOnlineTaskResultDao
 * @author: dongch
 * @date: 2017年4月6日 下午4:13:01
 * @Description:
 * 
 */
public interface NaOnlineTaskResultDao extends JpaRepository<NaOnlineTaskResult, Long>{

}

