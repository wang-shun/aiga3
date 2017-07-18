package com.ai.aiga.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchSrvManage;

public interface ArchSrvManageDao extends JpaRepository<ArchSrvManage, Long>, SearchAndPageRepository<ArchSrvManage, Long> {

}
