package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchAppidSystemRelation;

public interface ArchAppidSystemRelationDao extends JpaRepository<ArchAppidSystemRelation, Long>,
		SearchAndPageRepository<ArchAppidSystemRelation, Long> {

}
