package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.NaDbScriptExecutionProcess;

/**
 * @ClassName: ExecutionProcessDao
 * @author: liujinfang
 * @date: 2017年4月18日 上午9:50:07
 * @Description:
 * 
 */
public interface ExecutionProcessDao extends JpaRepository<NaDbScriptExecutionProcess, Long>
,SearchAndPageRepository<NaDbScriptExecutionProcess, Long>{

}

