package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Long>,
SearchAndPageRepository<SysRole, Long>{
	
	public List<SysRole> findByName(String name);

	@Query("select rol from SysRole rol, AigaAuthor aut where rol.roleId = aut.roleId and aut.staffId = ?1")
	public List<SysRole> findRolesByUserId(Long staffId);
}
