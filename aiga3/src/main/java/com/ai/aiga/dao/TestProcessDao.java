package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaDbScriptExecutionProcess;
import com.ai.aiga.domain.NaTestProcess;

/**
 * @ClassName: TestProcessDao
 * @author: liujinfang
 * @date: 2017年4月18日 上午9:57:48
 * @Description:
 * 
 */
public interface TestProcessDao extends JpaRepository<NaTestProcess, Long>
,SearchAndPageRepository<NaTestProcess, Long>{

}

