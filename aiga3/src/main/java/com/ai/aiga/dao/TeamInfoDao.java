package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: TeamInfoDao
 * @author: liujinfang
 * @date: 2017年4月5日 上午11:06:48
 * @Description:
 * 
 */
public interface TeamInfoDao extends JpaRepository<NaTeamInfo, Long>,
SearchAndPageRepository<NaTeamInfo, Long> {

}

