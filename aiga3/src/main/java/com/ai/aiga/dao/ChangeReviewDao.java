package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaEmployeeInfo;

/**
 * @ClassName: ChangeReviewDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午3:19:50
 * @Description:
 * 
 */
public interface ChangeReviewDao extends JpaRepository<NaChangeReview, Long>,
SearchAndPageRepository<NaChangeReview, Long>{
	
	@Query("select a from NaChangeReview a,NaChangePlanOnile b "
			+ "where b.onlinePlan=a.onlinePlanId and b.onlinePlan=?1 and a.ext1=?2")
	 NaChangeReview selectall(Long onlinePlan,String ext1);

	
	@Query("select a from NaChangeReview a,NaChangePlanOnile b "
			+ "where b.onlinePlan=a.onlinePlanId and b.onlinePlan=?1 ")
	 NaChangeReview selectReview(Long onlinePlan);
	
	Object findByReviewId(Long reviewId);
	
	
	
}

