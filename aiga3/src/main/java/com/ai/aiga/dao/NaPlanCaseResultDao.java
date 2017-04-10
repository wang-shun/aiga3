package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaPlanCaseResult;

/**
 * @ClassName: NaPlanCaseResultDao
 * @author: dongch
 * @date: 2017年4月7日 上午11:04:19
 * @Description:
 * 
 */
public interface NaPlanCaseResultDao extends JpaRepository<NaPlanCaseResult, Long>{

	@Modifying
	@Query(value = "insert into na_plan_case_result(result_id,sub_task_id,case_id,case_type,case_state)"
			+ " select na_plan_case_result$seq.nextval, ?1, element_id,element_type,0 from na_auto_coll_group_case"
			+ " where collect_id = ?2 and element_type = ?3", nativeQuery = true)
	void saveCaseResult(Long taskId, Long collectId, Long elementType);

	@Query(value = "select a.name as dealName, a.bill_id, b.task_name, b.assign_date, (select name from aiga_staff where staff_id = b.assign_id) as assignName from aiga_staff a, na_online_task_distribute b where a.staff_id = b.deal_op_id "
			+ " and b.task_id = ?1", nativeQuery = true)
	Object[] message(Long taskId);


}

