package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.PTopCsfReportBymonth;

public interface PTopCsfReportBymonthDao extends JpaRepository<PTopCsfReportBymonth, Long> {
	List<PTopCsfReportBymonth> findByMonthDate(String month);
}
