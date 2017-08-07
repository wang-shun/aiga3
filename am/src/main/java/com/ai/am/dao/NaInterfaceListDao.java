package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaInterfaceList;

/**
 * @ClassName: NaInterfaceListDao
 * @author: dongch
 * @date: 2017年4月11日 下午2:31:29
 * @Description:
 * 
 */
public interface NaInterfaceListDao extends JpaRepository<NaInterfaceList, Long>, SearchAndPageRepository<NaInterfaceList, Long>{

	@Modifying
	@Query("update NaInterfaceList set state = 1 where id = ?1")
	void updateState(Long id);

	@Modifying
	@Query("update NaInterfaceList set state = 2 where id in (?1)")
	void stateUpdate(List<Long> list);

}

