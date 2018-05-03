package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.DbSessionCount;

public interface DbSessionCountDao extends JpaRepository<DbSessionCount, Long>,SearchAndPageRepository<DbSessionCount, Long> {

}
