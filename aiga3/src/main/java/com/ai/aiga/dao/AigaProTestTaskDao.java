package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.AigaProTestTask;

public interface AigaProTestTaskDao extends JpaRepository<AigaProTestTask, BigDecimal>{

}
