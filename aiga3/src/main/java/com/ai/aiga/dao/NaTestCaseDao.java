package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaTestCase;


public interface NaTestCaseDao extends SearchAndPageRepository<NaTestCase,Long>{

	void deleteByTestIdIn(List<Long> caseIds);

	boolean existsByTestName(String testName);
	
	

}
