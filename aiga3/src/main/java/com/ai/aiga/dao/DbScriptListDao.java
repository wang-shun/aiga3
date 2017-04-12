package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaDbScriptList;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: DbScriptListDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午8:38:36
 * @Description:
 * 
 */
public interface DbScriptListDao extends JpaRepository<NaDbScriptList, Long>,
SearchAndPageRepository<NaDbScriptList, Long>{

}

