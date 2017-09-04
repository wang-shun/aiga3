package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchiSysHealthReport;

public interface ArchiSysHealthReportDao extends JpaRepository<ArchiSysHealthReport, Long> {
	public List<ArchiSysHealthReport> findByOnlysysId(Long onlysysId);
}
