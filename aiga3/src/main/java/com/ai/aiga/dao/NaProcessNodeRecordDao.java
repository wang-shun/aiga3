package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.NaProcessNodeRecord;

public interface NaProcessNodeRecordDao extends JpaRepository<NaProcessNodeRecord, Long>
,SearchAndPageRepository<NaProcessNodeRecord, Long>{
	

}
