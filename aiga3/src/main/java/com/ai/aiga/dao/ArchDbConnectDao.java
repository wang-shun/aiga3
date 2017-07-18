package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchDbConnect;

public interface ArchDbConnectDao extends JpaRepository<ArchDbConnect, Long>, SearchAndPageRepository<ArchDbConnect, Long> {

    //根据indexId查询
	List<ArchDbConnect>findByIndexId(Long indexId);
	
}
