package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaChangePlanOnile;

public interface NaChangePlanOnileDao extends JpaRepository<NaChangePlanOnile, Long>,
SearchAndPageRepository<NaChangePlanOnile, Long> 
{

}
