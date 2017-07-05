package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndex;

public interface AmCoreIndexDao extends JpaRepository<AmCoreIndex, Long>, SearchAndPageRepository<AmCoreIndex, Long> {

	
}
