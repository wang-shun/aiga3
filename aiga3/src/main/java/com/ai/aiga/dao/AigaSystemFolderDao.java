package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaSystemFolder;

public interface AigaSystemFolderDao extends JpaRepository<AigaSystemFolder, Long>
,SearchAndPageRepository<AigaSystemFolder, Long>{

	@Query("select af from AigaSystemFolder af where isInvalid=0 and isInvalid is not null")
	List<AigaSystemFolder> findAllByInvalid();


	

}
