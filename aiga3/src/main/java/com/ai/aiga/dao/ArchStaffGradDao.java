package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchStaffGrad;

public interface ArchStaffGradDao extends JpaRepository<ArchStaffGrad,Long> ,SearchAndPageRepository<ArchStaffGrad,Long>{
	
	public List<ArchStaffGrad> findByState(String state);
}
