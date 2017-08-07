package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoTaskReportDetail;

public interface NaAutoTaskReportDetailDao extends JpaRepository<NaAutoTaskReportDetail, Long>,SearchAndPageRepository<NaAutoTaskReportDetail, Long>{

	List<NaAutoTaskReportDetail> findByTaskId(Long taskId);

}
