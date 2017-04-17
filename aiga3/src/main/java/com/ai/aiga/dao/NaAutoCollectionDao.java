package com.ai.aiga.dao;


import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoCollection;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NaAutoCollectionDao extends SearchAndPageRepository<NaAutoCollection, Long>{

	/**
	 * @param collectId 用例集ID
	 */
	@Query(value="delete from  na_auto_collection where collect_id =?1 " , nativeQuery=true)
      public int deleteAigaCaseCollectionByCollectId(Long collectId);

	/**
	 * 根据collectId查询用例集信息
	 * @param collectId  用例集信息
	 * @return  NaAutoCollection
	 */
		public NaAutoCollection findByCollectId(Long collectId);
		
		
		
		public  List<NaAutoCollection> findByCaseType(Long caseType);
	
		
		/**
		 * @param caseNum 关联用例数量
		 * @param collectId 用例集编号
		 */
		@Modifying
		@Query(value="update na_auto_collection t set t.case_num= (select count(*) from  na_auto_coll_group_case where collect_id =?1 and element_type in(1,2)) where collect_id=?1 " ,nativeQuery=true)
		public int  updateCaseNum(Long collectId);
}
