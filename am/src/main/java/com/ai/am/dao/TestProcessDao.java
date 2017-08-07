package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaDbScriptExecutionProcess;
import com.ai.am.domain.NaTestProcess;

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

