package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaFunction;

public interface AigaFunctionDao extends JpaRepository<AigaFunction, Long>{

	@Query("select count(o.funcId) from AigaFunction o where o.parentId = ?1")
	int getCountByParentId(long parentId);
	
	@Query("select max(o.funSeq) from AigaFunction o where o.parentId = ?1")
	Short getMaxFunseqByParentId(long parentId);
	
	List<AigaFunction> getByParentIdAndName(long parentId, String name);

	List<AigaFunction> getByParentIdAndNameAndFuncIdNot(long parentId, String name, long funcId);

	@Query("select fun from AigaFunction fun, AigaRoleFunc rfun where fun.funcId = rfun.funcId and rfun.roleId in (?1) order by fun.funcLevel, fun.funSeq")
	List<AigaFunction> findFunctionsByRoleids(List<Long> roleIds);
	

}
