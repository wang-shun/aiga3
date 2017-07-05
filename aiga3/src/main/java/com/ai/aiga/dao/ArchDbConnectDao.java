package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchDbConnect;

public interface ArchDbConnectDao extends JpaRepository<ArchDbConnect, Long> {

    //查
	List<ArchDbConnect>findByIndexId(Long indexId);
}
