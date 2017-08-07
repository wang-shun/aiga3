package com.ai.am.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaUiCompCtrl;

import java.util.List;

public interface NaUiCompCtrlDao extends JpaRepository<NaUiCompCtrl, Long>{
	
	@Modifying
	@Query("delete from NaUiCompCtrl where compId= ?1")
	void deleteByCompId(Long compId);

	List<NaUiCompCtrl> findByCompId(Long compId);

}
