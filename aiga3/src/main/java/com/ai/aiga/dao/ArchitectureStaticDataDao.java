package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchitectureStaticData;

public interface ArchitectureStaticDataDao  extends JpaRepository<ArchitectureStaticData, Long>,SearchAndPageRepository<ArchitectureStaticData, Long> {
    //根据Type查询
	List<ArchitectureStaticData> findByCodeType(String codeType);
	
	@Query(value = " select a.* from aiam.architecture_static_data a where a.code_type = ?1 ", nativeQuery= true)
	List<ArchitectureStaticData> findByCodeType2(String codeType);
	
    //根据Type和CodeValue查询
	List<ArchitectureStaticData> findByCodeTypeAndCodeValue(String codeTypes,String codeValue);
	
	//根据Type和CodeName查询
	List<ArchitectureStaticData> findByCodeTypeAndCodeName(String codeTypes,String codeName);
}
