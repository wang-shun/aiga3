package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaSystemFolder;

public interface AigaSystemFolderDao extends JpaRepository<AigaSystemFolder, BigDecimal> {

	@Query("select af from AigaSystemFolder af where isInvalid=0 and isInvalid is not null")
	List<AigaSystemFolder> findAllByInvalid();

}
