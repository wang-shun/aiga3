package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaAutoCollGroupCase;



public interface NaAutoCollGroupCaseDao extends JpaRepository<NaAutoCollGroupCase, Long>{
	/**
	 * 根据collectId查询用例-用例集信息
	 * @param collectId  用例集信息
	 * @return  NaAutoCollection
	 */
	@Query(value="select RELA_ID,COLLECT_ID,ELEMENT_TYPE,ELEMENT_ID from na_auto_coll_group_case where collect_id in (1?) ",nativeQuery = true)
		public List<NaAutoCollGroupCase> findByCollectId(String collectId);
	
	@	Query(value="delete from na_auto_coll_group_case t where t.collect_id =  ?1 " , nativeQuery=true)
	  public void deleteByCollectId(Long collectId);
}
