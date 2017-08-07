package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.SecondCategory;

public interface SecondCategoryDao extends JpaRepository<SecondCategory, Long>,
		SearchAndPageRepository<SecondCategory, Long> {

	@Query("select sc from SecondCategory sc where secondId is not null")
	List<SecondCategory> findAllBySecondid();
}
