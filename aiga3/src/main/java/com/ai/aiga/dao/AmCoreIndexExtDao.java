package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndexExt;

public interface AmCoreIndexExtDao extends JpaRepository<AmCoreIndexExt, Long>,
		SearchAndPageRepository<AmCoreIndexExt, Long> {

}
