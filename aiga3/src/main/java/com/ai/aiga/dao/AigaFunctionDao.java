package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaFunction;

public interface AigaFunctionDao extends JpaRepository<AigaFunction, Long>{

	@Query("select count(o.funcId) from AigaFunction o where o.parentId = ?1")
	int getCountByParentId(long parentId);

	
	List<AigaFunction> getByParentIdAndName(long parentId, String name);

	List<AigaFunction> getByParentIdAndNameAndFuncIdNot(long parentId, String name, long funcId);

}
