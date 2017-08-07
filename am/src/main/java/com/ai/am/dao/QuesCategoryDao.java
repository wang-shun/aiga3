package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.QuesCategory;

public interface QuesCategoryDao extends JpaRepository<QuesCategory, Long>,
		SearchAndPageRepository<QuesCategory, Long> {

	@Query("select qc from QuesCategory qc where rootId is not null")
	List<QuesCategory> findAllByRootid();
}
