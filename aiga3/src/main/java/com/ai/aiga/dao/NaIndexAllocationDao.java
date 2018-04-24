package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaIndexAllocation;

/**
 * @ClassName: NaIndexAllocationDao
 * @author: dongch
 * @date: 2017年5月12日 下午2:56:02
 * @Description:
 * 
 */
public interface NaIndexAllocationDao extends JpaRepository<NaIndexAllocation, Long>,SearchAndPageRepository<NaIndexAllocation, Long>{
	
	
	List<NaIndexAllocation> findByExt2(String ext2,Sort sort);
	
	@Modifying
	@Query("update from NaIndexAllocation set isShow = 0")
	void update();

}

