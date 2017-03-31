package com.ai.aiga.dao;


import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCodePath;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NaCodePathDao extends JpaRepository<NaCodePath, Long>, SearchAndPageRepository<NaCodePath, Long>{

	NaCodePath findById(Long id);
	
	@Query(value="select *   from Na_Code_Path where to_char(plan_Date,'yyyy-MM-dd')  like ?1  " , nativeQuery=true)
	List<NaCodePath> findByPlanDate(String planDate);
    
}
