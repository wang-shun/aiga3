package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ThirdCategory;

public interface ThirdCategoryDao extends JpaRepository<ThirdCategory, Long>,
		SearchAndPageRepository<ThirdCategory, Long> {

	@Query("select sc from ThirdCategory sc where thirdId is not null")
	List<ThirdCategory> findAllByThirdid();
}
