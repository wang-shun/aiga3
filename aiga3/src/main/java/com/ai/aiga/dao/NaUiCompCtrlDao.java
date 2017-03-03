package com.ai.aiga.dao;

import java.sql.Clob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaUiCompCtrl;

public interface NaUiCompCtrlDao extends JpaRepository<NaUiCompCtrl, Long>{
	
	@Query(value = "select a.ctrl_template from na_autotest_control_type a, na_ui_control b where a.ctrl_type = b.ctrl_type "
			+ " and b.ctrl_id = ?1", nativeQuery = true)
	Clob ctrlScript(Long ctrlId);

}
