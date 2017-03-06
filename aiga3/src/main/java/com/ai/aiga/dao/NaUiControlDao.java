package com.ai.aiga.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutotestControlType;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.domain.NaUiControl;



public interface NaUiControlDao extends JpaRepository<NaUiControl, Long>,SearchAndPageRepository<NaUiControl, Long> {
	
	@Query(value="select * from NA_UI_CONTROL where fun_id=?1",nativeQuery = true)
	List<Object[]> findByFun(Long funId);
//	@Query("select * from NaUiControl n where n.funId = ?1")
	@Query(value="select * from NA_UI_CONTROL where  Ctrl_name=?1 and  Creator_id=?2 and  Create_time=?3",nativeQuery = true)
	public NaUiControl findByName(String Ctrl_name,Long creatorId,Date createTime);
	@Modifying
    @Query(value="insert into Na_UI_Control_DEL select * from NA_UI_CONTROL where ctrl_id=?1",nativeQuery = true)
	int backControl(Long Ctrl_id);
	
	@Query(value="select * from NA_AUTOTEST_CONTROL_TYPE  where CTRL_TYPE=?1",nativeQuery = true)
	NaAutotestControlType showConstant(String ctrlType);
}
