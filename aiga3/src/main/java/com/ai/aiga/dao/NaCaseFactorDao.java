package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaCaseFactor;

public interface NaCaseFactorDao extends JpaRepository<NaCaseFactor,Long>{

	@Modifying
	@Query("delete from NaCaseFactor t where t.caseId = ?1")
	void deleteByCaseId(Long caseId);

	List<NaCaseFactor> findByCaseId(Long caseId);
	
	List<NaCaseFactor> findByCaseIdOrderByFactorOrderAsc(Long caseId);

}
