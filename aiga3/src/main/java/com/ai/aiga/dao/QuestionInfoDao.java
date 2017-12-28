package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.QuestionInfo;

public interface QuestionInfoDao extends JpaRepository<QuestionInfo, Long>, SearchAndPageRepository<QuestionInfo, Long> {
	  //疑难问题查询所有
	  @Modifying
	  @Query(value = " select a.* from question_info a where a.ext_1 = 0 ", nativeQuery = true)
	  List<QuestionInfo>findAllProblemQuestion();
	  
	  //巡检问题查询所有
	  @Modifying
	  @Query(value = " select a.* from question_info a where a.ext_1 = 1  ", nativeQuery = true)
	  List<QuestionInfo>findAllXunjianQuestion();
}
