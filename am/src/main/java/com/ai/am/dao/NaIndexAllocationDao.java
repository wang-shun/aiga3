package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaIndexAllocation;

/**
 * @ClassName: NaIndexAllocationDao
 * @author: dongch
 * @date: 2017年5月12日 下午2:56:02
 * @Description:
 * 
 */
public interface NaIndexAllocationDao extends JpaRepository<NaIndexAllocation, Long>,SearchAndPageRepository<NaIndexAllocation, Long>{

	@Modifying
	@Query("update from NaIndexAllocation set isShow = 0")
	void update();

}

