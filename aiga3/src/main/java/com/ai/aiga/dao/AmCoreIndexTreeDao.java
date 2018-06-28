package com.ai.aiga.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AmCoreIndexTree;

public interface AmCoreIndexTreeDao extends JpaRepository<AmCoreIndexTree, Long>, SearchAndPageRepository<AmCoreIndexTree, Long> {

	  //月份指标查询所有
	  @Modifying
	  @Query(value = " select a.* from am_core_index_tree a where a.group_id like '1___' ", nativeQuery = true)
	  List<AmCoreIndexTree>findAllDayConnects();
	  
	  //月份指标查询所有
	  @Modifying
	  @Query(value = " select a.* from am_core_index_tree a where a.group_id like '2___' ", nativeQuery = true)
	  List<AmCoreIndexTree>findAllMonthConnects();
	  
	  //汇总指标查询所有  ri
	  @Modifying
//	  @Query(value = " select a.* from am_core_index_tree a where a.group_id not like '3___' and a.group_id not like '4___' and a.group_id not like '2___' and a.index_id not like '2___' and a.state = 'U' and a.index_id not like '2____' and a.index_id not like '2_____'", nativeQuery = true)
	  @Query(value = " (select a.* from am_core_index_tree a where a.state = 'U' and a.group_id = 999999 union select a.* from am_core_index_tree a where a.state = 'U' and a.index_id like '1___' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '1___' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '1____' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '1_____') ", nativeQuery = true)
	  List<AmCoreIndexTree>findAllIndexByDay();
	  
	  //汇总指标查询所有 yue
	  @Modifying
//	  @Query(value = " select a.* from am_core_index_tree a where a.group_id not like '3___' and a.group_id not like '4___' and a.group_id not like '1___' and a.index_id not like '1___' and a.state = 'U' and a.index_id not like '1____' and a.index_id not like '1_____'", nativeQuery = true)
	  @Query(value = " (select a.* from am_core_index_tree a where a.state = 'U' and a.group_id = 999999 union select a.* from am_core_index_tree a where a.state = 'U' and a.index_id like '2___' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '2___' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '2____' union select a.* from am_core_index_tree a where a.state = 'U' and a.group_id like '2_____') ", nativeQuery = true)
	  List<AmCoreIndexTree>findAllIndexByMonth();
	  
	  List<AmCoreIndexTree>findByGroupId(Long groupId);
}
