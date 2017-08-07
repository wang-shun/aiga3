package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.FirstCategory;

public interface FirstCategoryDao extends JpaRepository<FirstCategory, Long>,
		SearchAndPageRepository<FirstCategory, Long> {

	@Query("select fc from FirstCategory fc where firstId is not null")
	List<FirstCategory> findAllByFirstid();
}
