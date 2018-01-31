package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchSvnDbcp;

public interface ArchSvnDbcpDao extends JpaRepository<ArchSvnDbcp, Long>,
		SearchAndPageRepository<ArchSvnDbcp, Long> {

}
