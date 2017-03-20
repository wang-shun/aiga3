package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoRunPlanCase;




public interface NaAutoRunPlanCaseDao extends SearchAndPageRepository<NaAutoRunPlanCase, Long>{

  List<NaAutoRunPlanCase>  findByPlanIdAndAutoId(Long planId,Long autoId);
 
  List<NaAutoRunPlanCase>  findByPlanIdAndGroupId(Long planId,Long groupId);
  
  @Query(value = "select count(*) from na_auto_group_case  where plan_id=?1 and group_id = ?2")
    Object findCountByPlanIdAndGroup(Long planId,Long groupId);
  
  @Modifying
  @Query(value=" delete from na_auto_group_case where plan_id=?1 and auto_id in (?2)"  , nativeQuery=true)
   void  deleteByPlanIdAndCaseId(Long planId,  String caseIds);
  
  @Modifying
  @Query(value=" delete from na_auto_group_case where plan_id=?1 and group_id in (?2)"  , nativeQuery=true)
   void  deleteByPlanIdAndGroupId(Long planId,  String groupIds);
  
  @Modifying
  @Query(value=" delete from na_auto_group_case where plan_id=?1 and Collect_id in (?2)"  , nativeQuery=true)
   void  deleteByPlanIdAndCollcetId(Long planId,  String collectIds);

}
