package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.SrvcallDay;

public interface SrvcallDayDao extends JpaRepository<SrvcallDay, Long>,
		SearchAndPageRepository<SrvcallDay, Long> {
}
