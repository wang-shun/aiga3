package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.Third;

public interface ThirdDao extends JpaRepository<Third, Long>,
		SearchAndPageRepository<Third, Long> {

	@Query("select th from Third th where thirdCategory is not null")
	List<Third> findAllByThirdcategory();
}
