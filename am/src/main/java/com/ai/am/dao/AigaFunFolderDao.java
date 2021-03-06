package com.ai.am.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaFunFolder;

public interface AigaFunFolderDao extends JpaRepository<AigaFunFolder, Long>
 ,SearchAndPageRepository<AigaFunFolder, Long> {

	@Query("select af from AigaFunFolder af where isInvalid=0 and isInvalid is not null")
	List<AigaFunFolder> findAllByInvalid();

}
