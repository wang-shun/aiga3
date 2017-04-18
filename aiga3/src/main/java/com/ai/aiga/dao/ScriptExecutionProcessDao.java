package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.NaDbScriptExecutionProcess;

/**
 * @ClassName: ScriptExecutionProcessDao
 * @author: liujinfang
 * @date: 2017年4月17日 下午6:35:38
 * @Description:
 * 
 */
public interface ScriptExecutionProcessDao extends JpaRepository<NaDbScriptExecutionProcess, Long>
,SearchAndPageRepository<NaDbScriptExecutionProcess, Long>{

}

