package com.ai.am.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.ArchDbConnect;
import com.ai.am.domain.ArchitectureStaticData;

public interface ArchDbConnectDao extends JpaRepository<ArchDbConnect, Long>, SearchAndPageRepository<ArchDbConnect, Long> {

    //根据indexId查询
	List<ArchDbConnect>findByIndexId(Long indexId);
	
    //根据key1查询
	List<ArchDbConnect> findByKey1(String key1);
	//根据key2查询
	List<ArchDbConnect> findByKey2(String key2);
	//根据key3查询
	List<ArchDbConnect> findByKey3(String key3);
	
	
}
