package com.ai.am.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.ArchSrvManage;

public interface ArchSrvManageDao extends JpaRepository<ArchSrvManage, Long>, SearchAndPageRepository<ArchSrvManage, Long> {

}
