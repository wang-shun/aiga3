package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.domain.SysRoleStationtype;

public interface SysRoleStationtypeDao extends JpaRepository<SysRoleStationtype, Long>{

	List<SysRoleStationtype> findByRoleId(Long roleId);
	

}
