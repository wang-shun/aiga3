package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.OnlineSituationReport;

public interface OnlineSituationReportDao  extends JpaRepository<OnlineSituationReport, Long>, SearchAndPageRepository<OnlineSituationReport, Long> {
	

}
