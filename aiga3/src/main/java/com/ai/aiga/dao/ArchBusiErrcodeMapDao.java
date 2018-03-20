package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchBusiErrcodeMap;

public interface ArchBusiErrcodeMapDao extends JpaRepository<ArchBusiErrcodeMap, Long>,
		SearchAndPageRepository<ArchBusiErrcodeMap, Long> {

}
