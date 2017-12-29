package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchiSysIndexRela;
import com.ai.aiga.domain.ArchitectureThird;

public interface ArchiSysIndexRelaDao extends JpaRepository<ArchiSysIndexRela, Long>, SearchAndPageRepository<ArchiSysIndexRela, Long>{
	
    //查
	List<ArchiSysIndexRela> findByOnlysysId(Long onlysysId);
}
