package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachine;


public interface NaAutoMachineDao extends JpaRepository<NaAutoMachine, BigDecimal>,
SearchAndPageRepository<NaAutoMachine, BigDecimal> 
{

}
