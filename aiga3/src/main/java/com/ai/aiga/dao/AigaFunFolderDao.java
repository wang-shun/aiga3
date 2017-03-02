package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaFunFolder;

public interface AigaFunFolderDao extends JpaRepository<AigaFunFolder, BigDecimal> {

	@Query("select af from AigaFunFolder af where isInvalid=0 and isInvalid is not null")
	List<AigaFunFolder> findAllByInvalid();

}
