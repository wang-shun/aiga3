package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaDbScriptList;
import com.ai.am.domain.NaTeamInfo;

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

