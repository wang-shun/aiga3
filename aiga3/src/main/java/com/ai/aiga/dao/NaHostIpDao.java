package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaHostIp;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: NaHostIpDao
 * @author: liujinfang
 * @date: 2017年4月19日 上午11:09:59
 * @Description:
 * 
 */
public interface NaHostIpDao extends JpaRepository<NaHostIp, Long>,
SearchAndPageRepository<NaHostIp, Long>{

}

