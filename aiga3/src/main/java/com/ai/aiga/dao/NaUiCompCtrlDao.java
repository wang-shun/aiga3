package com.ai.aiga.dao;

import java.sql.Clob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaUiCompCtrl;

public interface NaUiCompCtrlDao extends JpaRepository<NaUiCompCtrl, Long>{
	
	@Modifying
	@Query("delete from NaUiCompCtrl where compId= ?1")
	void deleteByCompId(Long compId);
	

}
