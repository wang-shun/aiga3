package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaUiControl;

public interface NaChangeListDao extends JpaRepository<NaChangeList, Long>,
SearchAndPageRepository<NaChangeList, Long>{
	@Query(value="select a.change_name,a.change_manager,a.change_man,a.change_title,"
			+ "a.review_state,a.result_state from NA_CHANGE_LIST a where change_name=?1",nativeQuery = true)
	NaChangeList  select(String changeName);
	
	/*@Query(value="select  a.change_name,a.change_manager,a.change_man,a.change_title,"
			+ "a.review_state,a.result_state"
			+ " from NA_CHANGE_LIST a,NA_CHANGE_PLAN_ONILE b "
			+ "where a.PLAN_ID=b.ONLINE_PLAN ",nativeQuery = true)
			List<NaChangeList>  selectList();*/
	@Query(value="select  a.* from NA_CHANGE_LIST a,NA_CHANGE_PLAN_ONILE b "
			+ "where a.PLAN_ID=b.ONLINE_PLAN ",nativeQuery = true)
			List<NaChangeList>  selectList();
	
	@Modifying
	@Query("update NaChangeList set resultState=?1 where  changeId=?2")
	void updateResultState( String state,Long changeId);
	
}
