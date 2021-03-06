package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaStaffKpiRela;

/**
 * @ClassName: NaStaffKpiRelaDao
 * @author: dongch
 * @date: 2017年5月12日 下午3:00:05
 * @Description:
 * 
 */
public interface NaStaffKpiRelaDao extends JpaRepository<NaStaffKpiRela, Long>{

	
	List<NaStaffKpiRela> findByStaffIdAndState(Long staffId, Long state,Sort sort);
	
	@Modifying
	@Query("delete from NaStaffKpiRela where staffId = ?1")
	void deleteByStaffId(Long staffId);

}

