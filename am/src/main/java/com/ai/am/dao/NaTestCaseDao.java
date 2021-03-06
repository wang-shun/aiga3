package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaTestCase;


public interface NaTestCaseDao extends SearchAndPageRepository<NaTestCase,Long>{

	void deleteByTestIdIn(List<Long> caseIds);

	boolean existsByTestName(String testName);
	
	

}
