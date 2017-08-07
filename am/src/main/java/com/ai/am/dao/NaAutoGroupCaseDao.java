package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaAutoGroupCase;

public interface NaAutoGroupCaseDao extends JpaRepository<NaAutoGroupCase, Long>{

	@Query(value = "select max(group_order) from na_auto_group_case where group_id = ?1", nativeQuery = true)
	Long findMaxOrder(Long groupId);
	
	@Modifying
	@Query(value = "delete from na_auto_group_case where group_id = ?1 and auto_id in (?2)", nativeQuery = true)
	void caseRelatGroupDel(Long groupId, List<Long> autoIds);

	@Query(value = "select * from na_auto_group_case where group_id = ?1 and auto_id = ?2", nativeQuery = true)
	NaAutoGroupCase findByGroupIdAndOrder(Long groupId, Long autoId);
	
	@Query(value = "select count(auto_id) from na_auto_group_case where group_id = ?1 and auto_id in (?2)", nativeQuery = true)
	int findCaseByGroupId(Long groupId, List<Long> list);

	
		List<NaAutoGroupCase> findByGroupId(Long groupId);
}
