package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchFunctionRecord;

public interface ArchFunctionRecordDao  extends JpaRepository<ArchFunctionRecord, Long>, SearchAndPageRepository<ArchFunctionRecord, Long> {

}
