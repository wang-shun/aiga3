package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.domain.AigaOrganize;

public interface AigaOrganizeDao extends JpaRepository<AigaOrganize, Long>{
	
	//根据组织名称查询
	public List<AigaOrganize> findByOrganizeId(Long organizeId);
	
	//根据组织名称查询
	public List<AigaOrganize> findByOrganizeName(String organizeName);
	
	//查询子组织
	public List<AigaOrganize> findByParentOrganizeId(Long organizeId);
	
}
