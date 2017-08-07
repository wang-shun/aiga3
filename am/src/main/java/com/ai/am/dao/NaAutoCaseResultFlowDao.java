package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoCaseResultFlow;

public interface NaAutoCaseResultFlowDao extends JpaRepository<NaAutoCaseResultFlow, Long>, SearchAndPageRepository<NaAutoCaseResultFlow, Long>{

}
