package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaUiParam;

public interface NaUiParamDao extends JpaRepository<NaUiParam, Long>{

	@Query(value = "select param_id, param_name,param_value, param_desc,param_sql,param_expext"
			+ " from na_ui_param where comp_id = ?1", nativeQuery = true)
	List<Object[]> compParamList(Long compId);
	
	@Modifying
	@Query(value = "delete from na_ui_param where comp_id = ?1 and param_id = ?2", nativeQuery = true)
	void compParamDel(Long compId, Long paramId);

}
