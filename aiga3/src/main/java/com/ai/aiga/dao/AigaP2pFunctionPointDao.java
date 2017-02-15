package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaP2pFunctionPoint;

public interface AigaP2pFunctionPointDao extends JpaRepository<AigaP2pFunctionPoint, BigDecimal>{

	@Query("select a.sysName from AigaP2pFunctionPoint a")
	List<String> findAll2();

}
