package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchDcosData;

public interface ArchDcosDataDao extends JpaRepository<ArchDcosData, Long>,
		SearchAndPageRepository<ArchDcosData, Long> {

}
