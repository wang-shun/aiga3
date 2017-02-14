package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.AigaP2pFunctionPoint;

public interface AigaP2pFunctionPointDao extends JpaRepository<AigaP2pFunctionPoint, BigDecimal>{

}
