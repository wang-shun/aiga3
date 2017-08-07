package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaHostIp;
import com.ai.am.domain.NaTeamInfo;

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

