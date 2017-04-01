package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.NaAutoDbAcct;

public interface NaAutoDbAcctDao extends JpaRepository<NaAutoDbAcct,Long>{
	
	List<NaAutoDbAcct> findByState(Character state);
	
}
