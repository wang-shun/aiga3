package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaDatabaseScriptList;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: DatabaseScriptListDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午7:56:45
 * @Description:
 * 
 */
public interface DatabaseScriptListDao extends JpaRepository<NaDatabaseScriptList, Long>,
SearchAndPageRepository<NaDatabaseScriptList, Long>{

}

