package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.PCsfReportBymonth;

public interface PCsfReportBymonthDao extends JpaRepository<PCsfReportBymonth, Long> {
	List<PCsfReportBymonth> findByMonthDate(String month);
}
