package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.QuestionEvent;

public interface QuestionEventDao extends JpaRepository<QuestionEvent, Long>, SearchAndPageRepository<QuestionEvent, Long> {

}
