package com.ai.aiga.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.QuestionInfo;

public interface QuestionInfoDao extends JpaRepository<QuestionInfo, Long>, SearchAndPageRepository<QuestionInfo, Long> {

}
