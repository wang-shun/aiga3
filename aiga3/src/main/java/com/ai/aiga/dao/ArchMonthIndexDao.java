package com.ai.aiga.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchMonthIndex;

public interface ArchMonthIndexDao extends JpaRepository<ArchMonthIndex, Long>, SearchAndPageRepository<ArchMonthIndex, Long> {

}
