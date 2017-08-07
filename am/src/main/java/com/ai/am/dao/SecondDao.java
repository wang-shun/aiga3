package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.Second;

public interface SecondDao extends JpaRepository<Second, Long>,
		SearchAndPageRepository<Second, Long> {

	@Query("select se from Second se where secondCategory is not null")
	List<Second> findAllBySecondcategory();
}
