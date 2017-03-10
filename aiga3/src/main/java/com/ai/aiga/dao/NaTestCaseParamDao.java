package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.NaTestCaseParam;


public interface NaTestCaseParamDao extends JpaRepository<NaTestCaseParam,Long>{

	void deleteByTestIdIn(List<Long> caseIds);

}
