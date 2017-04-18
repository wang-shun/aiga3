package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.NaDbExecutionException;

/**
 * @ClassName: DbExecutionExceptionDao
 * @author: liujinfang
 * @date: 2017年4月17日 下午6:10:14
 * @Description:
 * 
 */
public interface DbExecutionExceptionDao extends JpaRepository<NaDbExecutionException, Long>
,SearchAndPageRepository<NaDbExecutionException, Long> {

}

