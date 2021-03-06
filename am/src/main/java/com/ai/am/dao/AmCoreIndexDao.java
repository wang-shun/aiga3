package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AmCoreIndex;
import com.ai.am.domain.ArchitectureStaticData;

public interface AmCoreIndexDao extends JpaRepository<AmCoreIndex, Long>, SearchAndPageRepository<AmCoreIndex, Long> {

	  //根据@Query中的sql执行
	  @Query("select b from AmCoreIndex a, ArchDbConnect b where a.indexId = b.indexId")
	  List<AmCoreIndex>findAllConnects();
	  
	  //indexGroup--->indexName
	  List<AmCoreIndex> findByIndexGroup(String indexGroup);
		
}
