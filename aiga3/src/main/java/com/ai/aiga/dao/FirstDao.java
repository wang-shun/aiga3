package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.First;

public interface FirstDao extends JpaRepository<First, Long>,
		SearchAndPageRepository<First, Long> {

	@Query("select fi from First fi where firstCategory is not null")
	List<First> findAllByFirstcategory();
}
