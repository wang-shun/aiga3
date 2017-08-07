package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoRunPlanCase;




public interface NaAutoRunPlanCaseDao extends SearchAndPageRepository<NaAutoRunPlanCase, Long>{

  List<NaAutoRunPlanCase>  findByPlanIdAndAutoId(Long planId,Long autoId);
 
  List<NaAutoRunPlanCase>  findByPlanIdAndGroupId(Long planId,Long groupId);
  
  @Query(value = "select count(*) from NaAutoRunPlanCase  where planId= ?1 and groupId = ?2")
    Object  findCountByPlanIdAndGroupId(Long planId,Long groupId);
  
  @Modifying
  @Query(value=" delete from na_auto_run_plan_case  where plan_id=?1 and auto_id = ?2 "  , nativeQuery=true)
   void  deleteByPlanIdAndCaseId(Long planId,  Long caseIds);
  
  @Modifying
  @Query(value=" delete from na_auto_run_plan_case  where plan_id=?1 and group_id = ?2  "  , nativeQuery=true)
   void  deleteByPlanIdAndGroupId(Long planId,  Long groupIds);
  
  @Modifying
  @Query(value=" delete from na_auto_run_plan_case where plan_id=?1 and Collect_id = ?2 "  , nativeQuery=true)
   void  deleteByPlanIdAndCollcetId(Long planId,  Long collectIds);
  
  List<NaAutoRunPlanCase> findByPlanId(Long planId);

}
