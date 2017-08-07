package com.ai.am.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaSubSysFolder;

public interface AigaSubSysFolderDao extends JpaRepository<AigaSubSysFolder, Long>
,SearchAndPageRepository<AigaSubSysFolder, Long>{


}
