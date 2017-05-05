package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.domain.Tasks;

public interface NaProcessNodeRecordDao extends JpaRepository<NaProcessNodeRecord, Long>
,SearchAndPageRepository<NaProcessNodeRecord, Long>{
	@Modifying
	@Query(value="update na_process_node_record a  set a.type=1 ,"
			+ "a.time = to_date(to_char(sysdate,'yyyy-MM-dd '),'yyyy-MM-dd') "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  update(Long onlinePlan ,Long node);
	
	
	@Modifying
	@Query(value="update na_process_node_record a  set a.type=2 ,"
			+ "a.time = to_date(to_char(sysdate,'yyyy-MM-dd '),'yyyy-MM-dd') "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  commit(Long onlinePlan ,Long node);
	
	
	//NaProcessNodeRecord findByOnlinePlanAndNode(Long onlinePlan ,Long node);
	

}
