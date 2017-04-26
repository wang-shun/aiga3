package com.ai.aiga.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaOnlineSystemReleaseStage;

public interface NaChangeReviewDao extends JpaRepository<NaChangeReview, Long>,
SearchAndPageRepository<NaChangeReview, Long>{

	@Query(value="select  *  from Na_Change_Review where ext_1=?1 and conclusion is  null", nativeQuery=true)
	public NaChangeReview findByExt1(String ext1);
	
	
	@Modifying
	@Query("update NaChangeReview set planReviewDate = ?3 , reviewNum=?2 where onlinePlanId=?1 and conclusion is  null")
	void  setReviewDate(Long planId, Long reviewNum ,Date planReviewDate);
}
