package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaUiControl;

public interface NaRequireListDao extends JpaRepository<NaRequireList, Long>,
SearchAndPageRepository<NaRequireList, Long> {
	
	@Query(value="select  REQUIRE_CODE,REQUIRE_NAME,REQUIRE_MAN,DEV_MANAGER,TEST_MANAGER,REVIEW_STATE,"
			+ "INTRODUCED_STATE from NA_REQUIRE_LIST  where REQUIRE_NAME=?1",nativeQuery = true)
	NaRequireList  select(String requireName);
	
	/*@Query(value="select a.id,a.REQUIRE_CODE,a.REQUIRE_NAME,a.REQUIRE_MAN,a.DEV_MANAGER,a.TEST_MANAGER,a.REVIEW_STATE,"
			+ "a.INTRODUCED_STATE from NA_REQUIRE_LIST a,NA_CHANGE_PLAN_ONILE b "
			+ "where a.PLAN_ID=b.ONLINE_PLAN ",nativeQuery = true)
			List<NaRequireList>  selectList();*/
	@Query(value="select a.* from NA_REQUIRE_LIST a,NA_CHANGE_PLAN_ONILE b "
			+ "where a.PLAN_ID=b.ONLINE_PLAN ",nativeQuery = true)
			List<NaRequireList>  selectList();
}
