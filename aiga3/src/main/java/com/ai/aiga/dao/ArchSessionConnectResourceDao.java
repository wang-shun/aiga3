package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchSessionConnectResource;

public interface ArchSessionConnectResourceDao extends JpaRepository<ArchSessionConnectResource, Long>, SearchAndPageRepository<ArchSessionConnectResource, Long> {
	  
}
