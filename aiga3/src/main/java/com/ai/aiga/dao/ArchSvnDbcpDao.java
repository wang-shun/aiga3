package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.ArchSvnDbcp;

public interface ArchSvnDbcpDao extends JpaRepository<ArchSvnDbcp, Long>,
		SearchAndPageRepository<ArchSvnDbcp, Long> {

	  //Center--->db
	  List<ArchSvnDbcp> findByCenter(String center);
	  
	  List<ArchSvnDbcp> findByCenterAndModule(String center, String module);
}
