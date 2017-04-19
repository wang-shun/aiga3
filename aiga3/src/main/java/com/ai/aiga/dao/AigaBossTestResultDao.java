package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.domain.AigaBossTestResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AigaBossTestResultDao extends SearchAndPageRepository<AigaBossTestResult, Long> {

	List<AigaBossTestResult> findByType(Long type);
	
	
}
