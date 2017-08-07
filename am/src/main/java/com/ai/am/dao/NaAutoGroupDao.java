package com.ai.am.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoGroup;

public interface NaAutoGroupDao extends JpaRepository<NaAutoGroup, Long>, SearchAndPageRepository<NaAutoGroup, Long>{
 

}
