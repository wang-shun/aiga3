package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.view.json.StaffListResponse;

public interface AigaStaffDao extends JpaRepository<AigaStaff,Long>{
	
	@Modifying
	@Query("delete from AigaAuthor a where a.staffId = ?1")
	void deleteByStaffId(Long staffId);
	
	@Query(value ="select * from aiga_staff ",nativeQuery=true)
	List<AigaStaff> ceshi();
	
	@Query(value = "select af.staff_id,af.code,af.name,af.state,ao.organize_id,ao.organize_name,ao.code as organize_code"
			+ " from aiga_staff af,aiga_organize ao ,aiga_staff_org_relat ar where af.staff_id = ar.staff_id"
			+ " and ar.organize_id = ao.organize_id and ar.organize_id =?1",nativeQuery= true)
	List<Object[]> findStaffByOrg(Long organizeId);
	
	
}
