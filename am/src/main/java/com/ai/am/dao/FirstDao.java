package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.First;

public interface FirstDao extends JpaRepository<First, Long>,
		SearchAndPageRepository<First, Long> {

	@Query("select fi from First fi where firstCategory is not null")
	List<First> findAllByFirstcategory();
}
