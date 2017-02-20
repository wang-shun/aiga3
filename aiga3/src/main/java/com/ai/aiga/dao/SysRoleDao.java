package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Long>{
	
	public List<SysRole> findByName(String name);
}
