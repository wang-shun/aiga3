package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.domain.ArchitectureThird;

public interface ArchitectureStaticDataDao  extends JpaRepository<ArchitectureStaticData, Long> {
    //根据Type查询
	List<ArchitectureStaticData> findByCodeType(String codeType);
	
    //根据Type和CodeValue查询
	List<ArchitectureStaticData> findByCodeTypeAndCodeValue(String codeTypes,String codeValue);
	
}
