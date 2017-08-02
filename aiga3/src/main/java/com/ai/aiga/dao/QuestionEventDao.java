package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.QuestionEvent;

public interface QuestionEventDao extends JpaRepository<QuestionEvent, Long>, SearchAndPageRepository<QuestionEvent, Long> {

}
