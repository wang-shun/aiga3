package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.QuesCategory;

public interface QuesCategoryDao extends JpaRepository<QuesCategory, Long>,
		SearchAndPageRepository<QuesCategory, Long> {

	@Query("select qc from QuesCategory qc where rootId is not null")
	List<QuesCategory> findAllByRootid();
}
