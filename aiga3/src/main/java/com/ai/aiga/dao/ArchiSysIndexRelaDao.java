package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchiSysIndexRela;

public interface ArchiSysIndexRelaDao extends JpaRepository<ArchiSysIndexRela, Long> {
	
    //查
	List<ArchiSysIndexRela> findByOnlysysId(Long onlysysId);
}
