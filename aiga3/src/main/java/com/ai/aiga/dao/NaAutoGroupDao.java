package com.ai.aiga.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoGroup;

public interface NaAutoGroupDao extends JpaRepository<NaAutoGroup, Long>, SearchAndPageRepository<NaAutoGroup, Long>{
 

}
