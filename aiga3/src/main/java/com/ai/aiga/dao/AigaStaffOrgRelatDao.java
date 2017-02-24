package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaStaffOrgRelat;

public interface AigaStaffOrgRelatDao extends JpaRepository<AigaStaffOrgRelat,Long>{

	@Modifying
	@Query("delete from AigaStaffOrgRelat o where o.staffId = ?1 and o.organizeId = ?2")
	void deleteByStaffIdAndOrgId(Long staffId, Long orga);
	@Modifying
	@Query("update AigaStaffOrgRelat o set o.isAdminStaff = ?3 , o.isBaseOrg = ?4 where o.staffId = ?1 and o.organizeId = ?2")
	void updateByStaffIdAndOrgId(Long staffId, Long organizeId, Character isAdminStaff,
			Character isBaseOrg);

}
