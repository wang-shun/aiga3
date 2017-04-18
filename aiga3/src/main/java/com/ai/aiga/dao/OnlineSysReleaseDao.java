package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaDbExecutionException;
import com.ai.aiga.domain.NaOnlineSysRelease;

/**
 * @ClassName: OnlineSysReleaseDao
 * @author: liujinfang
 * @date: 2017年4月17日 下午6:54:25
 * @Description:
 * 
 */
public interface OnlineSysReleaseDao extends JpaRepository<NaOnlineSysRelease, Long>
,SearchAndPageRepository<NaOnlineSysRelease, Long>{

}

