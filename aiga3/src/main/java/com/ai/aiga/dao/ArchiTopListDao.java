package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchiTopList;

public interface ArchiTopListDao extends JpaRepository<ArchiTopList, Long>, SearchAndPageRepository<ArchiTopList, Long> {

}
