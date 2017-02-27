package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaStaff;

public interface AigaStaffDao extends JpaRepository<AigaStaff,Long>{
	@Modifying
	@Query("delete from AigaAuthor a where a.staffId = ?1")
	void deleteByStaffId(Long staffId);
}
