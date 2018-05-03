package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchDbSession;

public interface ArchDbSessionDao extends JpaRepository<ArchDbSession, Long>,SearchAndPageRepository<ArchDbSession, Long> {

}
