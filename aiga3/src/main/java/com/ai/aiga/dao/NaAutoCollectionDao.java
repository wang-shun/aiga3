package com.ai.aiga.dao;


import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoCollection;

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
		
		
	
		
		/**
		 * @param caseNum 关联用例数量
		 * @param collectId 用例集编号
		 */
		@Modifying
		@Query(value="update na_auto_collection t set t.case_num= ?1 where t.collect_id= ?2 ",nativeQuery=true)
		public void  updateCaseNum(Integer caseNum,Long collectId);
}
