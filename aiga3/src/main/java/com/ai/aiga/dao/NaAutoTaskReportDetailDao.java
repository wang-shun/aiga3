package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoTaskReportDetail;

public interface NaAutoTaskReportDetailDao extends JpaRepository<NaAutoTaskReportDetail, Long>,SearchAndPageRepository<NaAutoTaskReportDetail, Long>{

}
