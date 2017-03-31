package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoCaseResultFlow;

public interface NaAutoCaseResultFlowDao extends JpaRepository<NaAutoCaseResultFlow, Long>, SearchAndPageRepository<NaAutoCaseResultFlow, Long>{

}
