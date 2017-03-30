package com.ai.aiga.dao;


import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCodePath;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NaCodePathDao extends JpaRepository<NaCodePath, Long>, SearchAndPageRepository<NaCodePath, Long>{

	NaCodePath findByIds(Long ids);
	
	List<NaCodePath> findByPlanDate(Date planDate);
    
}
