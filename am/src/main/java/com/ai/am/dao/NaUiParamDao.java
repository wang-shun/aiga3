package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaUiParam;

public interface NaUiParamDao extends JpaRepository<NaUiParam, Long>{
	
	List<NaUiParam> findByCompId(Long compId);

}
