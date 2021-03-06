package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlinePlanBug;

/**
 * @ClassName: NaOnlinePlanBugDao
 * @author: dongch
 * @date: 2017年4月5日 上午10:25:54
 * @Description:
 * 
 */
public interface NaOnlinePlanBugDao extends JpaRepository<NaOnlinePlanBug, Long>, SearchAndPageRepository<NaOnlinePlanBug, Long>{

	@Modifying
	@Query("delete from NaOnlinePlanBug where bugId in (?1)")
	void delete(List<Long> list);

}

