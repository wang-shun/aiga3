package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.domain.AigaStaffOrgRelat;
import com.ai.aiga.domain.AmCoreIndex;

public interface AigaStaffOrgRelatDao extends JpaRepository<AigaStaffOrgRelat, Long>, SearchAndPageRepository<AigaStaffOrgRelat,Long>{

	@Modifying
	@Query("delete from AigaStaffOrgRelat o where o.staffId = ?1 and o.organizeId = ?2")
	void deleteByStaffIdAndOrgId(Long staffId, Long orga);
	
	@Modifying
	@Query("update AigaStaffOrgRelat o set o.isAdminStaff = ?3 , o.isBaseOrg = ?4 where o.staffId = ?1 and o.organizeId = ?2")
	void updateByStaffIdAndOrgId(Long staffId, Long organizeId, Character isAdminStaff,
			Character isBaseOrg);
	
//	@Query(value = "select ao.organize_id ,ao.organize_name,ar.is_admin_staff,ar.is_base_org from aiga_organize ao,aiga_staff_org_relat ar"
//			+ " where ao.organize_id = ar.organize_id and ar.staff_id = ?1", nativeQuery= true)
//	List<Object[]> findStaffOrgRelatByOrg(Long staffId);

	
	List<AigaStaffOrgRelat> findByOrganizeId(Long organize);
}
