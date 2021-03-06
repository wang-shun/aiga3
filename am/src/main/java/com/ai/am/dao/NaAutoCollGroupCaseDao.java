package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaAutoCollGroupCase;



public interface NaAutoCollGroupCaseDao extends JpaRepository<NaAutoCollGroupCase, Long>{

	@Query(value="select * from na_auto_coll_group_case where collect_id = ?1 ",nativeQuery = true)
		public List<NaAutoCollGroupCase> findByCollectId(Long collectId);
	
	@Modifying
	@Query(value="delete from na_auto_coll_group_case t where t.collect_id =  ?1 " , nativeQuery=true)
	  public void deleteByCollectId (Long collectId) throws Exception ;
	
	@Modifying
	@Query(value="delete from  na_auto_coll_group_case where element_type = ?1  and collect_id =  ?2  and element_id  = ?3 " ,nativeQuery=true)
	public void deleteConnectGroups(Long types,Long collectId ,Long  groupId) throws Exception ;
	
	public List<NaAutoCollGroupCase>  findByCollectIdAndElementIdAndElementType(Long collectId, Long elementId,Long elementType);

	
	public List<NaAutoCollGroupCase> findByCollectIdAndElementType(Long collectId, Long elementType);
}
