package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.domain.NaTestCaseParam;


public interface NaTestCaseParamDao extends JpaRepository<NaTestCaseParam,Long>{

	void deleteByTestIdIn(List<Long> caseIds);

	List<NaTestCaseParam> findByTestId(Long testId);

}
