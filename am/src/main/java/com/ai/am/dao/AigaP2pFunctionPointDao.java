package com.ai.am.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.AigaP2pFunctionPoint;

public interface AigaP2pFunctionPointDao extends JpaRepository<AigaP2pFunctionPoint, BigDecimal>{

	
}
