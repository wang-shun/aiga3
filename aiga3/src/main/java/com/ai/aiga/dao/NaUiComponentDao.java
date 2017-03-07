package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaUiComponent;

public interface NaUiComponentDao extends SearchAndPageRepository<NaUiComponent, Long> , JpaRepository<NaUiComponent, Long>{
	
	@Query(value = "select sys_id as id, 0 as p_id, sys_name as name, 'N' as if_leaf"
			+ "  from aiga_system_folder where is_invalid=0 and is_invalid is not null"
			+ " union all "
			+ "select subsys_id as id, sys_id as p_id, sys_name as name, 'N' as if_leaf"
			+ " from aiga_sub_sys_folder union all "
			+ "select fun_id as id, sub_sys_id as p_id, sys_name as name, 'N' as if_leaf"
			+ "  from aiga_fun_folder where is_invalid=0 and is_invalid is not null",nativeQuery = true)
	List<Object[]> compTree();
	
	@Query(value = "select a.comp_id, a.comp_name, a.creator_id, b.name as creatorName, a.update_id, a.create_time, a.update_time"
			+ " from na_ui_component a, aiga_staff b where a.creator_id = b.staff_id and a.parent_id = ?1",nativeQuery = true)
	List<Object[]> listByFun(Long pareneId);
	
	@Query(value = "select name from aiga_staff  where staff_id = ?1", nativeQuery = true)
	String updateName(Long updateId);
	
	@Modifying
	@Query(value = "insert into na_ui_component_del select * from na_ui_component where comp_id = ?1", nativeQuery = true)
	void backUps(Long compId);
	
	@Query(value = "select sys_id as id, 0 as p_id, sys_name as name, 'N' as if_leaf,'' as script"
			+ "  from aiga_system_folder where is_invalid=0 and is_invalid is not null"
			+ " union all "
			+ "select subsys_id as id, sys_id as p_id, sys_name as name, 'N' as if_leaf, '' as script"
			+ " from aiga_sub_sys_folder union all "
			+ "select fun_id as id, sub_sys_id as p_id, sys_name as name, 'N' as if_leaf, '' as script"
			+ "  from aiga_fun_folder where is_invalid=0 and is_invalid is not null"
			+ " union all"
			+ " select b.ctrl_id as id,b.parent_id as p_id, b.ctrl_name as name,'Y' as if_leaf"
			+ " a.ctrl_template as script from na_autotest_control_type a, na_ui_control b where a.ctrl_type = b.ctrl_type",nativeQuery = true)
	List<Object[]> ctrlTree();

}
