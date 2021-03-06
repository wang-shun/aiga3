package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.domain.ArchitectureStaticData;

public interface ArchitectureStaticDataDao  extends JpaRepository<ArchitectureStaticData, Long> {
    //根据Type查询
	List<ArchitectureStaticData> findByCodeType(String codeType);
	
    //根据Type和CodeValue查询
	List<ArchitectureStaticData> findByCodeTypeAndCodeValue(String codeTypes,String codeValue);
	
}
