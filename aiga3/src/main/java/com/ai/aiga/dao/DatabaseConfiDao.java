package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaDatabaseConfiScript;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: DatabaseConfiDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午7:43:28
 * @Description:
 * 
 */
public interface DatabaseConfiDao extends JpaRepository<NaDatabaseConfiScript, Long>,
SearchAndPageRepository<NaDatabaseConfiScript, Long> {

}

