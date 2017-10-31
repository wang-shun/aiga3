package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndex;

public interface AmCoreIndexDao extends JpaRepository<AmCoreIndex, Long>, SearchAndPageRepository<AmCoreIndex, Long> {

	  //根据@Query中的sql执行
	  @Query("select b from AmCoreIndex a, ArchDbConnect b where a.indexId = b.indexId")
	  List<AmCoreIndex>findAllConnects();
	  
	  //indexGroup--->indexName
	  List<AmCoreIndex> findByIndexGroup(String indexGroup);
		
	  //月份指标查询所有
	  @Modifying
	  @Query(value = " select a.* from am_core_index a where a.group_id like '1___' ", nativeQuery = true)
	  List<AmCoreIndex>findAllDayConnects();
	  
	  //月份指标查询所有
	  @Modifying
	  @Query(value = " select a.* from am_core_index a where a.group_id like '2___' ", nativeQuery = true)
	  List<AmCoreIndex>findAllMonthConnects();
	  
	  //汇总指标查询所有  ri
	  @Modifying
	  @Query(value = " select a.* from am_core_index a where a.group_id != '3001' and a.group_id not like '2___' and a.index_id not like '2___'", nativeQuery = true)
	  List<AmCoreIndex>findAllIndexs();
	  
	  //汇总指标查询所有 yue
	  @Modifying
	  @Query(value = " select a.* from am_core_index a where a.group_id != '3001' and a.group_id not like '1___' and a.index_id not like '1___'", nativeQuery = true)
	  List<AmCoreIndex>findAllIndexs2();
}
