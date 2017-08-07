package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.Root;

public interface RootDao extends JpaRepository<Root, Long>,
		SearchAndPageRepository<Root, Long> {

	@Query("select rt from Root rt where quesType is not null")
	List<Root> findAllByQuestype();
}
